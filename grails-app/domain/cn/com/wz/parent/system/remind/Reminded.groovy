package cn.com.wz.parent.system.remind

import java.util.Date;

import org.springframework.orm.hibernate3.support.ClobStringType;

class Reminded {
	//id
	String id
	//提醒的内容
	String content
	//提醒的生成时间
	Date createTime
	//提醒生成时的操作人id
	String createrId
	//被提醒人的id
	String receiverId
	
	//产生提醒的业务模块
	String module
	//产生提醒消息的记录的id
	String recordId
	//提醒是否提供查看详情的按钮,0表示不提供，1表示提供。
	int status=0
	
	//字段限制条件
	static constraints = {
		content(nullable:false)
		createTime(nullable:false)
		createrId(nullable:true)
		receiverId(nullable:false)
		module(nullable:true)
		recordId(nullable:true)
		status(nullable:false)
	}
	
	//数据库映射
	static mapping={
		table 'SYS_REMINDED'
		id generator:'uuid.hex', column:'id'
		content type: ClobStringType
	}
}
