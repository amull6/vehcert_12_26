package cn.com.wz.vehcert.environment

import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils;

/**
 *@Description 环保信息
 *@Auther liuly
 *@createTime 2013-08-04
 */
class EnvironmentController extends BaseController {
    //定义一些方法的提交方式
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def exportService
    def environmentService
    def index() {
        redirect(action: "list", params: params)
    }

    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05
     */
    def list() {
        render (view: "/cn/com/wz/vehcert/environment/index",model: params)
    }
    /**
     *@Description 用于生成表格数据   获取满足条件的用户信息列表
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def cel={
            if(params.veh_Clxh){
                like("veh_Clxh","%${params.veh_Clxh}%")
            }
            if (params.veh_Clmc) {
                like("veh_Clmc","%${params.veh_Clmc}%")
            }
            kind{
                if (params.kind){
                    like("code","%${params.kind}%")
                }
            }

        }
        def rows=Environment.createCriteria().list(params,cel)
        def lst=[]
        int i=1
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.num=i
            m.veh_Clxh=u.veh_Clxh
            m.veh_Clmc=u.veh_Clmc
            m.veh_Clfl=u.veh_Clfl
            m.veh_Fdjxh=u.veh_Fdjxh
            m.veh_Ggpx=u.veh_Ggpx
            m.veh_Bz=u.veh_Bz
            m.kind=u.kind.dicValueNameC
            i++;
            lst.add(m)
        }
        def result = [rows:lst,total:rows.totalCount]
        render result as JSON
    }
    /**
     *@Description 跳转到用户新建页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-04
     */
    def create() {
        def environmentInstance=new Environment(params)
        render (view:'/cn/com/wz/vehcert/environment/create', model:[environmentInstance: environmentInstance])
    }
    /**
     *@Description 保存新建信息
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-04
     */
    def save() {
        def environmentInstance=new Environment(params)

        if (!environmentInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/environment/create", model: [environmentInstance: environmentInstance])
            return
        }else{
            flash.message = message(code: 'default.created.message', args: [message(code: 'environment.label', default: 'Environment'), environmentInstance.veh_Clxh])
            redirect(action: "show", id: environmentInstance.id)
        }

    }

    /**
     *@Description 跳转到详情页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05
     */
    def show() {
        def environmentInstance = Environment.get(params.id)
        if (!environmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'environment.label', default: 'Environment'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/environment/show', model: [environmentInstance: environmentInstance])
    }

    /**
     *@Description 进入编辑页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05
     */
    def edit() {
        def environmentInstance = Environment.get(params.id)
        if (!environmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'environment.label', default: 'Environment'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/environment/edit',model:  [environmentInstance: environmentInstance])
    }

    /**
     *@Description 保存更新信息
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05
     */
    def update() {
        //判定要修改的记录是否存在
        def environmentInstance = Environment.get(params.id)
        if (!environmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'environment.label', default: 'Environment'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (environmentInstance.version > version) {
                environmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'environment.label', default: 'environment')] as Object[],
                        "Another user has updated this Post while you were editing")
                render(view: "/cn/com/wz/vehcert/environment/edit", model: [environmentInstance: environmentInstance])
                return
            }
        }

        environmentInstance.properties = params



