package cn.com.wz.vehcert.zcinfoupload

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.StringUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.PrintResult
import cn.com.wz.vehcert.zcinfo.ZCInfo
import cn.com.wz.vehcert.zcinfo.ZCInfoReplace
import grails.converters.JSON
import cn.com.wz.vehcert.zcinfo.ZCInfoTemp
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils
import vehcert.*;
class ZCInfoUploadController extends BaseController {
    def exportService
    def zcInfoUploadService
    def zcInfoService
    def index() {
        redirect(action:'list',params:params)
    }

    /**
     * @Description 查询上传失败的合格证信息
     * @params
     * @author Xu
     */
    def list(){
        render(view:'/cn/com/wz/vehcert/zcinfoupload/uploadfailed/list')
    }

    /**
     * @Description 查询已上传的合格证信息
     * @params
     * @author Xu
     */
    def uploadSucList(){
        render(view:'/cn/com/wz/vehcert/zcinfoupload/uploadsuccess/list')
    }
    /**
     * @Description 查询已上传的合格证信息
     * @params
     * @author Xu
     */
    def uploadSucListForIsDistributor(){
        render(view:'/cn/com/wz/vehcert/zcinfoupload/uploadsuccess/list',model:[isDistributor:'1'])
    }
    /**
     * json查询上传失败的合格证信息
     * @author  Xu
     * @update 上传失败列表在上传人员登录查看时区分农用车汽车
     * @return
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        params.sort='createTime'
        params.order="desc"
        List codeList =[]
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        def userId = loginUser.id
        User user  = User.findById(userId)
        def roles = user.roles
        roles.each{role->
            def roleCode = role.roleCode
            codeList.add(roleCode)
        }
        def flag
        if(codeList!=null&&codeList.size()!=0){
            if(codeList.contains('carUpload')&&codeList.contains('farmVehicleUpload')){
                flag=0
            }else{
                if(codeList.contains('carUpload')){
                    flag=1
                }else if(codeList.contains('farmVehicleUpload')){
                    flag=2
                }else{
                    flag=0
                }
            }
        }else{
            flag=0
        }
        def cel = {
           eq("web_status","2")//上传失败
            if(flag==1){
                eq("veh_Type","0")
            }else if(flag==2){
                eq("veh_Type","1")
            }
        }
        def results = ZCInfo.createCriteria().list(params,cel)
        def result = [rows: results, total: results.totalCount]
        render result as JSON
    }
    /**
     *@Description 上传失败导出功能
     *@return
     *@Auther Xu
     */
    def failexport(){
        def rows =[]
        def lst=[]
        def content=[]
        try{
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("上传失败合格证处理信息"), "UTF-8")
            def filename = ""
            def userAgent = request.getHeader("User-Agent")
            if (userAgent =~ /MSIE [4-8]/) {
                // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
                filename = "filename=\"${encodedFilename}."+params.extension+"\""
            }
            else {
                // Use RFC 6266 (RFC 2231/RFC 5987)
                filename = "filename*=UTF-8''${encodedFilename}."+params.extension
            }
            response.setHeader("Content-disposition", "attachment;${filename}");
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            Map labels = [ "veh_Type":"汽车和农用车标示0：汽车1：农用车","veh_Zchgzbh":"整车合格证编号","veh_Dphgzbh":"底盘合格证编号","veh_Fzrq":"发证日期","veh_Clzzqymc":"车辆制造企业名称","veh_Qyid":"企业ID","veh_Clfl":"车辆分类","veh_Clmc":"车辆名称"
                    ,"veh_Clpp":"车辆品牌","veh_Clxh":"车辆型号","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_Clsbdh":"车辆识别代号","veh_Cjh":"车架号","veh_Fdjh":"发动机号"
                    ,"veh_Fdjxh":"发动机型号","veh_Rlzl":"燃料种类","veh_Pl":"排量","veh_Gl":"功率","veh_zdjgl":"最大净功率","veh_Zxxs":"转向形式","veh_Qlj":"前轮距","veh_Hlj":"后轮距","veh_Lts":"轮胎数","veh_Ltgg":"轮胎规格"
                    ,"veh_Gbthps":"钢板弹簧片数","veh_Zj":"轴距","veh_Zh":"轴荷","veh_Zs":"轴数","veh_Wkc":"外廓长","veh_Wkk":"外廓宽","veh_Wkg":"外廓高","veh_Hxnbc":"货箱内部长","veh_Hxnbk":"货箱内部宽"
                    ,"veh_Hxnbg":"货箱内部高","veh_Zzl":"总质量","veh_Edzzl":"额定载质量","veh_Zbzl":"整备质量","veh_Zzllyxs":"载质量利用系数","veh_Zqyzzl":"准牵引总质量","veh_Edzk":"额定载客","veh_Bgcazzdyxzzl":"半挂车鞍座最大允许总质量","veh_Jsszcrs":"驾驶室准乘人数"
                    ,"veh_Qzdfs":"前制动方式","veh_Hzdfs":"后制动方式","veh_Qzdczfs":"前制动操作方式","veh_Hzdczfs":"后制动操作方式","veh_Cpggh":"产品公告号","veh_Ggsxrq":"公告生效日期","veh_Zzbh":"纸张编号"
                    ,"veh_Dywym":"打印唯一码","veh_Zgcs":"最高车速","veh_Clzzrq":"车辆制造日期","veh_Bz":"备注","veh_Qybz":"企业标准","veh_Cpscdz":"产品生产地址","veh_Qyqtxx":"企业其它信息","veh_Pfbz":"排放标准","veh_Clztxx":"车辆状态信息"
                    ,"veh_Jss":"驾驶室","veh_Jsslx":"驾驶室类型","veh_Zchgzbh_0":"完整合格证编号","used":"used","used2":"used2","upload":"上传路径","veh_Yh":"油耗","veh_VinFourBit":"vin第四位"
                    ,"veh_Ggpc":"公告批次","veh_pzxlh":"配置序列号","veh_clzzqyxx":"车辆制造企业信息","veh_workshopno":"车间编号","createTime":"创建时间","createrId":"创建人id","updateTime":"最后更新时间"
                    ,"updaterId":"最后更新人","uploader_Id":"上传人","uploader_Time":"上传时间","upload_Failddm":"上传失败返回代码","upload_Path":"合格证上传相对路径",
                    "web_virtualcode":"虚拟合格证编号","vercode":"校验信息"]
             def out=response.outputStream
            params.sort='createTime'
            params.order="desc"
            def cel = {
                eq("web_status","2")//上传失败

            }
            rows= ZCInfo.createCriteria().list(params,cel)
            rows.each {r->
                def m=[:]
                if(r.veh_Type=='0'){
                    m.veh_Type  ='汽车'
                }else if(r.veh_Type=='1'){
                    m.veh_Type='农用车'
                }else{
                    m.veh_Type='参数错误'
                }

                m.veh_Zchgzbh =r.veh_Zchgzbh
                m.veh_Dphgzbh =r.veh_Dphgzbh
                m.veh_Fzrq =r.veh_Fzrq
                m.veh_Clzzqymc=r.veh_Clzzqymc
                m.veh_Qyid=r.veh_Qyid
                m.veh_Clfl=r.veh_Clfl
                m.veh_Clmc=r.veh_Clmc
                m.veh_Clpp=r.veh_Clpp
                m.veh_Clxh=r.veh_Clxh
                m.veh_Csys=r.veh_Csys
                m.veh_Dpxh=r.veh_Dpxh
                m.veh_Dpid =r.veh_Dpid
                m.veh_Clsbdh =r.veh_Clsbdh
                m.veh_Cjh =r.veh_Cjh
                m.veh_Fdjh=r.veh_Fdjh

                m.veh_Fdjxh=r.veh_Fdjxh
                m.veh_Rlzl=r.veh_Rlzl
                m.veh_Pl=r.veh_Pl
                m.veh_Gl =r.veh_Gl
                m.veh_zdjgl=r.veh_zdjgl
                m.veh_Zxxs=r.veh_Zxxs
                m.veh_Qlj=r.veh_Qlj
                m.veh_Hlj=r.veh_Hlj
                m.veh_Lts=r.veh_Lts
                m.veh_Ltgg=r.veh_Ltgg
                m.veh_Gbthps=r.veh_Gbthps
                m.veh_Zj=r.veh_Zj
                m.veh_Zh=r.veh_Zh
                m.veh_Zs=r.veh_Zs
                m.veh_Wkc=r.veh_Wkc
                m.veh_Wkk=r.veh_Wkk
                m.veh_Wkg=r.veh_Wkg
                m.veh_Hxnbc=r.veh_Hxnbc
                m.veh_Hxnbk =r.veh_Hxnbk
                m.veh_Hxnbg=r.veh_Hxnbg
                m.veh_Zzl=r.veh_Zzl
                m.veh_Edzzl=r.veh_Edzzl
                m.veh_Zbzl=r.veh_Zbzl
                m.veh_Zzllyxs=r.veh_Zzllyxs
                m.veh_Zqyzzl=r.veh_Zqyzzl
                m.veh_Edzk=r.veh_Edzk
                m.veh_Bgcazzdyxzzl=r.veh_Bgcazzdyxzzl
                m.veh_Jsszcrs =r.veh_Jsszcrs
                m.veh_Qzdfs=r.veh_Qzdfs
                m.veh_Hzdfs=r.veh_Hzdfs
                m.veh_Qzdczfs=r.veh_Qzdczfs
                m.veh_Hzdczfs=r.veh_Hzdczfs
                m.veh_Cpggh=r.veh_Cpggh
                m.veh_Ggsxrq=r.veh_Ggsxrq
                m.veh_Zzbh=r.veh_Zzbh
                m.veh_Dywym=r.veh_Dywym
                m.veh_Zgcs=r.veh_Zgcs
                m.veh_Clzzrq  =r.veh_Clzzrq
                m.veh_Bz =r.veh_Bz
                m.veh_Qybz =r.veh_Qybz
                m.veh_Cpscdz  =r.veh_Cpscdz
                m.veh_Qyqtxx=r.veh_Qyqtxx
                m.veh_Pfbz =r.veh_Pfbz
                m.veh_Clztxx =r.veh_Clztxx
                m.veh_Jss=r.veh_Jss
                m.veh_Jsslx =r.veh_Jsslx
                m.veh_Zchgzbh_0 =r.veh_Zchgzbh_0
                m.used=r.used
                m.used2 =r.used2
                m.upload=r.upload
                m.veh_Yh =r.veh_Yh
                m.veh_VinFourBit=r.veh_VinFourBit
                m.veh_Ggpc=r.veh_Ggpc
                m.veh_pzxlh =r.veh_pzxlh
                m.veh_clzzqyxx=r.veh_clzzqyxx
                m.veh_workshopno=r.veh_workshopno

                m.createTime=r.createTime
                m.createrId=r.createrId
                m.updateTime  =r.updateTime
                m.uploader_Time=r.uploader_Time
                m.upload_Failddm=r.upload_Failddm
                m.upload_Path =r.upload_Path


                m.web_virtualcode=r.web_virtualcode//虚拟合格证编号
                m.vercode=r.vercode

                def userC=User.get(r.updaterId)
                if (userC){
                    m.updaterName=userC.userDetail.realName
                }else{
                    m.updaterName=''
                }
                def userU=User.get(r.uploader_Id)
                if (userU){
                    m.uploaderName=userU.userDetail.realName
                }else{
                    m.uploaderName=''
                }

                m.uploader_Time=r.uploader_Time
                lst.add(m)
            }
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["上传失败合格证信息"],content)
            session.setAttribute('compFlag',"success")
            out.flush()
            out.close()
        }
    }catch(Exception e){
        e.printStackTrace()
        session.setAttribute('compFlag',"error")
    }finally{
        rows.clear()
        lst.clear()
        content.clear()
    }
    }

    /**
     * json查询已上传的合格证信息
     * @author  Xu
     * @return
     * @Update 获取手动上传数据时，条件是创建日期小于（不是小于等于）今天
     */
    def jsonUploadSucList(){
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        params.sort='createTime'
        params.order="desc"
        def now=new Date()
        params.curTime=DateUtil.getCurrentTime(now,"yyyy-MM-dd")
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        params.loginUser = loginUser
        def result =zcInfoService.selectZcinfoByParams(params)
        render result as JSON
    }

    /**
     *@Description 成功导出功能
     *@return
     *@Auther Xu
     */
    def sucexport(){
        def rows =[]
        def content=[]
        try{
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("已上传合格证信息"), "UTF-8")
            def filename = ""
            def userAgent = request.getHeader("User-Agent")
            if (userAgent =~ /MSIE [4-8]/) {
                // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
                filename = "filename=\"${encodedFilename}."+params.extension+"\""
            }
            else {
                // Use RFC 6266 (RFC 2231/RFC 5987)
                filename = "filename*=UTF-8''${encodedFilename}."+params.extension
            }
            response.setHeader("Content-disposition", "attachment;${filename}");
            response.contentType = grailsApplication.config.grails.mime.types[params.format]

            Map labels = [ "veh_Zchgzbh":"整车合格证编号","veh_Dphgzbh":"底盘合格证编号","veh_Fzrq":"发证日期","veh_Clzzqymc":"车辆制造企业名称",
                            "veh_Qyid":"企业ID","veh_Clfl":"车辆分类","veh_Clmc":"车辆名称","veh_Clpp":"车辆品牌",
                           "veh_Clxh":"车辆型号","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_Clsbdh":"车辆识别代码","veh_Cjh":"车架号",
                            "veh_Fdjh":"发动机号","veh_Fdjxh":"发动机型号","veh_Rlzl":"燃料种类","veh_Pl":"排量","veh_Gl":"功率","veh_Zxxs":"转向形式","veh_Qlj":"前轮距",
                            "veh_Hlj":"后轮距","veh_Lts":"轮胎数","veh_Ltgg":"轮胎规格","veh_Gbthps":"钢板弹簧片数","veh_Zj":"轴距","veh_Zh":"轴荷","veh_Zs":"轴数",
                             "veh_Wkc":"外廓长","veh_Wkk":"外廓宽","veh_Wkg":"外廓高","veh_Hxnbc":"货箱内部长","veh_Hxnbk":"货箱内部宽",
                            "veh_Hxnbg":"货箱内部高","veh_Zzl":"总质量","veh_Edzzl":"额定载质量","veh_Zbzl":"整备质量","veh_Zzllyxs":"载质量利用系数","veh_Zqyzzl":"准牵引总质量",
                            "veh_Edzk":"额定载客","veh_Bgcazzdyxzzl":"半挂车鞍座载定总质量","veh_Jsszcrs":"驾驶室准乘人数","veh_Qzdfs":"前制动方式",
                            "veh_Hzdfs":"后制动方式","veh_Qzdczfs":"前制动操作方式","veh_Cpggh":"产品公告号","veh_Ggsxrq":"公告生效日期","veh_Zzbh":"纸张编号",
                            "veh_Dywym":"打印唯一码","veh_Zgcs":"最高车速","veh_Clzzrq":"车辆制造日期","veh_Bz":"备注","veh_Qybz":"企业标准","veh_Cpscdz":"产品生产地址"
                            ,"veh_Qyqtxx":"企业其它信息", "veh_Pfbz":"排放标准","veh_Clztxx":"车辆状态信息","veh_Jss":"驾驶室","veh_Jsslx":"驾驶室类型","veh_VinFourBit":"VIN第四位",
                            "updaterId":"更新人","updateTime":"更新时间","uploader_Id":"上传人","uploader_Time":"上传时间"]
            def out=response.outputStream
            params.sort='createTime'
            params.order="desc"
            rows=zcInfoUploadService.search(params).rows

            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(rows)
            excelOp.preWriteExcel(out,null,m,["上传成功合格证信息"],content)
            session.setAttribute('compFlag',"success")
            out.flush()
            out.close()
        }
        }catch(Exception e){
            e.printStackTrace()
            session.setAttribute('compFlag',"error")
        }finally{
            rows.clear()
            content.clear()
        }
    }
    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }
    /**
     * 合格证详情查看
     * @author Xu
     * @return
     */
    def show() {
        def zcinfoInstance = ZCInfo.get(params.id)
        if (!zcinfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfo.label', default: 'ZCInfo'), params.id])
            redirect(action: "uploadSucList",params:params)
            return
        }
        render(view:'/cn/com/wz/vehcert/zcinfoupload/uploadfailed/show',model:[zcinfoInstance:zcinfoInstance,suctype:params.suctype])
    }

    /**
     * @Description 上传失败合格证处理的上传到国家方法
     * @author Xu
     * @return
     * @Update 2013-09-05 huxx
     */
    def uploadInfo(){
        def result=[:]
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        String noservicezcinfo=""  //由于服务连接不好未能够成功上传的合格证编号
        def idsStr=params.ids
        def map= zcInfoUploadService.upload(idsStr,loginUser,"2",grailsApplication,"insert","")

        String msg=map.msg
        int succount=map.succount
        int failcount=map.failcount
        def flag=map.flag
        String failedzcinfo=map.failedzcinfo //上传失败的合格证编号
        if (flag){
            if (failcount==0){
                msg=succount+"条合格证申请全部上传成功!"
            }else{
                msg =  succount+"条合格证申请上传成功,"+failcount+"条合格证申请上传失败,"
                if (failedzcinfo!=""){
                    msg= msg+ "上传失败的合格证编号为:"+ failedzcinfo+"."
                }
                if (noservicezcinfo!="") {
                    msg= msg+ "由于服务没有配置好,未能够上传的合格证编号为:"+ noservicezcinfo+"."
                }

            }
            msg+=" <div style='color:red'>错误信息:${map.msg}</div>"
            result.put('msg',msg)
            render result as JSON
        }else{
            result.put('msg',msg)
            render result as JSON
        }
    }

     /************************************** 经销商合格证上传申请 **************************************/

    /**
     * 经销商合格证申请上传 查看操作
     * @author
     * @return
     */
    def showByTemp() {
        def params=params
        def zcinfoInstance = ZCInfoTemp.get(params.id)
        if (!zcinfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfo.label', default: 'ZCInfoTemp'), params.id])
            render(view:'/cn/com/wz/vehcert/zcinfo/dealer/zcinfoApplyUpload')
            return
        }
        render(view:'/cn/com/wz/vehcert/zcinfo/dealer/show',model:[zcinfoInstance:zcinfoInstance,suctype:params.suctype])
    }

    /**
     * 上传到国家(经销商合格证上传)
     * @author
     * @return
     * @update 2013-09-02 huxx 将公用部分封装为service
     * @Update huxx 2014-02-12 经销商上传时仅是修改needUpload=1
     */
    def uploadInfoByTemp(){
        def result=[:]
        def msg=""
        def flag=true
        try{
            def idsStr=params.ids

            if (idsStr){
                def ids=idsStr.toString().split("_")
                def cel={
                    //解决in中字符串不大于1000的问题
                    def lst=StringUtil.splitIn(ids)
                    and{
                        lst?.each {id->
                            'in'("veh_Zchgzbh",ids)
                        }
                    }
                }
                def  lst=ZCInfo.createCriteria().list(cel)
                lst?.each{
                    it.needUpload=1
                    it.save()
                }
            }
            msg="上传申请提交成功！"
            flag = true
        }catch(Exception e){
            msg="上传申请提交失败！错误原因：${e.cause?e.cause:e}"
            flag = true
        }
        result.flag=flag
        result.msg=msg

        render result as JSON
    }
    /**
     * 将轮胎规格不符合公告信息的合格证变为上传失败状态
     * @author QJ
     * @return
     * @update 2017-08-24
     */
    def checkFailedLtgg(){
        def result=[:]
        def msg=""
        def ids=params.ids

        if (ids){
             User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
             zcInfoUploadService.checkFailedLtgg(ids,loginUser,"5")
             msg="已将轮胎规格与公告信息不符的合格证转到上传失败！"
        }else{
             msg="请检查该数据轮胎规格是否有与公告库不符的情况！"
        }
        result.msg = msg
        render result as JSON

    }
    /**
     * @Description 进入手动执行自动上传方法
     * @return
     * @Create 2013-09-11 huxx
     */
    def toUploadByHand(){
       render (view:"/cn/com/wz/vehcert/zcinfoupload/uploadByHand",model: params)
    }
    /**
     * @Description 验证合格证的轮胎规格是否符合公告库中的信息
     * @Create 2017-8-21 huxx
     */
    def checkLtgg(){
        def msg="验证通过！"
        def flag='sucessed'
        def checkMap=[:]
        def ids=params.ids
        if (ids){ //上传部分
            User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
            checkMap = zcInfoUploadService.checkLtgg(ids,loginUser,"5")
        }else{//上传所有未上传的
            def count=ZCInfo.countByWeb_status("0")
            params.max = count
            params.offset = 0
            params.searchType='hand'
            params.isUpload='0'
            User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
            params.loginUser = loginUser
            def lst =zcInfoService.selectZcinfoByParams(params)
            def  idsStr=""
            lst?.rows?.each{
                if(idsStr){
                    idsStr+="_"+it.id
                }else{
                    idsStr+=it.id
                }
            }
            checkMap = zcInfoUploadService.checkLtgg(idsStr,loginUser,"5")
        }
        if (checkMap.failcount>0){
            msg = "选中的合格证存在轮胎规格与公告库不符!是否继续上传？"
            flag='failed'
            checkMap.put("msg",msg);
            checkMap.put("flag",flag);

        }else{
            checkMap.put("msg",msg)
            checkMap.put("flag",flag)
        }

        render (checkMap as JSON)
    }
    /**
     * @Description 手动调用自动上传方法
     * @return
     * @Create 2013-09-11 huxx
     * @Update 2013-02-13 huxx 将手动上传分为选择上传和根据条件上传
     * @Update 2014-03-11 huxx       修改上传记录组字符串的错误(前两个记录的id没有使用分隔符分割)。
   * */
   def uploadByHand(){
        def ids=params.ids
        def result=[:]
        if (ids){ //上传部分
            User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
            result = zcInfoUploadService.upload(ids,loginUser,"5",grailsApplication,"insert","")
        }else{//上传所有未上传的
            def count=ZCInfo.countByWeb_status("0")
            params.max = count
            params.offset = 0
            params.searchType='hand'
            params.isUpload='0'
            User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
            params.loginUser = loginUser
            def lst =zcInfoService.selectZcinfoByParams(params)
            def  idsStr=""
            lst?.rows?.each{
                if(idsStr){
                    idsStr+="_"+it.id
                }else{
                    idsStr+=it.id
                }
            }
            result = zcInfoUploadService.upload(idsStr,loginUser,"5",grailsApplication,"insert","")
        }
        render result as JSON
    }
    /**
     * @Description 修改合格证的状态为禁止上传状态
     * @Create 2014-02-13 huxx
     */
    def forbid(){
        def result=[:]
        def msg=""
        def flag=true
        try{
            def idsStr=params.ids
            if (idsStr){
                def ids=idsStr.toString().split("_")
                def cel={
                    //解决in中字符串不大于1000的问题
                    def lst=StringUtil.splitIn(ids)
                    and{
                        lst?.each {id->
                            'in'("id",ids)
                        }
                    }
                }
                def  lst=ZCInfo.createCriteria().list(cel)
                lst?.each{
//                    def hgz=new ZCInfo(it.properties)
//                    hgz.web_status=6
                    it.delete(flush: true)
//                    hgz.save()
                }
            }
            msg="操作成功！"
            flag = true
        }catch(Exception e){
            msg="操作失败！错误原因：${e.cause?e.cause:e}"
            flag = true
        }
        result.flag=flag
        result.msg=msg

        render result as JSON
    }

    /**
     * @Description 将合格证信息补传到国家系统
     * @Create 2013-10-28 huxx
     */
    def uploadOverTime(){
        def result=[:]
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        String noservicezcinfo=""  //由于服务连接不好未能够成功上传的合格证编号
        def idsStr=params.ids

        def map= zcInfoUploadService.upload(idsStr,loginUser,"2",grailsApplication,"overTime",params.reason)

        String msg=map.msg
        int succount=map.succount
        int failcount=map.failcount
        def flag=map.flag
        String failedzcinfo=map.failedzcinfo //上传失败的合格证编号
        if (flag){
            if (failcount==0){
                msg=succount+"条合格证申请全部补传成功!"
            }else{
                msg =  succount+"条合格证申请补传成功,"+failcount+"条合格证申请补传失败,"
                if (failedzcinfo!=""){
                    msg= msg+ "补传失败的合格证编号为:"+ failedzcinfo+"."
                }
                if (noservicezcinfo!="") {
                    msg= msg+ "由于服务没有配置好,未能够补传的合格证编号为:"+ noservicezcinfo+"."
                }

            }
            result.put('msg',msg)
            render result as JSON
        }else{
            result.put('msg',msg)
            render result as JSON
        }
    }

    /**
     * @Description 撤销合格证信息
     * @Create 2013-11-02 huxx
     */
    def  uploadDelete(){
        def map=[:]
        if (!params.ids){
           map.msg="请选择记录！"
        }else{
            def map1=zcInfoUploadService.delete(params.ids,grailsApplication,params.reason)
            map.msg=map1.msg
        }
        render map  as JSON
    }
    /**
     * @Description 修改合格证信息
     */
    def uploadUpdate(){
        def result=[:]
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        String noservicezcinfo=""  //由于服务连接不好未能够成功上传的合格证编号
        def idsStr=params.ids
        def map= zcInfoUploadService.upload(idsStr,loginUser,"3",grailsApplication,"update",params.reason)

        String msg=map.msg
        int succount=map.succount
        int failcount=map.failcount
        def flag=map.flag
        String failedzcinfo=map.failedzcinfo //上传失败的合格证编号
        if (flag){
            if (failcount==0){
                msg=succount+"条合格证申请全部修改成功!"
            }else{
                msg =  succount+"条合格证申请修改成功,"+failcount+"条合格证申请修改失败,"
                if (failedzcinfo!=""){
                    msg= msg+ "修改失败的合格证编号为:"+ failedzcinfo+"."
                }
                if (noservicezcinfo!="") {
                    msg= msg+ "由于服务没有配置好,未能够补传的合格证编号为:"+ noservicezcinfo+"."
                }

            }
            result.put('msg',msg)
            render result as JSON
        }else{
            result.put('msg',msg)
            render result as JSON
        }
    }

    /**
     * @Description 需要撤销或修改页面的合格证上传方法
     * @return
     * @Create 2013-10-29 huxx
     */
    def upload(){
        def result=[:]
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        String noservicezcinfo=""  //由于服务连接不好未能够成功上传的合格证编号
        def idsStr=params.ids
        def map= zcInfoUploadService.upload(idsStr,loginUser,"3",grailsApplication,"insert","")

        String msg=map.msg
        int succount=map.succount
        int failcount=map.failcount
        def flag=map.flag
        String failedzcinfo=map.failedzcinfo //上传失败的合格证编号
        if (flag){
            if (failcount==0){
                msg=succount+"条合格证申请全部上传成功!"
            }else{
                msg =  succount+"条合格证申请上传成功,"+failcount+"条合格证申请上传失败,"
                if (failedzcinfo!=""){
                    msg= msg+ "上传失败的合格证编号为:"+ failedzcinfo+"."
                }
                if (noservicezcinfo!="") {
                    msg= msg+ "由于服务没有配置好,未能够上传的合格证编号为:"+ noservicezcinfo+"."
                }

            }
            result.put('msg',msg)
            render result as JSON
        }else{
            result.put('msg',msg)
            render result as JSON
        }
    }
    /**
     * @Description 需要撤销或修改页面的合格证上传方法
     * @return
     * @Create 2013-11-14 liuly
     */
    def finish(){
        def result=[:]
        def ids = params.ids
        def idsArray = ids.split('_')
        def flag = true
        def msg = message(code: 'default.finish.simple.message')
        idsArray.each {
            def zcinfoInstance = ZCInfo.get(it)
            if (!zcinfoInstance) {
                return
            }
            try {
                zcinfoInstance.delete(flush: true)
                def zcinfoInstance1=new ZCInfo(zcinfoInstance.properties)
                zcinfoInstance1.web_status=3
                zcinfoInstance1.updateTime=DateUtil.getCurrentTime()
                zcinfoInstance1.updaterId=getCurrentUser()?.userName
                zcinfoInstance1.save(flush:true)
            }catch (DataIntegrityViolationException e) {
                flag = false
            }
            if(!flag){
                msg = message(code: 'default.not.simple.finish.message')
            }
        }
        result.put('msg',msg)
        render result as JSON
    }
}