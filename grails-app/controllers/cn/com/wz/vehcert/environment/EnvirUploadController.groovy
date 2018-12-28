package cn.com.wz.vehcert.environment

import cn.com.wz.parent.StringUtil
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import cn.com.wz.parent.base.BaseController
import parent.poi.ExcelUtils;
/**
 * @Description环保清单上传控制器
 * @Createime 2017-01-01 by zhuwei
 */
class EnvirUploadController extends BaseController {
    def envirUplaodService
    def uploadToGovService


//    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
    /**
     * @description跳转到环保清单数据导出页面
     * @CreateTime 2017-01-01 by zhuwei
     * @return
     */
    def list() {
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
//        [envirUploadInstanceList: EnvirUpload.list(params), envirUploadInstanceTotal: EnvirUpload.count()]

        render (view: "/cn/com/wz/vehcert/environment/envirUpload/list",model: params)
    }
    /**
     * @Description 未上传环保信息VIN到环保部的页面跳转方法
     * @CreateTime 2017-02-24 by zhuwei
     */
    def uploadList(){
//         def key =  uploadToGovService.enviroLogin()
//        println(key)
//        def logout = uploadToGovService.enviroLogout(key)
//        println(logout)
        render (view: "/cn/com/wz/vehcert/environment/envirUpload/envirUploadList",model: params)
    }
    /**
     * @Description 已上传环保信息VIN到环保部的页面跳转方法
     * @CreateTime 2017-02-24 by zhuwei
     */
    def uploadedList(){
        render (view: "/cn/com/wz/vehcert/environment/envirUpload/envirUploadedList",model: params)
    }
    /**
     * @Description 上传失败环保信息记录list
     * @CreateTime 2017-02-24 by zhuwei
     */
    def failedList(){
        render (view: "/cn/com/wz/vehcert/environment/envirUpload/failedUploadList",model: params)
    }

    /**
     *@Description 获取列表信息
     *@Auther zhuwei
     *@createTime 2016-12-20
     */
    def jsonList(){
        def aaa = params
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def result=envirUplaodService.selectByParams(params)
        render result as JSON
    }

    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }

    /**
     *@Description 重型柴油车/城市车辆环保信息导出
     *@param
     *@return
     *@Auther zhuwei
     *@UpdateBy zhuwei 2017_01_10     增加了车辆型号、发动机型号、汽车分类三个字段的导出
     */
    def exportSearch(){
        def rows =[]
        def content=[]
        try{
            if(params?.format && params.format != "html"){
                def encodedFilename = URLEncoder.encode(("环保随车清单数据导出"), "UTF-8")
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

                Map labels =  [
                        "en_clsbdh":"VIN",
                        "en_xxgkbh":"信息公告开编号",
                        "en_sb"    :"商标",
                        "en_sccdz" :"生产厂地址",
                        "en_fdjh" :"发动机号",
                        "en_scrq":"生产日期",
                        "en_ccrq":"出厂日期",
                        "en_ccsy":"出厂试验",
                        "en_ccjl":"出厂试验结论",
                        "en_gfwz":"公开网站",
                        "en_fdjsb":"发动机商标",
                        "en_fdjsccdz":"发动机生产厂地址",

                        "en_clxh":"车辆型号",
                        "en_fdjxh":"发动机型号",
                        "en_qcfl":"汽车分类",

                        "creater_id":"创建人",
                        "create_time":"创建时间",
                        "type":"类型 "

                ]
                def upperCase = { domain, value ->
                    return value.toUpperCase()
                }
                def out=response.outputStream
                def map=envirUplaodService.selectByParams(params)

                ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
                def m=[]
                m.add(labels)
                content.add(map.rows)
                map.clear()
                excelOp.preWriteExcel(out,null,m,["环保随车清单数据导出"],content)
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
     * @Description手动勾选上传车辆环保信息
     * @CreateTime 2017-03-02 by zhuwei
     */
    def uploadByHand(){
        def key
        def uploadlist = []
        def uploadResult
        try {
            def ids = params?.ids
            //获取连接key
             key =  uploadToGovService.enviroLogin()
            println(key)
            if(ids){
                def idArr =ids?.split("_")
                idArr.each{id->
                    def upload = EnvirUpload.findById(id)
                    uploadlist.add(upload)
                }
                uploadResult =  uploadToGovService.sendVinData(key,uploadlist)
                println(uploadResult)
            }
        }catch (Exception e){
            e.cause.printStackTrace()
        }finally{
           def closeCon = uploadToGovService.enviroLogout(key)
            println(closeCon)
            render uploadResult as JSON
        }
    }
    /**
     * @Description 根据条件上传环保随车请单数据
     * @CreateTime 2017-03-06 by zhuwei
     */
    def uploadByCondition(){
        def key
        def uploadlist
        def uploadResult
        try {
            def aaa = params
            //获取连接key
            key =  uploadToGovService.enviroLogin()
            println(key)
                uploadlist   =  envirUplaodService.selectByCondition(params)
                uploadResult =  uploadToGovService.sendVinData(key,uploadlist)
                println(uploadResult)
//            }
        }catch (Exception e){
            e.cause.printStackTrace()
        }finally{
            def closeCon = uploadToGovService.enviroLogout(key)
            println(closeCon)
            render uploadResult as JSON
        }
    }

    /**
     * @Description 禁止环保随车请单上传，直接做物理删除
     * @Create 2017-03-06  by zhuwei
     */
    def forbid(){
        def result=[:]
        def msg=""
        def flag=true
        try{
            def idsStr=params.ids
            if (idsStr){
                def ids=idsStr.toString().split("_")
                def cel={
                    //解决in中字符串不大于1000的问题
                    def lst=StringUtil.splitIn(ids)
                    and{
                        lst?.each {id->
                            'in'("id",ids)
                        }
                    }
                }
                def  lst=EnvirUpload.createCriteria().list(cel)
                lst?.each{
                    it.delete(flush: true)
                }
            }
            msg="操作成功！"
            flag = true
        }catch(Exception e){
            msg="操作失败！错误原因：${e.cause?e.cause:e}"
            flag = true
        }
        result.flag=flag
        result.msg=msg

        render result as JSON
    }

    /**
     * @Description删除已经上传的环保随车清单数据，这种操作直接在数据中做物理删除
     * @CreateTime 2017-03-06 by zhuwei
     * @Update环保部提供的接口只有单条删除没有批量删除，为了保证数据出来的准确性，每次都只删除一条数据
     *
     */
    def delData(){
        def key
        def delResult
        try {
            def ids = params?.ids
            //获取连接key
            key =  uploadToGovService.enviroLogin()
            println(key)
            if(ids){
                def idArr =ids?.split("_")
                idArr.each{id->
                    def delInstance = EnvirUpload.findById(id)
                    delResult =  uploadToGovService.delData(key,delInstance.en_clsbdh)
                    println(delResult)
                }

            }
        }catch (Exception e){
            e.cause.printStackTrace()
        }finally{
            def closeCon = uploadToGovService.enviroLogout(key)
            println(closeCon)
            render delResult as JSON
        }

    }


}
