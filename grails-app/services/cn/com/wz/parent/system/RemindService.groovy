package cn.com.wz.parent.system

import cn.com.wz.parent.system.dictionary.DictionaryValue;
import cn.com.wz.parent.system.remind.Remind;
import cn.com.wz.parent.system.remind.Reminded;
import groovy.text.SimpleTemplateEngine

class RemindService {
	
	/**
	 *@Description 根据事件的代码和用户的个性配置及默认配置，判断是否发送系统消息提醒
	 *@param
	 *@return
	 *@Auther wangtao
	 *@createTime 2013-6-22 上午10:52:18
	 */
	def sendOrNot(receiverId,code,servletContext){
		def msgCon=''
		def pre=servletContext.getAttribute("msgCon_${receiverId}")
		if(pre){
			msgCon=pre.preValue
		}else{
			msgCon=servletContext.getAttribute("dic_defaultMsgCon").value1
		}
		//判断是否需要发送消息提醒，如果需要的返回true，否则反回false。
		def codes=msgCon.split(':_:')
		for(String tmpCode in codes){
			if(code==tmpCode){
				return true
			}
		}
		return false
	}
	
	//该方法接收一个map类型的message， 其中的code和receiverId是必填的
    def sendMsg(Map message) {
		def remindInstance=new Remind()
		remindInstance.receiverId=message.receiverId
		remindInstance.content=new SimpleTemplateEngine().createTemplate(message.msgtemplate).make(message);
		remindInstance.createTime=new Date()
		remindInstance.module=message.module
		remindInstance.recordId=message.recordId
		if(message.checkFlag=="check"){
			remindInstance.status=1
		}
		remindInstance.save(flush:true)
    }
	
}
