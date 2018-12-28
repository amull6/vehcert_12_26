package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.carstorage.PreCarStorage
import grails.converters.JSON
import parent.poi.ExcelUtils;

class ZCInfoTempController extends BaseController {
    def sqlService
    def exportService
    def zcinfoTempService

    def list()
    {
        render(view:'/cn/com/wz/vehcert/zcinfo/dealer/list')
    }

    /**
     * @Description 经销商(已下载合格证信息查询)
     * @Create 2013-08-04 zouQ
     */
    def jsonList(){
        ///当前登录用户
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        ///列表数据信息
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') :0
        params.sort='insertTime'
        params.order="desc"
       def sta = params.firstTime+" 00:00:00"
       def end = params.secondTime+" 23:59:59"
       def type = params.type
       def cel={
            if(params.firstTime){
            ge('insertTime',sta)
           }
            if(params.secondTime){
            le('insertTime',end)
           }
           if(params.type){
            eq('type',type)
           }
        eq('user_down',loginUser.id)
        order ("insertTime", "desc")
    }
        def rows=ZCInfoTemp.createCriteria().list(params,cel)
        def lst=[]
        int i=1

        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.veh_Type=u.veh_Type
            m.veh_Zchgzbh=u.veh_Zchgzbh
            m.veh_Dphgzbh=u.veh_Dphgzbh
            m.veh_Fzrq=u.veh_Fzrq
            m.veh_Clzzqymc=u.veh_Clzzqymc
            m.veh_Qyid=u.veh_Qyid
            m.veh_Clfl=u.veh_Clfl
            m.veh_Clmc=u.veh_Clmc
            m.veh_Clpp=u.veh_Clpp
            m.veh_Clxh=u.veh_Clxh
            m.veh_Csys=u.veh_Csys
            m.veh_Dpxh=u.veh_Dpxh
            m.veh_Dpid=u.veh_Dpid
            m.veh_Clsbdh=u.veh_Clsbdh
            m.veh_Cjh=u.veh_Cjh
            m.veh_Fdjh=u.veh_Fdjh

            m.veh_Fdjxh=u.veh_Fdjxh
            m.veh_Rlzl=u.veh_Rlzl
            m.veh_Pl=u.veh_Pl
            m.veh_Gl=u.veh_Gl
            m.veh_zdjgl=u.veh_zdjgl
            m.veh_Zxxs=u.veh_Zxxs
            m.veh_Qlj=u.veh_Qlj
            m.veh_Hlj=u.veh_Hlj
            m.veh_Lts=u.veh_Lts
            m.veh_Ltgg=u.veh_Ltgg
            m.veh_Gbthps=u.veh_Gbthps
            m.veh_Zj=u.veh_Zj
            m.veh_Zh=u.veh_Zh
            m.veh_Zs=u.veh_Zs
            m.veh_Wkc=u.veh_Wkc
            m.veh_Wkk=u.veh_Wkk
            m.veh_Wkg=u.veh_Wkg
            m.veh_Hxnbc=u.veh_Hxnbc
            m.veh_Hxnbk=u.veh_Hxnbk
            m.veh_Hxnbg=u.veh_Hxnbg
            m.veh_Zzl=u.veh_Zzl
            m.veh_Edzzl=u.veh_Edzzl
            m.veh_Zbzl=u.veh_Zbzl
            m.veh_Zzllyxs=u.veh_Zzllyxs
            m.veh_Zqyzzl=u.veh_Zqyzzl
            m.veh_Edzk=u.veh_Edzk
            m.veh_Bgcazzdyxzzl=u.veh_Bgcazzdyxzzl
            m.veh_Jsszcrs=u.veh_Jsszcrs
            m.veh_Qzdfs=u.veh_Qzdfs
            m.veh_Hzdfs=u.veh_Hzdfs
            m.veh_Qzdczfs=u.veh_Qzdczfs
            m.veh_Cpggh=u.veh_Cpggh
            m.veh_Ggsxrq=u.veh_Ggsxrq
            m.veh_Zzbh=u.veh_Zzbh
            m.veh_Dywym=u.veh_Dywym
            m.veh_Zgcs=u.veh_Zgcs
            m.veh_Clzzrq=u.veh_Clzzrq
            m.veh_Bz=u.veh_Bz
            m.veh_Qybz=u.veh_Qybz
            m.veh_Cpscdz=u.veh_Cpscdz
            m.veh_Qyqtxx=u.veh_Qyqtxx
            m.veh_Pfbz=u.veh_Pfbz
            m.veh_Clztxx=u.veh_Clztxx
            m.veh_Jss=u.veh_Jss
            m.veh_Jsslx=u.veh_Jsslx
            m.veh_Zchgzbh_0=u.veh_Zchgzbh_0
            m.used=u.used
            m.used2=u.used2
            m.upload=u.upload
            m.veh_Yh=u.veh_Yh
            m.veh_VinFourBit=u.veh_VinFourBit
            m.veh_Ggpc=u.veh_Ggpc

            m.createTime=u.createTime
            m.updateTime=u.updateTime
            m.type=u.type
            def userC=User.get(u.createrId)
            if (userC){
                m.createrId=userC.userDetail.realName
            }else{
                m.createrId=''
            }
            m.updateTime=u.updateTime
            def userU=User.get(u.updaterId)
            if (userU){
                m.updaterId=userU.userDetail.realName
            }else{
                m.updaterId=''
            }
            m.insertTime = u.insertTime
            def userD=User.get(u.user_down)
            if (userD){
                m.user_down=userD.userDetail.realName
            }else{
                m.user_down=''
            }
            i++;

           lst.add(m)

        }
        def result = [rows:lst,total:rows.totalCount]
        render result as JSON
    }

    ////经销商 (已下载合格证信息查询) 导出
    /**
     *  @ author zouq
     */
    def export_search_is(){
        ///当前登录用户
        def rows =[]
        def lst=[]
        def content=[]
        try{
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        print params
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("已下载合格证信息(经销商)"), "UTF-8")
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

            Map labels = ["veh_Zchgzbh":"整车合格证编号","veh_Dphgzbh":"底盘合格证编号","veh_Fzrq":"发证日期","veh_Clzzqymc":"车辆制造企业名称","createTime":"创建时间","insertTime":"下载时间","veh_Qyid":"企业ID","veh_Clfl":"车辆分类","veh_Clmc":"车辆名称"
                    ,"veh_Clpp":"车辆品牌","veh_Clxh":"车辆型号","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_Clsbdh":"车辆识别代号","veh_Cjh":"车架号","veh_Fdjh":"发动机号"
                    ,"veh_Fdjxh":"发动机型号","veh_Rlzl":"燃料种类","veh_Pl":"排量","veh_Gl":"功率","veh_zdjgl":"最大功率","veh_Zxxs":"转向形式","veh_Qlj":"前轮距","veh_Hlj":"后轮距","veh_Lts":"轮胎数","veh_Ltgg":"轮胎规格"
                    ,"veh_Gbthps":"钢板弹簧片数","veh_Zj":"轴距","veh_Zh":"轴荷","veh_Zs":"轴数","veh_Wkc":"外廓长","veh_Wkk":"外廓宽","veh_Wkg":"外廓高","veh_Hxnbc":"货箱内部长","veh_Hxnbk":"货箱内部宽"
                    ,"veh_Hxnbg":"货箱内部高","veh_Zzl":"总质量","veh_Edzzl":"额定载质量","veh_Zbzl":"整备质量","veh_Zzllyxs":"载质量利用系数","veh_Zqyzzl":"准牵引总质量","veh_Edzk":"额定载客","veh_Bgcazzdyxzzl":"半挂车按座","veh_Jsszcrs":"驾驶室准乘人数"
                    ,"veh_Qzdfs":"前制动方式","veh_Hzdfs":"后制动方式","veh_Qzdczfs":"前制动操作方式","veh_Hzdczfs":"后制动操作方式","veh_Cpggh":"产品公告号","veh_Ggsxrq":"公告生效日期","veh_Zzbh":"纸张编号"
                    ,"veh_Dywym":"打印唯一码","veh_Zgcs":"最高车速","veh_Clzzrq":"车辆制造日期","veh_Qybz":"企业标准","veh_Cpscdz":"产品生产地址","veh_Qyqtxx":"企业其它信息","veh_Pfbz":"排放标准","veh_Clztxx":"车辆状态信息","veh_Clxh":"车辆型号"
                    ,"veh_Jss":"驾驶室","veh_Jsslx":"驾驶室类型","veh_VinFourBit":"vin第四位","updaterId":"更新人","updateTime":"最后更新时间","veh_Bz":"备注"]
            def out=response.outputStream

            def sta = params.firstTime+" 00:00:00"
            def end = params.secondTime+" 23:59:59"
            params.sort='insertTime'
            params.order="desc"
            def cel={
                if(params.firstTime){
                    ge('insertTime',sta)
                }
                if(params.secondTime){
                    le('insertTime',end)
                }
                eq('user_down',loginUser.id)
            }
            rows=ZCInfoTemp.createCriteria().list (params,cel)
            rows.each {
                def m=[:]
                it.properties.each {
                    m."${it.key}"=it.value
                }
                lst.add(m)
            }
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["已下载合格证信息(经销商)"],content)
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


    ////经销商 (合格证上传申请) 导出
    /**
     *  @ author zouq
     */
    def export_search_load(){
        def rows =[]
        def lst=[]
        def content=[]
        try{
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        print params
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("合格证证上传申请(经销商)"), "UTF-8")
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
            //List fields = ["insertTime","veh_Zchgzbh","veh_Fzrq","veh_Clfl","veh_Clmc","veh_Clxh","veh_Csys","veh_Dpxh","veh_Dpid","veh_Clsbdh","veh_Cjh","veh_Fdjh" ]

            Map labels = ["insertTime":"下载时间","veh_Zchgzbh":"整车合格证编号","veh_Fzrq":"发证日期","veh_Clfl":"车辆分类","veh_Clmc":"车辆名称",
                    "veh_Clxh":"车辆型号","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_Clsbdh":"车辆识别代码","veh_Cjh":"车架号","veh_Fdjh":"发动机号"]
            def out=response.outputStream

            def sta = params.firstTime+" 00:00:00"
            def end = params.secondTime+" 23:59:59"
            params.sort='insertTime'
            params.order="desc"
            def cel = {
                eq("web_status","0")  ////未上传
                eq("user_down",loginUser.id)
                if(params.firstTime){
                    ge('insertTime',sta)
                }
                if(params.secondTime){
                    le('insertTime',end)
                }
            }
            rows=ZCInfoTemp.createCriteria().list (params,cel)
            rows.each {
                def m=[:]
                it.properties.each {
                    m."${it.key}"=it.value
                }
                lst.add(m)
            }
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["合格证上传申请(经销商)"],content)
            session.setAttribute('compFlag',"success")
            content.clear()
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
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }
    /**
     * @Description 营销公司查询所有的已下载合格证信息查询
     * @Create 2013-09-04 liuly
     */
    def jsonListAll(){
        ///列表数据信息
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') :0
        params.sort='insertTime'
        params.order="desc"
        def result=zcinfoTempService.search(params)
        render result as JSON
    }
    /**
     *@Descriptions 营销公司查询所有的已下载合格证信息查询
     * @Auther liuly
     * @createTime  2013-09-04
     */
    def searchAll(){
        render(view:'/cn/com/wz/vehcert/zcinfo/dealer/listSearch')
    }
    /**
     *@Descriptions 导出所有的已下载合格证信息
     * @Auther liuly
     * @createTime  2013-09-04
     */
    def export_search_all(){
        def rows =[]
        def content=[]
        try{
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("已下载合格证信息"), "UTF-8")
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

            Map labels = ["veh_Zchgzbh":"整车合格证编号","veh_Dphgzbh":"底盘合格证编号","veh_Fzrq":"发证日期","veh_Clzzqymc":"车辆制造企业名称","createTime":"创建时间","insertTime":"下载时间","user_down":"下载人","veh_Qyid":"企业ID","veh_Clfl":"车辆分类","veh_Clmc":"车辆名称"
                    ,"veh_Clpp":"车辆品牌","veh_Clxh":"车辆型号","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_Clsbdh":"车辆识别代号","veh_Cjh":"车架号","veh_Fdjh":"发动机号"
                    ,"veh_Fdjxh":"发动机型号","veh_Rlzl":"燃料种类","veh_Pl":"排量","veh_Gl":"功率","veh_zdjgl":"最大功率","veh_Zxxs":"转向形式","veh_Qlj":"前轮距","veh_Hlj":"后轮距","veh_Lts":"轮胎数","veh_Ltgg":"轮胎规格"
                    ,"veh_Gbthps":"钢板弹簧片数","veh_Zj":"轴距","veh_Zh":"轴荷","veh_Zs":"轴数","veh_Wkc":"外廓长","veh_Wkk":"外廓宽","veh_Wkg":"外廓高","veh_Hxnbc":"货箱内部长","veh_Hxnbk":"货箱内部宽"
                    ,"veh_Hxnbg":"货箱内部高","veh_Zzl":"总质量","veh_Edzzl":"额定载质量","veh_Zbzl":"整备质量","veh_Zzllyxs":"载质量利用系数","veh_Zqyzzl":"准牵引总质量","veh_Edzk":"额定载客","veh_Bgcazzdyxzzl":"半挂车按座","veh_Jsszcrs":"驾驶室准乘人数"
                    ,"veh_Qzdfs":"前制动方式","veh_Hzdfs":"后制动方式","veh_Qzdczfs":"前制动操作方式","veh_Hzdczfs":"后制动操作方式","veh_Cpggh":"产品公告号","veh_Ggsxrq":"公告生效日期","veh_Zzbh":"纸张编号"
                    ,"veh_Dywym":"打印唯一码","veh_Zgcs":"最高车速","veh_Clzzrq":"车辆制造日期","veh_Qybz":"企业标准","veh_Cpscdz":"产品生产地址","veh_Qyqtxx":"企业其它信息","veh_Pfbz":"排放标准","veh_Clztxx":"车辆状态信息","veh_Clxh":"车辆型号"
                    ,"veh_Jss":"驾驶室","veh_Jsslx":"驾驶室类型","veh_VinFourBit":"vin第四位","updaterId":"更新人","updateTime":"最后更新时间","veh_Bz":"备注"]
           def out=response.outputStream
            rows=zcinfoTempService.search(params).rows
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(rows)
            excelOp.preWriteExcel(out,null,m,["已下载合格证信息"],content)
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
    def printCountList()
    {
        render(view:'/cn/com/wz/vehcert/zcinfo/dealer/printCount/PrintCount')
    }
    /**
     * @DEscription 更新经销商打印限制次数数据
     * @return
     */
    def checkCount() {
        def result=[:]
        def id = params.hidden_id;    ////要更新的合格证ID信息
        def zcinfoModel = ZCInfo.get(id)
        DistributorPrintCount distributorPrintCount = DistributorPrintCount.findByVeh_Zchgzbh_0(zcinfoModel.veh_Zchgzbh_0)
        if(distributorPrintCount){
            def limitPrintCount=distributorPrintCount.limitPrintCount
            def printCount=distributorPrintCount.printCount
            if(printCount+1>limitPrintCount){
                result.put("msg",'超过打印次数限制，如有需要请联系营销公司申请增加打印次数');
                result.put("flag",'0')
                return  render (result as JSON)
            }else{
                result.put("msg",'允许打印');
                result.put("flag",'1')
                return  render (result as JSON)
                }
        }else{
            result.put("msg",'允许打印');
            result.put("flag",'1')
            return  render (result as JSON)
        }
    }
    /**
     * @DEscription 更新经销商打印限制次数数据
     * @return
     */
    def updateCount() {
        def result = [:]
        def id = params.hidden_id;    ////要更新的合格证ID信息
        def zcinfoModel = ZCInfo.get(id)
        DistributorPrintCount distributorPrintCount = DistributorPrintCount.findByVeh_Zchgzbh_0(zcinfoModel.veh_Zchgzbh_0)
        if (distributorPrintCount) {
            def printCount = distributorPrintCount.printCount
            distributorPrintCount.printCount = printCount + 1
            if (distributorPrintCount.save(flush: true)) {
                result.put("msg", '更新打印次数成功');
                result.put("flag", '1')
                return render(result as JSON)
            }else{
                result.put("msg", '更新打印次数失败');
                result.put("flag", '0')
                return render(result as JSON)
            }
        } else {
            DistributorPrintCount newDistributorPrintCount = new DistributorPrintCount();
            newDistributorPrintCount.veh_Zchgzbh_0 = zcinfoModel.veh_Zchgzbh_0
            newDistributorPrintCount.veh_Clsbdh = zcinfoModel.veh_Clsbdh
            newDistributorPrintCount.veh_Type = zcinfoModel.veh_Type
            newDistributorPrintCount.printCount = 1
            newDistributorPrintCount.limitPrintCount = 1
            if (newDistributorPrintCount.save(flush: true)) {
                result.put("msg", '保存打印次数成功');
                result.put("flag", '1')
                return  render (result as JSON)
            }else{
                result.put("msg", '保存打印次数失败');
                result.put("flag", '0')
                return render(result as JSON)
            }
        }
    }
}
