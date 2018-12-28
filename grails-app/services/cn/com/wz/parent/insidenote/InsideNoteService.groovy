package cn.com.wz.parent.insidenote

import grails.converters.JSON

/**
 *@Description 内部短信services类
 *@Auther huxx
 *@createTime 2012-12-14 下午2:54:24
 */
class InsideNoteService {

    def serviceMethod() {

    }
	/**
	 *@Description 获取未读短信数
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-12-14 下午2:56:15
	 */
	Integer getUnReadInsideNoteCount(params){
		def cel = {
			eq('receiverId',params.userId)
			inList('status',params.status)
		}
		def results = InsideNote.createCriteria().count(cel)
	}
	/**
	 *@Description 获取未读且需要提醒的短信数
	 *@param
	 *@return
	 *@Auther liuly
	 *@createTime 2012-04-12
	 */
	Integer getUnReadInsideNoteCountFlag(params){
		
		def cel = {
			eq('receiverId',params.userId)
			inList('status',params.status)
			
		}
		def results = InsideNote.createCriteria().count(cel)
	}
	/**
	 *@Description 根据条件获取发件箱从信息
	 *@param
	 *@return
	 *@Auther zhuwei
	 *@createTime 2012-12-15 上午8:37:34
	 */
	def getNotesByParams(params){
		//处理时间参数信息
		def startTime
		def endTime
		if(params.firstTime){
			startTime=java.sql.Date.valueOf(params.firstTime);
		}else{
			startTime=null
		}
		if(params.lastTime){
			endTime=java.sql.Date.valueOf(params.lastTime)+1;//因为传入的时间精确到天，如果想查询出结束时间那一天的记录就需要取时间大于等于endtime+1的日期
		}else{
			endTime=null
		}

		def cel = {
		
		  //##################公共查询条件############
			if(startTime){
				ge('sendTime',startTime)
			 }
			 if(endTime){
				le('sendTime',endTime)
			 }
		   //如果是前台调用就只获取未读短信
		   inList('status',params.status)
		 //##################公共查询条件END############
		 //#################接收人查询条件###############
		   if(params.senderId){
			   eq('senderId',params.senderId)
		   }
		   if(params.receiverId){
			    eq('receiverId',params.receiverId)
		   }
		   
		   if(params.senderName){
			   like("senderName", "%${params.senderName}%")
		   }
		 //#################接收人查询条件END###############
		 //#################发件人查询条件################
		   if(params.receiverName){
			   like("receiverName", "%${params.receiverName}%")
		   }
		 //#################发件人查询条件END################
		   
		   //排序条件
		   order (params.sort,params.order)
	   }
	   def results = InsideNote.createCriteria().list(params,cel)
	   def result = [rows: results, total: results.totalCount]
	   return result as JSON
	}
	/**
	 *@Description 内部消息发送接口方法,本方法接收一个map类型参数，该参数包含senderId(必填)、senderName、receiverId(必填)、receiverName(必填)、title(必填)、content(必填)。
	 *@param
	 *@return
	 *@Auther zhuwei
	 *@createTime 2012-12-15 上午8:37:34
	 */
	def sendMessage(Map message){
		InsideNote insideNoteInstance=new InsideNote ();
		insideNoteInstance.receiverName=message.receiverName
		insideNoteInstance.receiverId=message.receiverId
		insideNoteInstance.content=message.content
		insideNoteInstance.senderName=message.senderName
		insideNoteInstance.senderId=message.senderId
		insideNoteInstance.sendTime=new Date()
		insideNoteInstance.status=1
		insideNoteInstance.save(flush:true)
	}
}
