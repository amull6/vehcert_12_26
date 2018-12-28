package cn.com.wz.stvehcert

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import cn.com.wz.stvehcert.TractorModle
import grails.converters.JSON
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils
import parent.poi.event.ExcelReaderUtil
import parent.poi.event.IRowReader
import parent.poi.event.RowReader

class TractorModleController extends BaseController{
//这个地方不注释掉会用submit方式提交表单出现405错误
//    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def tractorModleService
    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        render(view: '/cn/com/wz/stvehcert/tractorModle/list',model:[params:params])
    }
    /**
     * @Description 配货地址list的初始数据查询方法
     * @Create_time 2016-05-10 zhuwi
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def cel={
            if(params.modleName){
                like("modleName","%${params.modleName}%")
            }
            if (params.modle){
                eq('modle',"${params.modle}")
            }
            if (params.veh_Fdjxh){
                eq('veh_Fdjxh',"${params.veh_Fdjxh}")
            }
            if (params.car_storage_no1&&params.car_storage_no2){
                between("car_storage_no",params.car_storage_no1,params.car_storage_no2)
            }else{
                if (params.car_storage_no1&&!params.car_storage_no2){
                    ge("car_storage_no",params.car_storage_no1)
                }else if (!params.car_storage_no1&&params.car_storage_no2){
                    le("car_storage_no",params.car_storage_no2)
                }
            }
            order 'createTime','desc'
        }
        def rows=TractorModle.createCriteria().list(params,cel)

        def lst=[]
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.modle=u.modle
            m.modleName=u.modleName
            m.createid=u.createid
            m.createTime=u.createTime
            m.lastUpdateId=u.lastUpdateId
            m.lastUpdateTime=u.lastUpdateTime
            m.veh_Jxdl=u.veh_Jxdl
            m.veh_Rllx=u.veh_Rllx
            m.veh_Jxxlb=u.veh_Jxxlb
            m.veh_Zycs=u.veh_Zycs
            m.veh_Pfjd=u.veh_Pfjd
            m.veh_Fdjxh=u.veh_Fdjxh
            m.car_storage_no=u.car_storage_no
            m.modle_type=u.modle_type
            lst.add(m)
        }
        def result = [rows:lst,total:rows.totalCount]
        render result as JSON
    }
    /**
     * Description 创建跳转方法
     * @CreatetTime 2016-06-13 by zhuwei
     * @return
     */
    def create() {
        def tractorModleInstance=new TractorModle(params)
        render (view:'/cn/com/wz/stvehcert/tractorModle/create', model:[tractorModleInstance: tractorModleInstance])
    }
    /**
     * Description 保存跳转方法
     * @CreatetTime 2016-06-13 by zhuwei
     * @return
     */
    def save() {
        def tractorModleInstance = new TractorModle(params)
        tractorModleInstance.createTime= DateUtil.getCurrentTime()
        tractorModleInstance.createid= User.get(getCurrentUser()?.id)?.userName
        if (!tractorModleInstance.save(flush: true)) {
            render(view: '/cn/com/wz/stvehcert/tractorModle/create',  model: [tractorModleInstance: tractorModleInstance])
            return
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'tractorModle.label', default: 'TractorModle'), tractorModleInstance.id])
        redirect(action: "show", id: tractorModleInstance.id)
    }
    /**
     * @description show 方法
     * CreateTime 2016-06-14 by zhuwei
     * @return
     */
    def show() {
        def tractorModleInstance = TractorModle.get(params.id)
        if (!tractorModleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'tractorModle.label', default: 'TractorModle'), params.id])
            redirect(action: "list")
            return
        }
        render (view:'/cn/com/wz/stvehcert/tractorModle/show', model:  [tractorModleInstance: tractorModleInstance])

    }
    /**
     * @description 编辑方法
     * CreateTime 2016-06-14 by zhuwei
     * @return
     */
    def edit() {
        def tractorModleInstance = TractorModle.get(params.id)
        if (!tractorModleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tractorModle.label', default: 'TractorModle'), params.id])
            redirect(action: "list")
            return
        }
        render (view:'/cn/com/wz/stvehcert/tractorModle/edit',model: [tractorModleInstance: tractorModleInstance])

    }
    /**
     * @Description 进入导入页面
     * @return
     * @Create 2013-07-29 huxx
     */
    def importPage(){
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'tractorModle',action: 'imp')}",tabId:params.tabId])
    }
    /**
     * @Description 公告信息导入
     * @param filePath
     * @return
     * @Create 2013-07-29 huxx
     * @Update 2014-07-25 zhuwei
     * @update 改用SQL导入公告
     */
    def imp(String filePath){

        def realPath=""
        def filePaths=filePath.split(':_:')
        if(filePaths?.length>=2){
            //获取文件的相对路径(不包含文件名)
            def picRelativePath=UploadUtil.getRelativePathByUploadType(filePaths[0])
            def fileNames=filePaths[1].split(":;:")
            if(fileNames.length==3){
                realPath= UploadUtil.getRootDir(servletContext)+fileNames[2]
            }
        }

        List<String> labels=new ArrayList<String>();
        def lst=[]
        def totalCount=0  //读取记录总数
        def count=0                 //实际存储成功的记录数
        def failList=[]             //保存失败记录
        def checkResult
        StringBuilder errorMessages=new StringBuilder()
        def timeCount=0   //记录导入使用的时间
        def emptyCount     //记录导入公告中公告唯一码为空的个数
        def existsCarStorageNoList  //记录重复的公告唯一码的数列
        def turnOffCarStorage   //导入公告中已经停用的公告数组

        try{
            //获得服务器信息，这里获取程序本地的数据库，而非远程数据库
            def dsp=PropertiesLoaderUtils.loadAllProperties("db.properties")
            def remoteUrl= dsp.getProperty("url")
            def remoteUserName=dsp.getProperty("userName")
            def remotePassword= dsp.getProperty("password")
            def remoteDriverClassName=dsp.getProperty("driverClassName")
            if (!remoteUrl || !remoteUserName || !remotePassword || !remoteDriverClassName){
                return render("服务器信息配置不完整！")
            }
            def remoteDB=groovy.sql.Sql.newInstance("${remoteUrl}","${remoteUserName}","${remotePassword}","${remoteDriverClassName}")
            def carStorageMaxCount=Integer.parseInt(grailsApplication.config.client.impCarStorage.maxCount?grailsApplication.config.client.impCarStorage.maxCount:'0')
            //获取Excle内容信息
            labels.add('modle');
            labels.add('modleName');
            labels.add('veh_Fdjxh');
            labels.add('veh_Jxdl');
            labels.add('veh_Rllx');
            labels.add('veh_Jxxlb');
            labels.add('veh_Zycs');
            labels.add('veh_Pfjd');
            labels.add('modle_type');
            labels.add('car_storage_no');
            def map=[:];
            map.put('startRow',1) ;//从第二行开始
            IRowReader reader = new RowReader(labels,map);
            lst=ExcelReaderUtil.readExcel(reader, realPath);

            totalCount=lst?.size()  //读取记录总数
            def user=getCurrentUser()
            def time=DateUtil.getCurrentTime()
            def paramsMap=[:]
            paramsMap.lst=lst
            paramsMap.remoteDB=remoteDB
            paramsMap.carStorageMaxCount=carStorageMaxCount
            paramsMap.time=time
            paramsMap.user=user
            if(totalCount>0){
                //对list里面的的公告唯一码进行校验，如果没有查到相同公告唯一码的公告，就做公告导入处理
                println('在这里面进行公告唯一号校验')
                checkResult=tractorModleService.checkCarStorageNo(lst)   //不再校验公告唯一号的唯一性
                emptyCount= checkResult.emptyCount
                turnOffCarStorage=checkResult.turnOffCarStorage
                paramsMap.existsCarStorageNoList=checkResult.existsCarStorageNoList
                if(emptyCount==0&&turnOffCarStorage.size()==0){ // 导入时不再校验公告唯一号是不是唯一的，只校验公告是不是空的、公告是不是已经停用
                    def m=tractorModleService.newSqlSaveList(paramsMap,request,grailsAttributes)
                    timeCount=m.timeCount
                    count = lst.size()
                }else{
                    if(turnOffCarStorage.size()>0){
                        errorMessages.append("公告库中已经存在公告唯一号为${turnOffCarStorage}的停用公告，")
                    }
                    if(emptyCount>0){
                        errorMessages.append("导入公告中有${emptyCount}条公告唯一号为空，")
                    }
                }
            }

        }catch(Exception e){
            totalCount = -1
            count = 0
            errorMessages.append("数据插入时出现问题！数据全部回滚！错误原因：${e.cause}")
            e.cause?e.cause.printStackTrace():e.printStackTrace()
        }finally{
            lst.clear()
            labels.clear()
            failList.clear()
            def result=[realCount:count,count:totalCount,errorMessages:errorMessages,timeCount:timeCount]
            render result as JSON
        }

    }
    /**
     *@Description 山拖公告信息查询页面导出
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-28 liuly
     *@UpdateBy zhuwei 2014_07_25
     *@UpdateBy QJ 2014_07_19
     *@导出公告内容添加公告唯一号导出农装公告
     */
    def exportSearch(){
        def rows =[]
        def content=[]
        try{
            if(params?.format && params.format != "html"){
                def encodedFilename = URLEncoder.encode(("分解后公告信息"), "UTF-8")
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
                //List fields = ["veh_Clzzqymc","veh_Qyid", "veh_Clfl","veh_Clmc","veh_Clpp","veh_Clxh","veh_Csys","veh_Dpxh","veh_Dpid","veh_cpid","veh_Ggpc","veh_qxhx","veh_Fdjxh","veh_Rlzl","veh_Pl","veh_Gl","veh_Zxxs","veh_Qlj","veh_Hlj","veh_Lts","veh_Ltgg","veh_Gbthps","veh_Zj","veh_Zh","veh_Zs","veh_Wkc","veh_Wkk","veh_Wkg","veh_Hxnbc","veh_Hxnbk","veh_Hxnbg","veh_Zzl","veh_Edzzl","veh_Zbzl","veh_Zzllyxs","veh_Zqyzzl","veh_Edzk","veh_Bgcazzdyxzzl","veh_Jsszcrs","veh_Hzdfs","veh_Cpggh","veh_Ggsxrq","veh_jjlqj","veh_dpqy","veh_Zgcs","veh_Yh","veh_Bz","veh_Qybz","veh_Cpscdz","veh_Qyqtxx","veh_Pfbz","veh_Clztxx","veh_Jss","veh_Jsslx","veh_fgbsqy","veh_fgbsxh","veh_fgbssb","veh_y45pic","veh_zhpic","veh_fhpic","veh_pic1","veh_pic2","veh_other"]
                Map labels =  ["modle":"车型编码","modleName":"车型名称","veh_Fdjxh":"发动机型号","veh_Jxdl":"机械大类","veh_Rllx":"燃料类型", "veh_Jxxlb":"机械小类别","veh_Zycs":"主要参数","veh_Pfjd":"排放阶段","modle_type":"类型",
                       "car_storage_no":"公告唯一号"]
                def upperCase = { domain, value ->
                    return value.toUpperCase()
                }
                def out=response.outputStream
                def map=tractorModleService.selectStCarStorageByParams(params)

                ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
                def m=[]
                m.add(labels)
                content.add(map.rows)
                map.clear()
                excelOp.preWriteExcel(out,null,m,["分解后公告信息"],content)
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
     * @description Update方法
     * CreateTime 2016-06-14 by zhuwei
     * @return
     */
    def update() {
        def tractorModleInstance = TractorModle.get(params.id)
        if (!tractorModleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tractorModle.label', default: 'TractorModle'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (tractorModleInstance.version > version) {
                tractorModleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tractorModle.label', default: 'TractorModle')] as Object[],
                          "Another user has updated this TractorModle while you were editing")
                render(view: "/cn/com/wz/stvehcert/tractorModle/edit", model: [tractorModleInstance: tractorModleInstance])
                return
            }
        }

        tractorModleInstance.properties = params
        tractorModleInstance.lastUpdateTime= DateUtil.getCurrentTime()
        tractorModleInstance.lastUpdateId= User.get(getCurrentUser()?.id)?.userName
        if (!tractorModleInstance.save(flush: true)) {
            render(view: "/cn/com/wz/stvehcert/tractorModle/edit", model: [tractorModleInstance: tractorModleInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'tractorModle.label', default: 'TractorModle'), tractorModleInstance.id])
        redirect(action: "show", id: tractorModleInstance.id)
    }

    /**
     * @Descripion 删除方法
     * @Create_time 2016-05-10 zhuwei
     * @return
     */
    def jsonDelete(){
        def ids = params.deleteIds
        def idsArray = ids.split('_')
        def flag = true
        def msg = message(code: 'default.deleted.simple.message')
        idsArray.each {
            def tractorModlInstance = TractorModle.get(it)
            if (!tractorModlInstance) {
                //msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
                return
            }
            try {
                tractorModlInstance.delete(flush: true)
                //msg << message(code: 'default.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
            }catch (DataIntegrityViolationException e) {
                //msg << message(code: 'default.not.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
                flag = false
            }
            if(!flag){
                msg = message(code: 'default.not.simple.deleted.message')
            }
        }
        render msg
    }
}
