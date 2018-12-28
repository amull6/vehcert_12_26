package cn.com.wz.parent.system
import grails.converters.JSON;
import cn.com.wz.parent.ConstantsUtil;
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.remind.Remind;
import cn.com.wz.parent.system.user.User;
class RemindController extends BaseController{
	
	def preferenceService
	/**
	 * @Description 跳转到list页面
	 * @Author wangtao
	 * @CreateTime 2013-06-17
	 */
    def index() { 
		render(view:'/cn/com/wz/parent/system/remind/list',model:[mm:'mm'])
	}
	
	/**
	 * @Description 跳转到list页面
	 * @Author wangtao
	 * @CreateTime 2013-06-17
	 */
	def list(){
		render(view:'/cn/com/wz/parent/system/remind/list')
	}
	/**
	 * @Description 跳转到新建页面
	 * @Author wangtao
	 * @CreateTime 2013-06-17
	 */
	def create(){
		render(view:'/cn/com/wz/parent/system/remind/create',model:[mm:'mm'])
	}
	/**
	 * @Description 发送一条消息提醒
	 * @Author wangtao
	 * @CreateTime 2013-06-17
	 */
	def send(){
		Remind.withTransaction {
			String receiverIds=params.receiverIds
			//分别保存接收人的内部消息
			def ids=receiverIds?.split("_")
			def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
			def loginUser=User.get(user.id)
			if(ids?.length>0){
				 for(int i=0;i<ids.length;i++){
					 def receiver=ids[i].split";"
					  Remind a=new Remind ();
					  a.content=params.content
					  a.receiverId=receiver[0]
					  //保存发件人信息
					  a.createrId=user.id
					  a.createTime=new Date()
					  a.save(flush:true)
				}
			}
		}
		render(view:'/cn/com/wz/parent/system/remind/create',model:[mm:'mm'])
	}
	/**
	 * @Description 历史提醒取数函数
	 * @Author wangtao
	 * @CreateTime 2013-06-18
	 */
	def jsonList(){
		params.max =params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def cel = {
			eq('createrId',user.id)
		   order ('createTime','desc')
	   }
	   def results = Remind.createCriteria().list(params,cel)
	   def result = [rows: results, total: results.totalCount]
	   render result as JSON
	}
	
	/**
	 * @Description 获取提醒消息的数量，做判断之用
	 * @Author wangtao
	 * @CreateTime 2013-06-20
	 */
	def msgCount(){
		def flag='no'
		def countDic=servletContext.getAttribute("dic_count")
		//通过字典取得，当消息的数量大于等于mmm的值时，将返回showMore用列表展示消息。
		def mmm=countDic?.value1?.toInteger()
		if(mmm==null||mmm==''){
			mmm=5
		}
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def cel = {
			eq('receiverId',user.id)
	   }
	   def results = Remind.createCriteria().list(params,cel)
	   if(results.size()>0&&results.size()<mmm){
		   flag="showOne"
	   }else if(results.size()>=mmm){
	   	flag="showMore"
	   }
	   
	   render flag
	}
	
	/**
	 * @Description 定时查看消息提醒取数函数
	 * @Author wangtao
	 * @CreateTime 2013-06-18
	 */
	def getMsgs(){
		params.max =params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def cel = {
			eq('receiverId',user.id)
		   order ('createTime','desc')
	   }
	   def results = Remind.createCriteria().list(params,cel)
	   def lst=[]
	   if(results.size()>0){
		   results.each{r->
			   def m=[:]
			   m.createTime=r.createTime
			   m.content=r.content
			   m.module=r.module
			   m.recordId=r.recordId
			   m.status=r.status
			   lst.add(m)
			   r.delete(flush:true)
		   }
		 def result = [rows: lst, total: results.totalCount]
		 if(params.flag=="1"){//表示单个弹出，要返回一个简单的list
			 render lst as JSON
		 }else{//取数函数是被omGrid调用，要返回这种类型的。
		 	render result as JSON
		 }
		   
	   }
	}
	
	/**
	 * @Description 当提醒消息超过5条时，从此处跳转到页面展示
	 * @Author wangtao
	 * @CreateTime 2013-06-20
	 */
	def showMsgs(){
		render(view:'/cn/com/wz/parent/system/remind/showMsgs',model:params)
	}
	
	/**
	 * @Description 跳转到消息提醒配置页面并判断用户是否保存了个性化消息提醒配置信息，如果有就取出配置信息并传递到配置页面，如没有就将默认配置传递掉配置页面。
	 * @Author wangtao
	 * @CreateTime 2013-06-14
	 */
	def remindCon(){
		def msgCon=''
		def smsCon=''
		def roleFlag=''
		def phoneFlag=''
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def loginUser=User.get(user.id)
		if(user.userDetail?.mPhone){
			phoneFlag='phone'
		}
		//判断用户是否有接收手机短信权限
		loginUser?.roles?.each{
			if(it.roleCode=='smsReceiver'){
				roleFlag='smsReceiver'
			}
		}
		//获取用户系统消息提醒配置信息
		
		def msg=servletContext.getAttribute("msgCon_${user.id}")
		if(msg){
			msgCon=msg.preValue
		}else{
			msgCon=servletContext.getAttribute('dic_defaultMsgCon').value1
		}
		//获取用户手机短信提醒配置信息
		def sms=servletContext.getAttribute("smsCon_${user.id}")
		if(sms){
			smsCon=sms.preValue
		}else{
			smsCon=servletContext.getAttribute('dic_defaultSmsCon').value1
		}
		render(view:'/cn/com/wz/parent/system/remind/configuration',model:[msgCon:msgCon,smsCon:smsCon,roleFlag:roleFlag,phoneFlag:phoneFlag])
	}
	
	/**
	 * @Description 保存用户的个性化消息提醒配置
	 * @Author wangtao
	 * @CreateTime 2013-06-14
	 */
	def msgConSave(){
		def message="保存成功"
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def loginUser=User.get(user.id)
		params.preName="系统消息提醒配置"
		params.preValue=params.msgCheckIds
		params.code='msgCon'
		params.showOrder=3
		params.preDesc="保存了用户自定义配置的系统消息提醒"
		def flag=preferenceService.save(loginUser,params,servletContext)
		if(flag){
			render message
		}else{
			message="保存失败"
			render message
		}
	
	}
	
	/**
	 * @Description 保存用户的个性化手机提醒配置
	 * @Author wangtao
	 * @CreateTime 2013-06-14
	 */
	def smsConSave(){
		def message="保存成功"
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def loginUser=User.get(user.id)
		params.preName="手机短信提醒配置"
		params.preValue=params.smsCheckIds
		params.code='smsCon'
		params.showOrder=3
		params.preDesc="保存了用户自定义配置的手机短信提醒"
		def flag=preferenceService.save(loginUser,params,servletContext)
		if(flag){
			render message
		}else{
			message="保存失败"
			render message
		}
	}
}
