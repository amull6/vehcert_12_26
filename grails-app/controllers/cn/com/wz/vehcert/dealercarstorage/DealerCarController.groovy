package cn.com.wz.vehcert.dealercarstorage

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils;

class DealerCarController extends BaseController {
//定义一些方法的提交方式
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def exportService
    def dealerCarService
    def index() {
        redirect(action: "list", params: params)
    }

    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-17
     */
    def list() {
        render (view: "/cn/com/wz/vehcert/dealerCar/index",model: params)
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
            if(params.dc_Ggxh){
                like("dc_Ggxh","%${params.dc_Ggxh}%")
            }
            if (params.dc_Jss) {
                like("dc_Jss","%${params.dc_Jss}%")
            }
            kind{
                if (params.kind){
                    like("code","%${params.kind}%")
                }
            }
        }
        def rows=DealerCar.createCriteria().list(params,cel)
        def lst=[]
        rows.each {r->
              def m=[:]
            m.id=r.id
            m.dc_Ggxh=r.dc_Ggxh
            m.dc_Jss=r.dc_Jss
            m.dc_Ggpc=r.dc_Ggpc
            m.dc_Pic=r.dc_Pic
            m.dc_Jsszcrs=r.dc_Jsszcrs
            m.dc_Fdj=r.dc_Fdj
            m.dc_Gglt=r.dc_Gglt
            m.dc_Bhps=r.dc_Bhps
            m.dc_Qhlj=r.dc_Qhlj
            m.dc_Zj=r.dc_Zj
            m.dc_Zccc=r.dc_Zccc
            m.dc_Hxcc=r.dc_Hxcc
            m.dc_Zzl=r.dc_Zzl
            m.dc_Zbzl=r.dc_Zbzl
            m.dc_ZAzl=r.dc_ZAzl
            m.dc_Kzrq=r.dc_Kzrq
            m.dc_Qt=r.dc_Qt
            m.updaterName=r.updaterName
            m.updateTime=r.updateTime
            m.kind=r.kind.dicValueNameC
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
     *@createTime 2013-08-17
     */
    def create() {
        def dealerCarInstance=new DealerCar(params)
        render (view:'/cn/com/wz/vehcert/dealerCar/create', model:[dealerCarInstance: dealerCarInstance])
    }
    /**
     *@Description 保存新建信息
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-17
     */
    def save() {
        def dealerCarInstance=new DealerCar(params)
         def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        dealerCarInstance.updaterName=user.userDetail.realName
        dealerCarInstance.updateTime=DateUtil.getCurrentTime()
        if (!dealerCarInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/dealerCar/create", model: [dealerCarInstance: dealerCarInstance])
            return
        }else{
            flash.message = message(code: 'default.created.message', args: [message(code: 'dealerCar.label', default: 'dealerCar'), dealerCarInstance.dc_Ggxh])
            redirect(action: "show", id: dealerCarInstance.id)
        }

    }

    /**
     *@Description 跳转到详情页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-17
     */
    def show() {
        def dealerCarInstance = DealerCar.get(params.id)
        if (!dealerCarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dealerCar.label', default: 'dealerCar'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/dealerCar/show', model: [dealerCarInstance: dealerCarInstance])
    }

    /**
     *@Description 进入编辑页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-17
     */
    def edit() {
        def dealerCarInstance = DealerCar.get(params.id)
        if (!dealerCarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dealerCar.label', default: 'dealerCar'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/dealerCar/edit',model:  [dealerCarInstance: dealerCarInstance])
    }

    /**
     *@Description 保存更新信息
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-17
     */
    def update() {
        //判定要修改的记录是否存在
        def dealerCarInstance = DealerCar.get(params.id)
        if (!dealerCarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dealerCar.label', default: 'dealerCar'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (dealerCarInstance.version > version) {
                dealerCarInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'dealerCar.label', default: 'dealerCar')] as Object[],
                        "Another user has updated this Post while you were editing")
                render(view: "/cn/com/wz/vehcert/dealerCar/edit", model: [dealerCarInstance: dealerCarInstance])
                return
            }
        }

        dealerCarInstance.properties = params



        if (!dealerCarInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/dealerCar/edit", model: [dealerCarInstance: dealerCarInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'dealerCar.label', default: 'dealerCar'), dealerCarInstance.dc_Ggxh])
        render(view:'/cn/com/wz/vehcert/dealerCar/show',model:[dealerCarInstance: dealerCarInstance])
    }
    /**
     *@Description 删除
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-17
     *
     */
    def jsonDelete(){
        def ids = params.deleteIds
        def idsArray = ids.split('_')
        def flag = true
        def msg = message(code: 'default.deleted.simple.message')
        idsArray.each {
            print it
            def dealerCarInstance = DealerCar.get(it)
            if (!dealerCarInstance) {
                //msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
                return
            }
            try {
                dealerCarInstance.delete(flush: true)
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
     *@createTime 2013-08-17
     */
    def impdealerCar(String filePath){
        def realPath=""
        def filePaths=filePath.split(':_:')
        if(filePaths?.length>=2){
            //获取图片存储的相对路径(不包含文件名)
            def picRelativePath=UploadUtil.getRelativePathByUploadType(filePaths[0])
            def fileNames=filePaths[1].split(":;:")
            if(fileNames.length==2){
                realPath=UploadUtil.getRelativeFile(servletContext, picRelativePath, fileNames[0])
                realPath= UploadUtil.getRootDir(servletContext)+realPath
            }
        }
        ExcelUtils excelOp=new ExcelUtils();
        Map config_column_map=[sheet:0,
                startRow:2,
                columnMap:[
                        '0':'dc_Ggxh',
                        '1':'dc_Jss',
                        '2':'dc_Ggpc',
                        '3':'dc_Pic',
                        '4':'dc_Jsszcrs',
                        '5':'dc_Fdj',
                        '6':'dc_Gglt',
                        '7':'dc_Bhps',
                        '8':'dc_Qhlj',
                        '9':'dc_Zj',
                        '10':'dc_Zccc',
                        '11':'dc_Hxcc',
                        '12':'dc_Zzl',
                        '13':'dc_Zbzl',
                        '14':'dc_ZAzl',
                        '15':'dc_Kzrq',
                        '16':'kind',
                        '17':'dc_Qt'
                ]
        ]
        def lst=excelOp.readExcel(realPath, config_column_map)
        def totalCount=lst?.size()  //读取记录总数
        def count=0                 //实际存储成功的记录数
        def returnData=""           //返回的第一个存储成功的记录
        def failList=[]             //保存失败记录
       print lst
        lst?.each{ p->
            //清空属性值的前后空格
            p.entrySet()?.each{
                it.value=it.value?.toString()?.trim()
            }
            try{
                def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
                def updaterName=user.userDetail.realName
                def m=dealerCarService.save(p,updaterName)


                if(m.result){
                    count+=1
                    //记录第一个保存成功的记录
                    if (count==1){
                        if (p.dc_Ggxh){
                            returnData+= p.dc_Ggxh
                        } else{
                            returnData+= "nodc_Ggxh"
                        }
                        returnData+="_"
                        if (p.dc_Jss){
                            returnData+= p.dc_Jss
                        } else{
                            returnData+= "nodc_Jss"
                        }
                    }
                }else{
                    def errorMessages=""
                    def num=1
                    //错误信息处理
                    m.instance.errors.allErrors?.each{
                        if(it in org.springframework.validation.FieldError){
                            //将对象和属性国际化
                            it.arguments[1]="${message(code: 'dealerCar.label')}"
                            it.arguments[0]="${message(code:"dealerCar.${ it.arguments[0]}.label")}"
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
            errorMessages.append("【dc_Ggxh=${it.dc_Ggxh} dc_Jss=${it.dc_Jss}】 错误信息为："+it.errorMessages+"&*_*&")
        }


        def result=[realCount:count,count:totalCount,errorMessages:errorMessages,returnData:returnData]
        render result as JSON

    }
    /**
     *@Description 跳转到导入文件页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-17
     */
    def importPage(){
        //传入参数来跳转不同的action
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'dealerCar',action: 'impdealerCar')}",tabId:params.tabId])
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
     *@createTime 2013-08-17
     */
    def export(){
        def datas =[]
        def lst=[]
        def content=[]
        try{
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("公告信息（经销商）"), "UTF-8")
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
            //List fields = ['dc_Ggxh','dc_Jss','dc_Ggpc','dc_Pic','dc_Jsszcrs','dc_Fdj','dc_Gglt','dc_Bhps','dc_Qhlj','dc_Zj','dc_Zccc','dc_Hxcc','dc_Zzl','dc_Zbzl','dc_ZAzl','dc_Kzrq','kind','dc_Qt']
            Map labels =  ["dc_Ggxh":"公告型号", "dc_Jss":"驾驶室",'dc_Ggpc':'公告批次','dc_Pic':'车辆图片','dc_Jsszcrs':'准乘人数','dc_Fdj':'发动机','dc_Gglt':'公告轮胎','dc_Bhps':'板簧片数','dc_Qhlj':'前/后轮距','dc_Zj':'轴距','dc_Zccc':'整车尺寸','dc_Hxcc':'货箱尺寸','dc_Zzl':'总质量','dc_Zbzl':'整备质量','dc_ZAzl':'载质量','dc_Kzrq':'扩展日期','kind':'类型','dc_Qt':'其它']
            def out=response.outputStream
            def cel={
                if(params.dc_Ggxh){
                    like("dc_Ggxh","%${params.dc_Ggxh}%")
                }
                if (params.dc_Jss) {
                    like("dc_Jss","%${params.dc_Jss}%")
                }
                kind{
                    if (params.kind){
                        if(params.kind=='all'){
                            like("code","%%")
                        }else{
                            like("code","%${params.kind}%")
                        }

                    }
                }
            }
            datas=DealerCar.createCriteria().list (cel)
            datas.each {r->
                def m=[:]
                m.dc_Ggxh=r.dc_Ggxh
                m.dc_Jss=r.dc_Jss
                m.dc_Ggpc=r.dc_Ggpc
                m.dc_Pic=r.dc_Pic
                m.dc_Jsszcrs=r.dc_Jsszcrs
                m.dc_Fdj=r.dc_Fdj
                m.dc_Gglt=r.dc_Gglt
                m.dc_Bhps=r.dc_Bhps
                m.dc_Qhlj=r.dc_Qhlj
                m.dc_Zj=r.dc_Zj
                m.dc_Zccc=r.dc_Zccc
                m.dc_Hxcc=r.dc_Hxcc
                m.dc_Zzl=r.dc_Zzl
                m.dc_Zbzl=r.dc_Zbzl
                m.dc_ZAzl=r.dc_ZAzl
                m.dc_Kzrq=r.dc_Kzrq
                m.dc_Qt=r.dc_Qt
                m.kind=r.kind.dicValueNameC
                lst.add(m)
            }
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["公告信息（经销商）"],content)
            session.setAttribute('compFlag',"success")
            out.flush()
            out.close()
        }
    }catch(Exception e){
        e.printStackTrace()
        session.setAttribute('compFlag',"error")
    }finally{
        datas.clear()
        lst.clear()
        content.clear()
    }
   }
}
