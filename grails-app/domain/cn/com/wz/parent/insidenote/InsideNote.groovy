package cn.com.wz.parent.insidenote

import java.sql.Clob;
/**
 * Description  网站内部信息域类
 * @author 
 * @CreateTime 2012-10-29
 */
class InsideNote {
	String id
	/**
	 * 发送信息人id
	 */
	String senderId
	/**
	 * 发送信息人名称
	 */
	String senderName
	/**
	 * 接收人id
	 */
	String receiverId
	/**
	 * 接收人名称
	 */
	String receiverName
	/**
	 * 发送内容
	 */
	String content
	/**
	 * 发送时间
	 */
	Date sendTime
	/**
	 * 短信状态，0未发送；1已发送未读；2已发送已读；3发件人已删未读；4发件人已删已读；5收件人已删。
	 */
	int status

	
    static constraints = {
		senderId(blank:false,nullable:true)
		senderName(nullable:true)
		receiverId(nullable:true)
		receiverName(blank:true)
		content(blank:false)
		sendTime(nullable:true)
		
    }
	
	/**
	 *与数据库库数据映射（定义数据库数据存储名称）
	 */
	static mapping = {
		table 'TBL_insideNote'
		id generator:'uuid.hex', column:'ID'
		
	}
}
