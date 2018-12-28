package cn.com.wz.vehcert.paper

import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.system.user.User
import cn.com.wz.parent.base.BaseController
import grails.converters.JSON
import cn.com.wz.parent.ConstantsUtil
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils;

/**
 *@Description 合格证纸张管理-发放
 *@Auther liuly
 *@createTime 2013-08-02
 */
class PaperProController extends BaseController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def exportService
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
        render (view: "/cn/com/wz/vehcert/paper/paperpro/index",model: params)
    }
    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def listDealer() {
        render (view: "/cn/com/wz/vehcert/paper/paperpro/dealer/indexDealer",model: params)
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
            if(params.dealerNum){
                like("dealerNum","%${params.dealerNum}%")
            }
            if (params.dealerName) {
                like("dealerName","%${params.dealerName}%")
            }
            if(params.startTime){
                ge('provideTime',params.startTime)
            }
            if(params.endTime){
                le('provideTime',params.endTime)
            }
            if(params.type&&params.type!='all'){
                eq('type',Integer.parseInt(params.type))
            }
            eq('status',0)
        }
        def rows=Paper.createCriteria().list(params,cel)

        def lst=[]
        int i=1

        rows?.each {u->
            def m=[:]
            m.num=i
            m.id=u.id
            m.dealerNum=u.dealerNum
            m.dealerName=u.dealerName
            m.paperNum =u.paperNum
            m.note =u.note
            m.status =u.status
            m. recoverTime=u.recoverTime
            m.provideTime=u.provideTime
            m.receiveName=u.receiveName
            m.confirmTime=u.confirmTime
            if(u.type==0){
                m.type='汽车整车'
            }else if(u.type==1){
                m.type='农用车整车'
            }else{
                m.type='二类底盘'
            }
            def userP=User.get(u.provideId)
            if (userP){
                m.provideId=userP.userDetail.realName
            }
            def userR=User.get(u.recoverId)
            if (userR){
                m.recoverId=userR.userDetail.realName
            }
            m.noteConfirm=u.noteConfirm
            m.nums=u.nums
            m.numRand=u.numRand
            i++;
            lst.add(m)
        }
        def result = [rows:lst,total:rows.totalCount]
        render result as JSON
    }
    /**
     *@Description 用于生成表格数据   获取满足条件的用户信息列表
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-11-04
     */
    def jsonListDealer(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def cel={
            if(params.type&&params.type!='all'){
               eq('type',Integer.parseInt(params.type))
            }
            if(params.startTime){
                ge('provideTime',params.startTime)
            }
            if(params.endTime){
                le('provideTime',params.endTime)
            }
            eq('dealerNum',user.userName)
            eq('status',0)
        }
        def rows=Paper.createCriteria().list(params,cel)

        def lst=[]

        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.paperNum =u.paperNum
            m.note =u.note
            m.status =u.status
            m. recoverTime=u.recoverTime
            m.provideTime=u.provideTime
            m.receiveName=u.receiveName
            m.confirmTime=u.confirmTime
            if(u.type==0){
                m.type='汽车整车'
            }else if(u.type==1){
                 m.type='农用车整车'
            }else{
                m.type='二类底盘'
            }
            def userP=User.get(u.provideId)
            if (userP){
                m.provideId=userP.userDetail.realName
            }
            def userR=User.get(u.recoverId)
            if (userR){
                m.recoverId=userR.userDetail.realName
            }
            m.noteConfirm=u.noteConfirm
            m.nums=u.nums
            m.numRand=u.numRand
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

        def paperInstance=new Paper(params)

        render (view:'/cn/com/wz/vehcert/paper/paperpro/create', model:[paperInstance: paperInstance])
    }
    /**
     *@Description 保存新建信息
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def save(){
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def provideTime=new Date().format("yyyy-MM-dd")
        params.type=Integer.parseInt(params.type)
        def paperInstance=new Paper(params)
        paperInstance.provideId=user.id
        paperInstance.provideTime=provideTime.toString()

        if (!paperInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/paper/paperpro/create", model: [paperInstance: paperInstance])
            return
        }else{
            flash.message = message(code: 'default.created.message', args: [message(code: 'paper.label', default: 'Paper'), paperInstance.dealerName])
            redirect(action: "show", id: paperInstance.id)
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
        def paperInstance = Paper.get(params.id)
        if (!paperInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paper.label', default: 'paper'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/paper/paperpro/show', model: [paperInstance: paperInstance])
    }

    /**
     *@Description 进入编辑页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def edit() {
        def paperInstance = Paper.get(params.id)
        if (!paperInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paper.label', default: 'Paper'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/paper/paperpro/edit',model:  [paperInstance: paperInstance])
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
        def paperInstance = Paper.get(params.id)
        if (!paperInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paper.label', default: 'Paper'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (paperInstance.version > version) {
                paperInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'paper.label', default: 'Paper')] as Object[],
                        "Another user has updated this Post while you were editing")
                render(view: "/cn/com/wz/vehcert/paper/paperpro/edit", model: [paperInstance: paperInstance])
                return
            }
        }
        params.type=Integer.parseInt(params.type)
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def provideTime=new Date().format("yyyy-MM-dd")
        paperInstance.properties = params
        paperInstance.provideId=user.id
        paperInstance.provideTime=provideTime.toString()


        if (!paperInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/paper/paperpro/edit", model: [paperInstance: paperInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'paper.label', default: 'Paper'), paperInstance.dealerName])
        render(view:'/cn/com/wz/vehcert/paper/paperpro/show',model:[paperInstance: paperInstance])
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
            def paperInstance = Paper.get(it)
            if (!paperInstance) {
                //msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
                return
            }
            try {
                paperInstance.delete(flush: true)
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
                        '0':'dealerNum',
                        '1':'dealerName',
                        '2':'nums',
                        '3':'numRand',
                        '4':'type',
                        '5':'receiveName',
                        '6':'provideId',
                        '7':'provideTime',
                        '8':'note'
                ]
        ]
        def lst=excelOp.readExcel(realPath, config_column_map)
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
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
                def m=paperService.save(p,user)


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
        print params.tabId
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'PaperPro',action: 'impPaper')}",tabId:params.tabId])
    }
    /*  *
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
            def encodedFilename = URLEncoder.encode(("纸张发放"), "UTF-8")
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
            //List fields = ["num","dealerNum", "dealerName","nums","numRand","receiveName","provideId","provideTime","note"]
            Map labels =  ["dealerNum":"经销商编号", "dealerName":"经销商姓名","nums":"纸张数量","numRand":"编号范围","type":"合格证类型：0.汽车整车，1.农用车整车,2.二类底盘","receiveName":"领取人","provideId":"发放人","provideTime":"发放时间","note":"备注"]
             def out=response.outputStream
            def cel={
                if(params.dealerNum){
                    like("dealerNum","%${params.dealerNum}%")
                }
                if (params.dealerName) {
                    like("dealerName","%${params.dealerName}%")
                }
                if(params.startTime){
                    ge('provideTime',params.startTime)
                }
                if(params.type&&params.type!='all'){
                    eq('type',Integer.parseInt(params.type))
                }
                if(params.endTime){
                    le('provideTime',params.endTime)
                }
                eq('status',0)
            }
            rows=Paper.createCriteria().list (cel)
            int i=1
            rows.each {u->
                def m=[:]
                m.put("dealerNum", u.dealerNum)
                m.put("dealerName", u.dealerName)
                m.put("nums",u.nums)
                m.put("numRand",u.numRand)
                m.put("receiveName",u.receiveName)
                def user=User.get(u.provideId)
                m.type=u.type
                if(user){
                    m.put("provideId",user.userDetail.realName)
                } else{
                    m.put("provideId","")
                }

                m.put("provideTime",u.provideTime)
                m.put("note", u.note)
                lst.add(m)
                i++
            }
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["纸张发放"],content)
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
    /*  *
   *@Description 确认操作
   *@param
   *@return
   *@Auther liuly
   *@createTime 2013-11-04
*/
    def confirm(){
        def flag=true
        def paper=Paper.get(params.id)
        def confirmTime=new Date().format("yyyy-MM-dd")
        paper.confirmTime=confirmTime
         if (paper.save()){
             flag = true
         }else{
             flag = false
         }
        render flag
    }
    /*  *
    *@Description 修改操作
    *@param
    *@return
    *@Auther liuly
    *@createTime 2013-11-04
    */
    def xiugai(){
        def paperInstance=Paper.get(params.id)
        render(view:'/cn/com/wz/vehcert/paper/paperpro/dealer/create',model:[paperInstance:paperInstance])
    }
    /*  *
    *@Description 修改操作
    *@param
    *@return
    *@Auther liuly
    *@createTime 2013-11-04
    */
    def saveDealer(){

        def paperInstance=Paper.get(params.pid)
        paperInstance.noteConfirm= params.noteConfirm
        if(paperInstance.save()){
            redirect(action: "listDealer", params: params)
        } else{
            render(view:'/cn/com/wz/vehcert/paper/paperpro/dealer/create',model:[paperInstance:paperInstance])
        }
    }
}
