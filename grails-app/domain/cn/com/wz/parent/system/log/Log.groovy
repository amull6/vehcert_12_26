package cn.com.wz.parent.system.log

import cn.com.wz.parent.system.dictionary.DictionaryValue;
/**
 *@Description 日志domain类
 *@Auther huxx
 *@createTime 2012-11-5 上午9:34:09
 */
class Log {
	String id;
	/**
	 * 开始时间
	 */
	String startTime;
	/**
	 * 结束时间
	 */
	String endTime;
	/**
	 * 操作人名称
	 */
//	String userName;
	/**
	 * 操作人id
	 */
	String userId;
	/**
	 * ip
	 */
	String remoteIp;
	/**
	 * 操作类型
	 */
	String operateType;
	/**
	 * 操作内容
	 */
	String operateContent;
	/**
	 * 操作标识
	 */
	String flag;
	/**
	 * 创建时间
	 */
	String createTime;
	
//	String logTypeName
	
//	String objectName
    /**
     * 系统代号
     */
    String appCode
//    String theSystem
	static belongsTo=[logType:DictionaryValue,operateObject:DictionaryValue]
    static constraints = {
		logType()
		startTime(nullable:true)
		endTime(nullable:true)
//		userName(nullable:true)
		userId()
		remoteIp(nullable:true)
		operateObject()
		operateType(nullable:true)
		operateContent()
		flag(nullable:true)
		createTime()
        appCode(nullable: true)
//        theSystem(nullable: true)
//		logTypeName(nullable:true)
//		objectName(nullable:true)
    }
	/**
	 *与数据库库数据映射（定义数据库数据存储名称）
	 */
	static mapping = {
		table 'sys_log'
		id generator:'uuid.hex', column:'ID'
	}
}
