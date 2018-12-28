package cn.com.wz.vehcert.workshop
import cn.com.wz.parent.base.BaseController;
import org.springframework.dao.DataIntegrityViolationException;
import grails.converters.JSON
import parent.poi.ExcelUtils
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.system.dictionary.DictionaryValue;

class WorkshopToSignController extends BaseController {

    //定义一些方法的提交方式
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def exportService
      def workshopToSignService
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
        render (view: "/cn/com/wz/vehcert/workshoptosign/index",model: params)
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
        def cel={
            if(params.serialNum){
                workshop{like("serialNum","%${params.serialNum}%") }
            }
            if (params.name) {
                workshop{ like("name","%${params.name}%") }
            }
        }
        def rows=Symbol.createCriteria().list(params,cel)
        def lst=[]
        int i=1
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.num=i
            m.serialNum=u.workshop.serialNum
            m.name=u.workshop.name
            m.symbol=u.symbol
            m.kind=u.kind.dicValueNameC
            m.symbolNote=u.symbolNote
            m.maxNum=u.maxNum
            m.startNum=u.startNum
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
     *@createTime 2013-07-23
     */
    def create() {
        def symbolInstance=new Symbol(params)
        render (view:'/cn/com/wz/vehcert/workshoptosign/create', model:[symbolInstance: symbolInstance,serialNum:symbolInstance.workshop ])
    }
    /**
     *@Description 保存新建信息
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def save() {
        def symbolInstance=new Symbol(params)
         def workshop=Workshop.findBySerialNum(params.serialNum)
        symbolInstance.workshop=workshop
        if (!symbolInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/workshoptosign/create", model: [symbolInstance: symbolInstance])
            return
        }else{
            flash.message = message(code: 'default.created.message', args: [message(code: 'symbol.label', default: 'Workshop'), symbolInstance.symbol])
            redirect(action: "show", id: symbolInstance.id)
        }

    }

    /**
     *@Description 跳转到详情页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def show() {
        def symbolInstance = Symbol.get(params.id)
        if (!symbolInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'symbol.label', default: 'Workshop'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/workshoptosign/show', model: [symbolInstance: symbolInstance])
    }

    /**
     *@Description 进入编辑页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def edit() {
        def symbolInstance = Symbol.get(params.id)
        if (!symbolInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'symbol.label', default: 'Workshop'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/workshoptosign/edit',model:  [symbolInstance: symbolInstance,name: symbolInstance.workshop.name,serialNum:symbolInstance.workshop.serialNum ])
    }

    /**
     *@Description 保存更新信息
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def update() {
        //判定要修改的记录是否存在
        def symbolInstance = Symbol.get(params.id)

        if (!symbolInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'symbol.label', default: 'Workshop'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (symbolInstance.version > version) {
                symbolInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'symbol.label', default: 'Workshop')] as Object[],
                        "Another user has updated this Post while you were editing")
                render(view: "/cn/com/wz/vehcert/workshoptosign/edit", model: [symbolInstance: symbolInstance])
                return
            }
        }

        symbolInstance.properties=params

        if (!symbolInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/workshoptosign/edit", model: [symbolInstance: symbolInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'symbol.label', default: 'Workshop'), symbolInstance.workshop.name])
        render(view:'/cn/com/wz/vehcert/workshoptosign/show',model:[symbolInstance: symbolInstance])
    }
    /**
     *@Description 删除
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def jsonDelete(){
        def ids = params.deleteIds
        def idsArray = ids.split('_')
        def flag = true
        def msg = message(code: 'default.deleted.simple.message')
        idsArray.each {
            def symbolInstance = Symbol.get(it)
            if (!symbolInstance) {
                //msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
                return
            }
            try {
                symbolInstance.delete(flush: true)
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
     *@Description 从excel中导入车间符号信息
     *@param  filePath:用户信息文件路径；
     *config_column_map 存储了参数信息。sheet从第几个sheet中取，从0开始；startRow从第几行开始读取，从0开始；
     *columnMap保存了列与实体属性的对应关系，第一列的列号为0.
     *@return
     *@Auther lly
     *@createTime 2013-4-02
     * @update 修改导入 realPath错误
     * @updateTime 2013-03-24 by zhuwei
     */
    def impWorkshop(String filePath){
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
                        '1':'serialNum',
                        '2':'name',
                        '3':'kind',
                        '4':'symbol',
                        '5':'startNum',
                        '6':'maxNum',
                        '7':'symbolNote'

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
                def m=workshopToSignService.save(p)
                if(m.result){
                    count+=1
                    //记录第一个保存成功的记录
                    if (count==1){
                        if (p.serialNum){
                            returnData+= p.serialNum
                        } else{
                            returnData+= "noSerialNum"
                        }
                        returnData+="_"
                        if (p.name){
                            returnData+= p.name
                        } else{
                            returnData+= "noName"
                        }
                    }
                }else{
                    def errorMessages=""
                    def num=1
                    //错误信息处理
                    m.instance.errors.allErrors?.each{
                        if(it in org.springframework.validation.FieldError){
                            //将对象和属性国际化
                            it.arguments[1]="${message(code: 'symbol.label')}"
                            it.arguments[0]="${message(code:"workshop.${ it.arguments[0]}.label")}"
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
            errorMessages.append("【serialNum=${it.serialNum} name=${it.name}】 错误信息为："+it.errorMessages+"&*_*&")
        }


        def result=[realCount:count,count:totalCount,errorMessages:errorMessages,returnData:returnData]
        render result as JSON
    }
    /**
     *@Description 跳转到导入文件页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-04-02
     */
    def importPage(){
        //传入参数来跳转不同的action
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'WorkshopToSign',action: 'impWorkshop')}",tabId:params.tabId])
    }
    /**
     *@Description 导出功能
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-03-19
     */
    def export(){
        def rows =[]
        def lst=[]
        def content=[]
        try{
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("车间符号维护"), "UTF-8")
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
            List fields = ["num","serialNum", "name","kind","symbol","startNum","maxNum","symbolNote"]
            Map labels =  ["num":"序号","serialNum":"车间编号", "name":"车间名称","kind":"分类","symbol":"对应符号",'startNum':'符号起始值','maxNum':'符号最大值',"symbolNote":"说明"]
            def out=response.outputStream
            def cel={
                if(params.serialNum){
                    workshop{like("serialNum","%${params.serialNum}%") }
                }
                if (params.name) {
                    workshop{ like("name","%${params.name}%") }
                }
            }
            rows=Symbol.createCriteria().list (cel)
            int i=1
            rows.each {u->
                def m=[:]
                m.put("num",i)
                m.put("serialNum", u.workshop.serialNum)
                m.put("name", u.workshop.name)
                m.put('kind',u.kind.dicValueNameC)
                m.put("symbol", u.symbol)
                m.put("symbolNote", u.symbolNote)
                m.put('startNum',u.startNum)
                m.put('maxNum',u.maxNum)
                lst.add(m)
                i++
            }
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["车间符号维护"],content)
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
