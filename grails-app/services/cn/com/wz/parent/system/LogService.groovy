package cn.com.wz.parent.system

import cn.com.wz.parent.DateUtil;
import cn.com.wz.parent.system.dictionary.DictionaryValue;
import cn.com.wz.parent.system.log.Log

/**
 *@Description 系统日志service类
 *@Auther huxx
 *@createTime 2012-11-5 下午3:45:18
 */
class LogService {
     def grailsApplication
    def serviceMethod() {

    }
	
	/**
	 *@Description 保存日志信息 
	 *@param userId 操作人id
	 *operateObject操作对象（在字典中维护）
	 *operateContent操作内容
	 *logTypeCode日志类型编码（在字典中维护）systemLog系统日志;businessLog业务日志
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-11-5 下午3:47:49
	 */
	def insertLog(String userId,String operateObjectCode,String operateContent,String logTypeCode){
		Log log=new Log()
		log.userId=userId
		log.operateContent=operateContent
		log.createTime=DateUtil.getCurrentTime()
		log.appCode=grailsApplication.config.app.appCode
		//获取操作对象信息
		def o=DictionaryValue.findByCode(operateObjectCode)
		log.operateObject=o
		//获取日志类型
		def logType=DictionaryValue.findByCode(logTypeCode)
		log.logType=logType
		
		log.save(flush:true)
	}
}
