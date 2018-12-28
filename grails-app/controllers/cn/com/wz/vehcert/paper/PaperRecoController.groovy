package cn.com.wz.vehcert.paper

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.zcinfo.ZCInfoReplace
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils;
/**
 *@Description 合格证纸张管理-回收
 *@Auther liuly
 *@createTime 2013-08-02
 */
class PaperRecoController extends BaseController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def exportService
    def sqlService
    def paperService
    def index() {
        redirect(action: "list", params: params)
    }

    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def list() {
        render (view: "/cn/com/wz/vehcert/paper/paperreco/index",model: params)
    }
    /**
     *@Description 用于生成表格数据   获取满足条件的用户信息列表
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        //回收状态
        def status='0'
        if (params.status){
            status=params.status
        }
        def lst=[]
        def rows=[]
        print params
        def dealerNum=''
        if (params.dealerNum){
            dealerNum=params.dealerNum
        }
        def dealerName=''
        if (params.dealerName){
            dealerName=params.dealerName
        }
        //未回收
        if (status=='0'){
            //查询的经销商编号和名称
            StringBuffer sql_sbf=new StringBuffer()
            sql_sbf = new StringBuffer("SELECT id from sys_user s where s.user_name LIKE '%"+dealerNum+"%' and s.id in (select user_id from sys_userdetail d where d.real_name  LIKE '%"+dealerName+"%')");
            def list = sqlService.executeList(sql_sbf.toString())
            if (dealerName==''&&dealerNum==''){
                list = []
            }
            def createrId=[]
            if(list.size()>0){
                list.each{
                    createrId.add(it.id)
                }
            }
            def cel={
                if(params.dealerNum||params.dealerName){
                    inList("createrId",createrId)
                }

                if (params.paperNum){
                    like("veh_Zzbh","%${params.paperNum}%")
                }
                if(params.type&&params.type!='all'){
                    print '222222222222222222222222222222222222222222222222222222'
                       if (params.type=='0'){
                           eq('veh_Type','0')
                       }else if (params.type=='1'){
                           eq('veh_Type','1')
                           eq('veh_Clztxx','QX')
                       }else{
                           eq('veh_Type','1')
                           eq('veh_Clztxx','DP')
                       }
                }
                eq("veh_coc_status",1)
                eq('statusReco',0)
                isNotNull("veh_Zzbh")
            }
            rows=ZCInfoReplace.createCriteria().list(params,cel)
            rows?.each {u->
                def m=[:]
                m.id=u.id
                def userL=User.get(u.createrId)
                if(userL){
                    m.dealerNum=userL.userName
                    m.dealerName=userL.userDetail.realName
                }else{
                    m.dealerNum=''
                    m.dealerName=''
                }
                if (u.veh_Type=='0'){
                    m.type='汽车整车'
                }else if (u.veh_Type=='1'){
                    if (u.veh_Clztxx=='QX'){
                        m.type='农用车整车'
                    }else{
                        m.type='二类底盘'
                    }
                }
                m.paperNum =u.veh_Zzbh
                m.note =u.veh_Bz
                m.status='未回收'
                m. recoverTime=''
                m. recoverId=''
                lst.add(m)
            }
        }
       //已回收
        if (status=='1') {
            def celR={
                if(params.dealerNum){
                    like("dealerId","%${params.dealerNum}%")
                }
                if (params.dealerName) {
                    like("dealerName","%${params.dealerName}%")
                }

                if (params.paperNum){
                    like("paperNum","%${params.paperNum}%")
                }
                if(params.type&&params.type!='all'){
                    eq('type',Integer.parseInt(params.type))
                }
            }
            rows=PaperReco.createCriteria().list(params,celR)

            rows?.each {u->
                def m=[:]
                m.id=u.id
                m.dealerNum=u.dealerId
                m.dealerName=u.dealerName
                m.paperNum =u.paperNum
                if(u.type==0){
                    m.type='汽车整车'
                }else if(u.type==1){
                    m.type='农用车整车'
                }else{
                    m.type='二类底盘'
                }
                m.note =u.note
                m.status='已回收'
                m. recoverTime=u.recoverTime
                def userR=User.get(u.recoverId)
                m. recoverId=userR.userDetail.realName
                lst.add(m)
            }
        }

        def result = [rows:lst,total:rows.totalCount]
        render result as JSON
    }
    /**
     *@Description 用于生成表格数据   获取满足条件的用户信息列表
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-20
     */
    def recover(){
        def zcinfoReplace =ZCInfoReplace.get(params.id)
        zcinfoReplace.statusReco=1
        def paperReco=new PaperReco()
        if (zcinfoReplace.veh_Type=='0'){
            paperReco.type=0
        }else if (zcinfoReplace.veh_Type=='1'){
            if (zcinfoReplace.veh_Clztxx=='QX'){
                paperReco.type=1
            }else{
                paperReco.type=2
            }
        }
        def user= User.get(zcinfoReplace.createrId)
        paperReco.dealerId=user.userName
        paperReco.dealerName=user.userDetail.realName
        paperReco.paperNum=zcinfoReplace.veh_Zzbh
        paperReco.note=zcinfoReplace.veh_Bz
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        paperReco.recoverId= loginUser.id
        paperReco.replaceId= zcinfoReplace.id
        paperReco.recoverTime=DateUtil.getCurrentTime()
        def msg=''
        if (paperReco.save(flush: true)&&zcinfoReplace.save(flush: true)){
            msg='回收成功！'
        }else{
            msg='回收失败！'
        }
        render msg
    }
    /*  *
       *@Description 导出功能
       *@param
       *@return
       *@Auther liuly
       *@createTime 2013-03-19*/

    def export(){
        def rows =[]
        def lst=[]
        def content=[]
        try{
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("纸张回收"), "UTF-8")
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
            //List fields = ["num","dealerNum", "dealerName","paperNum","status","recoverId","recoverTime","note"]
            Map labels =  ["num":"序号","dealerNum":"经销商编号", "dealerName":"经销商姓名","paperNum":"纸张编号","type":"合格证类型：0.汽车整车，1.农用车整车,2.二类底盘","status":"回收状态","recoverId":"回收人","recoverTime":"回收时间","note":"备注"]
            def out=response.outputStream
            def i=1
            def dealerNum=''
            if (params.dealerNum){
                dealerNum=params.dealerNum
            }
            def dealerName=''
            if (params.dealerName){
                dealerName=params.dealerName
            }
            //未回收
            if (params.status=='0'){
                //查询的经销商编号和名称
                StringBuffer sql_sbf=new StringBuffer()
                sql_sbf = new StringBuffer("SELECT id from sys_user s where s.user_name LIKE '%"+dealerNum+"%' and s.id in (select user_id from sys_userdetail d where d.real_name  LIKE '%"+dealerName+"%')");
                def list = sqlService.executeList(sql_sbf.toString())
                if (dealerName==''&&dealerNum==''){
                    list = []
                }
                def createrId=[]
                if(list.size()>0){
                    list.each{
                        createrId.add(it.id)
                    }
                }
                def cel={
                    if(params.dealerNum||params.dealerName){
                        inList("createrId",createrId)
                    }

                    if (params.paperNum){
                        like("veh_Zzbh","%${params.paperNum}%")
                    }
                    if(params.type&&params.type!='all'){
                        if (params.type=='0'){
                            eq('veh_Type','0')
                        }else if (params.type=='1'){
                            eq('veh_Type','1')
                            eq('veh_Clztxx','QX')
                        }else{
                            eq('veh_Type','1')
                            eq('veh_Clztxx','DP')
                        }
                    }
                    eq("veh_coc_status",1)
                    eq('statusReco',0)
                    isNotNull("veh_Zzbh")
                }
                rows=ZCInfoReplace.createCriteria().list(params,cel)
                rows?.each {u->
                    def m=[:]
                    m.num=i
                    m.id=u.id
                    def user=User.get(u.createrId)
                    if (user){
                        m.dealerNum=user.userName
                        m.dealerName=user.userDetail.realName
                    }else{
                        m.dealerNum=''
                        m.dealerName=''
                    }
                    if (u.veh_Type=='0'){
                        m.type='汽车整车'
                    }else if (u.veh_Type=='1'){
                        if (u.veh_Clztxx=='QX'){
                            m.type='农用车整车'
                        }else{
                            m.type='二类底盘'
                        }
                    }
                    m.paperNum =u.veh_Zzbh
                    m.status='未回收'
                    m.note =u.veh_Bz
                    m. recoverTime=''
                    m. recoverId=''
                    i++
                    lst.add(m)
                }
            }
            if (params.status=='1'){
               def cel={
                   if(params.dealerNum){
                       like("dealerId","%${params.dealerNum}%")
                   }
                   if (params.dealerName) {
                       like("dealerName","%${params.dealerName}%")
                   }

                   if (params.paperNum){
                       like("paperNum","%${params.paperNum}%")
                   }
                   if(params.type&&params.type!='all'){
                       eq('type',Integer.parseInt(params.type))
                   }
               }
               rows=PaperReco.createCriteria().list(params,cel)
                rows?.each {u->
                    def m=[:]
                    m.num=i
                    m.id=u.id
                    m.dealerNum=u.dealerId
                    m.dealerName=u.dealerName
                    m.paperNum =u.paperNum
                    m.note =u.note
                    m.status='已回收'
                    if(u.type==0){
                        m.type='汽车整车'
                    }else if(u.type==1){
                        m.type='农用车整车'
                    }else{
                        m.type='二类底盘'
                    }
                    m. recoverTime=u.recoverTime
                    def user=User.get(u.recoverId)
                    m. recoverId=user.userDetail.realName
                    i++
                    lst.add(m)
                }
            }

            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["纸张回收"],content)
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
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }
}