        if (!environmentInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/environment/edit", model: [environmentInstance: environmentInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'environment.label', default: 'environment'), environmentInstance.veh_Clxh])
        render(view:'/cn/com/wz/vehcert/environment/show',model:[environmentInstance: environmentInstance])
    }
    /**
     *@Description 删除
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05
     */
    def jsonDelete(){
        def ids = params.deleteIds
        def idsArray = ids.split('_')
        def flag = true
        def msg = message(code: 'default.deleted.simple.message')
        idsArray.each {
            def environmentInstance = Environment.get(it)
            if (!environmentInstance) {
                //msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
                return
            }
            try {
                environmentInstance.delete(flush: true)
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
    /**
     *@Description 从excel中导入用户信息
     *@param  filePath:用户信息文件路径；
     *config_column_map 存储了参数信息。sheet从第几个sheet中取，从0开始；startRow从第几行开始读取，从0开始；
     *columnMap保存了列与实体属性的对应关系，第一列的列号为0.
     *@return
     *@Auther lly
     *@createTime 2013-08-05
     */
    def impEnvironment(String filePath){
        def realPath=""
        def filePaths=filePath.split(':_:')
        if(filePaths?.length>=2){
            //获取图片存储的相对路径(不包含文件名)
            def picRelativePath=UploadUtil.getRelativePathByUploadType(filePaths[0])
            def fileNames=filePaths[1].split(":;:")
            if(fileNames.length==3){
//                realPath=UploadUtil.getRelativeFile(servletContext, picRelativePath, fileNames[0])
                realPath= UploadUtil.getRootDir(servletContext)+fileNames[2]
            }
        }
        ExcelUtils excelOp=new ExcelUtils();
        Map config_column_map=[sheet:0,
                startRow:2,
                columnMap:[
                        '1':'veh_Clxh',
                        '2':'veh_Clmc',
                        '3':'veh_Clfl',
                        '4':'veh_Fdjxh',
                        '5':'veh_Ggpx',
                        '6':'veh_Bz',
                        '7':'kind'

                ]
        ]
        def lst=excelOp.readExcel(realPath, config_column_map)
        def totalCount=lst?.size()  //读取记录总数
        def count=0                 //实际存储成功的记录数
        def returnData=""           //返回的第一个存储成功的记录
        def failList=[]             //保存失败记录

        lst?.each{ p->
            //清空属性值的前后空格
            p.entrySet()?.each{
                it.value=it.value?.toString()?.trim()
            }
            try{
                def m=environmentService.save(p)


                if(m.result){
                    count+=1
                    //记录第一个保存成功的记录
                    if (count==1){
                        if (p.veh_Clxh){
                            returnData+= p.veh_Clxh
                        } else{
                            returnData+= "noVeh_Clxh"
                        }
                        returnData+="_"
                        if (p.veh_Clmc){
                            returnData+= p.veh_Clmc
                        } else{
                            returnData+= "noVeh_Clmc"
                        }
                    }
                }else{
                    def errorMessages=""
                    def num=1
                    //错误信息处理
                    m.instance.errors.allErrors?.each{
                        if(it in org.springframework.validation.FieldError){
                            //将对象和属性国际化
                            it.arguments[1]="${message(code: 'environment.label')}"
                            it.arguments[0]="${message(code:"environment.${ it.arguments[0]}.label")}"
                            errorMessages+="${num}、"+message(error: it)+";"
                        }

                    }
                    p.errorMessages=errorMessages
                    failList.add(p)
                }

            }catch(Exception e){
                p.errorMessages=e.message
                failList.add(p)
            }
        }
        StringBuilder errorMessages=new StringBuilder()
        failList?.each{
            errorMessages.append("【veh_Clxh=${it.veh_Clxh} veh_Clmc=${it.veh_Clmc}】 错误信息为："+it.errorMessages+"&*_*&")
        }


        def result=[realCount:count,count:totalCount,errorMessages:errorMessages,returnData:returnData]
        render result as JSON

    }
    /**
     *@Description 跳转到导入文件页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05
     */
    def importPage(){
        //传入参数来跳转不同的action
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'Environment',action: 'impEnvironment')}",tabId:params.tabId])
    }


    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }
    /**
     *@Description 导出功能
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05
     */
    def export(){
        def rows =[]
        def lst=[]
        def content=[]
        try{
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("环保信息"), "UTF-8")
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
            //List fields = ["num","veh_Clxh", "veh_Clmc","veh_Clfl","veh_Fdjxh","veh_Ggpx","veh_Bz","kind"]
            Map labels =  ["num":"序号","veh_Clxh":"车辆型号", "veh_Clmc":"车辆名称","veh_Clfl":"车辆类别","veh_Fdjxh":"发动机型号","veh_Ggpx":"公告批次","veh_Bz":"备注","kind":"类型"]
            def out=response.outputStream
            def cel={
                if(params.veh_Clxh){
                    like("veh_Clxh","%${params.veh_Clxh}%")
                }
                if (params.veh_Clmc) {
                    like("veh_Clmc","%${params.veh_Clmc}%")
                }
                kind{
                    if (params.kind){
                        like("code","%${params.kind}%")
                    }
                }
            }
            rows=Environment.createCriteria().list (params,cel)
            int i=1
            rows.each {u->
                def m=[:]
                m.put("num",i)
                m.put("veh_Clxh", u.veh_Clxh)
                m.put("veh_Clmc", u.veh_Clmc)
                m.put("veh_Clfl", u.veh_Clfl)
                m.put("veh_Fdjxh", u.veh_Fdjxh)
                m.put("veh_Ggpx", u.veh_Ggpx)
                m.put("veh_Bz", u.veh_Bz)
                m.put("kind", u.kind.dicValueNameC)
                lst.add(m)
                i++
            }
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["环保信息"],content)
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
}
