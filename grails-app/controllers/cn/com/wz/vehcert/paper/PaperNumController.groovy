package cn.com.wz.vehcert.paper

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController;
import cn.com.wz.parent.system.user.User
import parent.poi.ExcelUtils

import java.text.SimpleDateFormat;
import grails.converters.JSON

/**
 *@Description 合格证纸张管理-查询
 *@Auther liuly
 *@createTime 2013-08-20
 */
class PaperNumController extends BaseController {
    def exportService
    def sqlService
    def paperNumService
    def index() {
        redirect(action: "list", params: params)
    }
    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-20
     */
    def list() {
        render (view: "/cn/com/wz/vehcert/paper/paperNum/index",model: params)
    }
    /**
     *@Description 用于生成表格数据   获取满足条件的用户信息列表
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-20
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        //经销商编号和名称
        def rows=paperNumService.paperNumRows(params)
        def list=[]
        def start=Integer.parseInt(params.start)

        def limit=Integer.parseInt(params.limit)
         //实现分页
        def total=start+limit
        if (start+limit>rows.size()){
            total=rows.size()
        }
        for(int i=start;i<total;i++){
             list.add(rows.get(i))
        }
        def result = [rows:list,total:rows.size() ]
        render result as JSON
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
            def encodedFilename = URLEncoder.encode(("纸张数量查询"), "UTF-8")
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
            //List fields = ["dealerNum", "dealerName","num","status","note"]
            Map labels =  ["dealerNum":"经销商编号", "dealerName":"经销商姓名","num":"纸张数量","type":"合格证类型：0.汽车整车，1.农用车整车,2.二类底盘","status":"状态","note":"备注"]

            def out=response.outputStream
           /* Map m=(Map)JSON.parse(jsonList())*/
            rows=paperNumService.paperNumRows(params)
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(rows)
            excelOp.preWriteExcel(out,null,m,["纸张数量记录"],content)
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
