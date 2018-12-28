package cn.com.wz.parent.system.log
import parent.poi.ExcelUtils;
import cn.com.wz.parent.base.BaseController;
import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON;
import cn.com.wz.parent.base.BaseController;
import cn.com.wz.parent.system.dictionary.DictionaryValue;
import cn.com.wz.parent.system.user.User;
/**
 *@Description 系统日志管理controller类 
 *@Auther huxx
 *@createTime 2012-11-6 上午10:58:27
 */
class LogController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def exportService

    def index() {
       redirect(action:'list',params:params)
    }

	/**
	 *@Description 跳转到日志list页面
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-11-6 上午10:59:41
	 */
	def list() {
		render(view:'/cn/com/wz/parent/system/log/list')
	}
	/**
	 *@Description ajax获取列表中的日志记录
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-11-6 上午11:00:04
	 */
	def jsonList(){
		params.max = params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		params.sort='createTime'
		params.order="desc"
		
		def dd=params
		def cel = {
			if(params.firstTime){
				ge('createTime',params.firstTime)
			 }
			if(params.secondTime){
				le('createTime',params.secondTime)
			 }
			
			if(params.operateObject?.id){
				operateObject {eq('id',"${params.operateObject.id}")}
			}
			if(params.logType?.id){
				logType{eq('id',"${params.logType.id}")}
			}
            if(params.theSystem?.code){
                eq('appCode',"${params.theSystem.code}")
            }
		}
		def results = Log.createCriteria().list(params,cel)
        def lst=[]
        if(results.size()>0){
            results.each{l->
                def m=[:]
                m.logTypeName=DictionaryValue.get(l.logType?.id)?.dicValueNameC
                m.userName=User.get(l.userId)?.userDetail?.realName
                m.objectName=DictionaryValue.get(l.operateObject?.id)?.dicValueNameC
                def theSys=DictionaryValue.findByCode(l.appCode)
                if (theSys) {
                    m.theSystem=DictionaryValue.findByCode(l.appCode)?.dicValueNameC
                } else{
                    m.theSystem=l.appCode
                }

                m.id=l.id
                m.startTime=l.startTime
                m.endTime=l.endTime
                m.remoteIp=l.remoteIp
                m.operateType=l.operateType
                m.operateContent=l.operateContent
                m.flag=l.flag
                m.createTime=l.createTime
                lst.add(m)
            }
        }
//		results?.each { l->
//            def theSystem=DictionaryValue.findByCode(l.appCode)
//			l.logTypeName=DictionaryValue.get(l.logType?.id)?.dicValueNameC
//			l.userName=User.get(l.userId)?.userDetail?.realName
//			l.objectName=DictionaryValue.get(l.operateObject?.id).dicValueNameC
//            l.theSystem=theSystem
//		}
		
		def result = [rows: lst, total: results.totalCount]
		
		render result as JSON
	}
	/**
	 *@Description 导出功能
	 *@param
	 *@return
	 *@Auther liuly
	 *@createTime 2013-03-19
	 */
	def export(){
		if(params?.format && params.format != "html"){
			response.contentType = grailsApplication.config.grails.mime.types[params.format]
			response.setHeader("Content-disposition", "attachment; filename=Log.${params.extension}")
			List fields = ["logType.DicValueNameC", "userName","operateObject.DicValueNameC","operateContent","createTime"]
			Map labels =  ["logType.DicValueNameC":"日志类型","userName":"操作人","operateObject.DicValueNameC":"操作对象","operateContent":"操作内容","createTime":"创建时间"]
			def upperCase = { domain, value ->
				return value.toUpperCase()
			}
			Map formatters = [DicValueNameC: upperCase]
			Map parameters = [title: "系统日志", "column.widths": [0.3, 0.3, 0.3,0.3,0.3]	,"pdf.encoding":"UniGB-UCS2-H", "font.family": "STSong-Light"]
			def out=response.outputStream
			def cel = {
			if(params.firstTime){
				ge('createTime',params.firstTime)
			 }
			if(params.secondTime){
				le('createTime',params.secondTime)
			 }
			
			if(params.operateObject){
				operateObject {eq('id',"${params.operateObject}")}
			}
			if(params.logType){
				logType{eq('id',"${params.logType}")}
			}
			order("createTime","desc")
		}
		   def results = Log.createCriteria().list(params,cel)
			exportService.export(params.format, out, results, fields, labels, formatters, parameters)
			out.flush()
			out.close()
		}
   }
   
}
