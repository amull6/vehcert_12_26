package cn.com.wz.vehcert.oil

import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.itextpdf.text.pdf.qrcode.BitMatrix
import com.itextpdf.text.pdf.qrcode.EncodeHintType
import grails.converters.JSON
import org.apache.poi.hslf.blip.Bitmap
import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils

import java.nio.file.FileSystems;


class OilController extends BaseController {
//定义一些方法的提交方式
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def exportService
    def oilService
    def index() {
        redirect(action: "list", params: params)
    }

    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-04
     */
    def list() {
        render (view: "/cn/com/wz/vehcert/oil/index",model: params)
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
        def rows=Oil.createCriteria().list(params,cel)
        def lst=[]
        int i=1
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.num=i
            m.veh_Clxh=u.veh_Clxh
            m.veh_Clmc=u.veh_Clmc
            m.veh_Yh=u.veh_Yh
            m.veh_Fdjxh=u.veh_Fdjxh
            m.veh_CarH=u.veh_CarH
            m.veh_GoodH=u.veh_GoodH
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
        def oilInstance=new Oil(params)
        render (view:'/cn/com/wz/vehcert/oil/create', model:[oilInstance: oilInstance])
    }
    /**
     *@Description 保存新建信息
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-04
     */
    def save() {
        def oilInstance=new Oil(params)

        if (!oilInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/oil/create", model: [oilInstance: oilInstance])
            return
        }else{
            flash.message = message(code: 'default.created.message', args: [message(code: 'oil.label', default: 'oil'), oilInstance.veh_Clxh])
            redirect(action: "show", id: oilInstance.id)
        }

    }

    /**
     *@Description 跳转到详情页面
     *@param
     *@return
     *@creatTime 2016-12-21 by zhuwei
     */
    def show() {
        def oilInstance = Oil.get(params.id)
        if (!oilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'oil.label', default: 'oil'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/oil/show', model: [oilInstance: oilInstance])
    }

    /**
     *@Description 进入编辑页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05
     */
    def edit() {
        def oilInstance = Oil.get(params.id)
        if (!oilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'oil.label', default: 'oil'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/vehcert/oil/edit',model:  [oilInstance: oilInstance])
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
        def oilInstance = Oil.get(params.id)
        if (!oilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'oil.label', default: 'oil'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (oilInstance.version > version) {
                oilInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'oil.label', default: 'oil')] as Object[],
                        "Another user has updated this Post while you were editing")
                render(view: "/cn/com/wz/vehcert/oil/edit", model: [oilInstance: oilInstance])
                return
            }
        }

        oilInstance.properties = params



        if (!oilInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/oil/edit", model: [oilInstance: oilInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'oil.label', default: 'oil'), oilInstance.veh_Clxh])
        render(view:'/cn/com/wz/vehcert/oil/show',model:[oilInstance: oilInstance])
    }
    /**
     *@Description 删除
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05
     *
     */
    def jsonDelete(){
        def ids = params.deleteIds
        def idsArray = ids.split('_')
        def flag = true
        def msg = message(code: 'default.deleted.simple.message')
        idsArray.each {
            def oilInstance = Oil.get(it)
            if (!oilInstance) {
                //msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
                return
            }
            try {
                oilInstance.delete(flush: true)
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
     *@Description 从excel中导入油耗信息
     *@param  filePath:用户信息文件路径；
     *config_column_map 存储了参数信息。sheet从第几个sheet中取，从0开始；startRow从第几行开始读取，从0开始；
     *columnMap保存了列与实体属性的对应关系，第一列的列号为0.
     *@return
     *@Auther lly
     *@createTime 2013-08-05
     * @update 修改获得realPath路径出错
     * @update 2013-03-24 by zhuwei
     */
    def impoil(String filePath){
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
                        '3':'veh_Yh',
                        '4':'veh_Fdjxh',
                        '5':'veh_CarH',
                        '6':'veh_GoodH',
                        '7':'veh_Bz',
                        '8':'kind'

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
                def m=oilService.save(p)


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
                            it.arguments[1]="${message(code: 'oil.label')}"
                            it.arguments[0]="${message(code:"oil.${ it.arguments[0]}.label")}"
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
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'oil',action: 'impoil')}",tabId:params.tabId])
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
            def encodedFilename = URLEncoder.encode(("油耗信息"), "UTF-8")
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
           // List fields = ["num","veh_Clxh", "veh_Clmc","veh_Yh","veh_Fdjxh","veh_CarH","veh_GoodH","veh_Bz","kind"]
            Map labels =  ["num":"序号","veh_Clxh":"车辆型号", "veh_Clmc":"车辆名称","veh_Yh":"油耗批次","veh_Fdjxh":"发动机型号","veh_CarH":"整车长*宽*高","veh_GoodH":"货箱长*宽*高","veh_Bz":"备注","kind":"类型"]
             def out=response.outputStream
            def cel={
                if(params.veh_Clxh){
                    like("veh_Clxh","%${params.veh_Clxh}%")
                }
                if (params.veh_Clmc) {
                    like("veh_Clmc","%${params.veh_Clmc}%")
                }
                kind{
                    //根据页面的油耗类型先查询出来油耗信息，在导出到EXCLE
                    if (params.kind){
                        like("code","%${params.kind}%")
                    }
                }
            }
            rows=Oil.createCriteria().list (params,cel)
            int i=1
            rows.each {u->
                def m=[:]
                m.put("num",i)
                m.put("veh_Clxh", u.veh_Clxh)
                m.put("veh_Clmc", u.veh_Clmc)
                m.put("veh_Yh", u.veh_Yh)
                m.put("veh_Fdjxh", u.veh_Fdjxh)
                m.put("veh_CarH", u.veh_CarH)
                m.put("veh_GoodH", u.veh_GoodH)
                m.put("veh_Bz", u.veh_Bz)
                m.put("kind", u.kind.dicValueNameC)
                lst.add(m)
                i++
            }
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["油耗信息"],content)
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



