package cn.com.wz.vehcert.paper

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils;

class PaperDealerController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def exportService
    def paperDealerService
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
        render (view: "/cn/com/wz/vehcert/paper/paperdealer/index",model: params)
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
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def result= paperDealerService.list(params,user)
        render result as JSON
    }
    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def listAll() {
        render (view: "/cn/com/wz/vehcert/paper/paperdealer/indexAll",model: params)
    }
    /**
     *@Description 用于生成表格数据   获取满足条件的用户信息列表
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def jsonListAll(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def result= paperDealerService.list(params,user)
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

        def paperdealerInstance=new PaperDealer(params)

        render (view:'/cn/com/wz/vehcert/paper/paperdealer/create', model:[paperdealerInstance: paperdealerInstance])
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
        def paperdealerInstance=new PaperDealer(params)
        paperdealerInstance.createrId=user.id
        paperdealerInstance.createTime=createTime.toString()

        if (!paperdealerInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/paper/paperdealer/create", model: [paperdealerInstance: paperdealerInstance])
            return
        }else{
            flash.message = message(code: 'default.created.message', args: [message(code: 'paperDealer.label', default: 'Paper Dealer'), paperdealerInstance.paperNum])
            redirect(action: "show", id: paperdealerInstance.id)
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
        def paperdealerInstance = PaperDealer.get(params.id)
        if (!paperdealerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paperDealer.label', default: 'Paper Dealer'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/paper/paperdealer/show', model: [paperdealerInstance: paperdealerInstance])
    }

    /**
     *@Description 进入编辑页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def edit() {
        def paperdealerInstance = PaperDealer.get(params.id)
        if (!paperdealerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paperDealer.label', default: 'paper Dealer'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/paper/paperdealer/edit',model:  [paperdealerInstance: paperdealerInstance])
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
        def paperdealerInstance = PaperDealer.get(params.id)
        if (!paperdealerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paperDealer.label', default: 'paperDealer'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (paperdealerInstance.version > version) {
                paperdealerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'paperDealer.label', default: 'paperDealer')] as Object[],
                        "Another user has updated this Post while you were editing")
                render(view: "/cn/com/wz/vehcert/paper/paperdealer/edit", model: [paperdealerInstance: paperdealerInstance])
                return
            }
        }
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def updateTime=new Date().format("yyyy-MM-dd")
        paperdealerInstance.properties = params
        paperdealerInstance.updaterId=user.id
        paperdealerInstance.updateTime=updateTime.toString()


        if (!paperdealerInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/paper/paperdealer/edit", model: [paperdealerInstance: paperdealerInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'paperDealer.label', default: 'paperDealer'), paperdealerInstance.paperNum])
        render(view:'/cn/com/wz/vehcert/paper/paperdealer/show',model:[paperdealerInstance: paperdealerInstance])
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
            def paperdealerInstance = PaperDealer.get(it)
            if (!paperdealerInstance) {
                //msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
                return
            }
            try {
                paperdealerInstance.delete(flush: true)
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
      *@createTime 2013-4-02
      * @Update 修改获取realPath错误
      * @UpdateBy zhuwei
      * UpdateTime2014_05_14
    */

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
                        '0':'paperNum',
                        '1':'zcinfoNum',
                        '2':'vin',
                        '3':'type'
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
                def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
                def m=paperDealerService.save(p,user)


                if(m.result){
                    count+=1
                    //记录第一个保存成功的记录
                    if (count==1){
                        if (p.paperNum){
                            returnData+= p.paperNum
                        } else{
                            returnData+= "noPaperNum"
                        }
                        returnData+="_"
                        if (p.zcinfoNum){
                            returnData+= p.zcinfoNum
                        } else{
                            returnData+= "noZcinfoNum"
                        }
                    }
                }else{
                    def errorMessages=""
                    def num=1
                    //错误信息处理
                    m.instance.errors.allErrors?.each{
                        if(it in org.springframework.validation.FieldError){
                            //将对象和属性国际化
                            it.arguments[1]="${message(code: 'paperDealer.label')}"
                            it.arguments[0]="${message(code:"paperDealer.${ it.arguments[0]}.label")}"
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
            errorMessages.append("【paperNum=${it.paperNum} zcinfoNum=${it.zcinfoNum}】 错误信息为："+it.errorMessages+"&*_*&")
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
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'paperDealer',action: 'impPaper')}",tabId:params.tabId])
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
                def encodedFilename = URLEncoder.encode(("经销商合格证记录"), "UTF-8")
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
                Map labels =  ["paperNum":"纸张编号", "zcinfoNum":"合格证编号","vin":"VIN","type":"合格证类型：0.汽车整车，1.农用车整车,2.二类底盘","createrId":"经销商名称","dealerNum":"经销商编号","createTime":"创建时间"]
                def out=response.outputStream
                def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
                def result= paperDealerService.list(params,user).rows
                ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
                def m=[]
                m.add(labels)
                content.add(result)
                excelOp.preWriteExcel(out,null,m,["经销商合格证纸张"],content)
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


