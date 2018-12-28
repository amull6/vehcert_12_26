package cn.com.wz.vehcert.paper

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils;


class PaperErrorController extends BaseController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def exportService
    def paperErrorService
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
        render (view: "/cn/com/wz/vehcert/paper/papererror/index",model: params)
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
        print params
        def cel={
            if(params.dealerNum){
                like("dealerNum","%${params.dealerNum}%")
            }
            if (params.dealerName) {
                like("dealerName","%${params.dealerName}%")
            }
            if(params.startTime){
                ge('createTime',params.startTime)
            }
            if(params.type&&params.type!='all'){
                eq('type',Integer.parseInt(params.type))
            }
            if(params.endTime){
                le('createTime',params.endTime)
            }
        }
        def rows=PaperError.createCriteria().list(params,cel)

        def lst=[]

        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.dealerNum=u.dealerNum
            m.dealerName=u.dealerName
            m.paperNum =u.paperNum
            m.veh_Hgzbh=u.veh_Hgzbh
            m.num=u.num
            if(u.type==0){
                m.type='汽车整车'
            }else if(u.type==1){
                m.type='农用车整车'
            }else{
                m.type='二类底盘'
            }
            m.note =u.note
            m. createTime=u.createTime
            def userP=User.get(u.createrId)
            if (userP){
                m.createrId=userP.userDetail.realName
            }
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

        def paperErrorInstance=new PaperError(params)

        render (view:'/cn/com/wz/vehcert/paper/papererror/create', model:[paperErrorInstance: paperErrorInstance])
    }
    /**
     *@Description 保存新建信息
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def save() {
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def createTime=new Date().format("yyyy-MM-dd")
        def paperErrorInstance=new PaperError(params)
        paperErrorInstance.createrId=user.id
        paperErrorInstance.createTime=createTime.toString()

        if (!paperErrorInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/paper/papererror/create", model: [paperErrorInstance: paperErrorInstance])
            return
        }else{
            flash.message = message(code: 'default.created.message', args: [message(code: 'paper.label', default: 'Paper'), paperErrorInstance.dealerName])
            redirect(action: "show", id: paperErrorInstance.id)
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
        def paperErrorInstance = PaperError.get(params.id)
        if (!paperErrorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paper.label', default: 'paper'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/paper/papererror/show', model: [paperErrorInstance: paperErrorInstance])
    }

    /**
     *@Description 进入编辑页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def edit() {
        def paperErrorInstance = PaperError.get(params.id)
        if (!paperErrorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paper.label', default: 'Paper'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/paper/papererror/edit',model:  [paperErrorInstance: paperErrorInstance])
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
        def paperErrorInstance = PaperError.get(params.id)
        if (!paperErrorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paper.label', default: 'Paper'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (paperErrorInstance.version > version) {
                paperErrorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'paper.label', default: 'Paper')] as Object[],
                        "Another user has updated this Post while you were editing")
                render(view: "/cn/com/wz/vehcert/paper/papererror/edit", model: [paperErrorInstance: paperErrorInstance])
                return
            }
        }

        paperErrorInstance.properties = params



        if (!paperErrorInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/paper/papererror/edit", model: [paperErrorInstance: paperErrorInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'paperError.label', default: 'Paper'), paperErrorInstance.dealerName])
        render(view:'/cn/com/wz/vehcert/paper/papererror/show',model:[paperErrorInstance: paperErrorInstance])
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
            def paperErrorInstance = PaperError.get(it)
            if (!paperErrorInstance) {
                //msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
                return
            }
            try {
                paperErrorInstance.delete(flush: true)
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
    /* *
      *@Description 从excel中导入用户信息
      *@param  filePath:用户信息文件路径；
      *config_column_map 存储了参数信息。sheet从第几个sheet中取，从0开始；startRow从第几行开始读取，从0开始；
      *columnMap保存了列与实体属性的对应关系，第一列的列号为0.
      *@return
      *@Auther lly
      *@createTime 2013-4-02*/

    def impPaper(String filePath){
        def realPath=""
        def filePaths=filePath.split(':_:')
        if(filePaths?.length>=2){
            //获取图片存储的相对路径(不包含文件名)
            def picRelativePath=UploadUtil.getRelativePathByUploadType(filePaths[0])
            def fileNames=filePaths[1].split(":;:")
            if(fileNames.length==3){
                realPath=UploadUtil.getRelativeFile(servletContext, picRelativePath, fileNames[0])
                realPath= UploadUtil.getRootDir(servletContext)+fileNames[2]
            }
        }
        ExcelUtils excelOp=new ExcelUtils();
        Map config_column_map=[sheet:0,
                startRow:2,
                columnMap:[
                        '0':'dealerNum',
                        '1':'dealerName',
                        '2':'paperNum',
                        '3':'veh_Hgzbh',
                        '4':'type',
                        '5':'num',
                        '6':'note'
                ]
        ]
        def lst=excelOp.readExcel(realPath, config_column_map)
        print lst
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
                def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
                def m=paperErrorService.save(p,user)


                if(m.result){
                    count+=1
                    //记录第一个保存成功的记录
                    if (count==1){
                        if (p.dealerNum){
                            returnData+= p.dealerNum
                        } else{
                            returnData+= "noDealerNum"
                        }
                        returnData+="_"
                        if (p.dealerName){
                            returnData+= p.dealerName
                        } else{
                            returnData+= "noDealerName"
                        }
                    }
                }else{
                    def errorMessages=""
                    def num=1
                    //错误信息处理
                    m.instance.errors.allErrors?.each{
                        if(it in org.springframework.validation.FieldError){
                            //将对象和属性国际化
                            it.arguments[1]="${message(code: 'paper.label')}"
                            it.arguments[0]="${message(code:"paper.${ it.arguments[0]}.label")}"
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
            errorMessages.append("【dealerNum=${it.dealerNum} dealerName=${it.dealerName}】 错误信息为："+it.errorMessages+"&*_*&")
        }


        def result=[realCount:count,count:totalCount,errorMessages:errorMessages,returnData:returnData]
        render result as JSON

    }
    /* *
      *@Description 跳转到导入文件页面
      *@param
      *@return
      *@Auther liuly
      *@createTime 2013-04-02
      */
    def importPage(){
        //传入参数来跳转不同的action
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'PaperError',action: 'impPaper')}",tabId:params.tabId])
    }
    /*  *
       *@Description 导出功能
       *@param
       *@return
       *@Auther liuly
       *@createTime 2013-03-19
      * @Update 修改获取realPath错误
      * @UpdateBy zhuwei
      * UpdateTime2014_05_14
    */

    def export(){
        def rows =[]
        def lst=[]
        def content=[]
        try{
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("纸张打印错误记录"), "UTF-8")
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
            //List fields = ["dealerNum", "dealerName","paperNum","veh_Hgzbh","num","createrId","createTime","note"]
            Map labels =  ["dealerNum":"经销商编号", "dealerName":"经销商姓名","paperNum":"纸张编号","veh_Hgzbh":"合格证编号","type":"合格证类型：0.汽车整车，1.农用车整车,2.二类底盘","num":"纸张数量","createrId":"记录人","createTime":"记录时间","note":"备注"]
            def out=response.outputStream
            def cel={
                if(params.dealerNum){
                    like("dealerNum","%${params.dealerNum}%")
                }
                if (params.dealerName) {
                    like("dealerName","%${params.dealerName}%")
                }
                if(params.startTime){
                    ge('createTime',params.startTime)
                }
                if(params.endTime){
                    le('createTime',params.endTime)
                }
                if(params.type&&params.type!='all'){
                    eq('type',Integer.parseInt(params.type))
                }
            }
            rows=PaperError.createCriteria().list(params,cel)

            rows?.each {u->
                def m=[:]
                m.id=u.id
                m.dealerNum=u.dealerNum
                m.dealerName=u.dealerName
                m.paperNum =u.paperNum
                m.veh_Hgzbh=u.veh_Hgzbh
                m.num=u.num
                m.note =u.note
                m.type=u.type
                m. createTime=u.createTime
                def userP=User.get(u.createrId)
                if (userP){
                    m.createrId=userP.userDetail.realName
                }
                lst.add(m)
            }
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["纸张打印错误记录"],content)
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

