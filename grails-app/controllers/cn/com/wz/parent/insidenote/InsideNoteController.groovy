package cn.com.wz.parent.insidenote

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import org.springframework.dao.DataIntegrityViolationException

/**
 *@Description 内部短信管理：收件箱、发件箱、草稿箱
 *@Auther  
 *@createTime 2012-10-29
 */
class InsideNoteController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def insideNoteService
	/**@Description默认跳转显示发件箱
	 * @Auther 
	 * @return
	 */
    def index() {
        redirect(action: "outBox", params: params)
    }
	
	/**@Description跳转到收信人弹出框
	 * 
	 * @return
	 */
    def receiver(){
		def receivers=[]
		def receiverIds=params.receiverIds
		if(receiverIds!=null&&receiverIds!=""){
			def idsArray=receiverIds?.split('_')
			idsArray?.each{i->
				receivers+=User.get(i)
				}
			}
		render(view:'/cn/com/wz/parent/insidenote/receiver',model:[tabId:params.tabId,receivers:receivers])
	}
	/**
	 * @Description 跳转到发件箱
	 * @params 
	 * @return
	 * @createTime 2012-10-18
	 * @author wt
	 * @update 如果是在线人员页面或者活动页面发送短信成功后就跳转回原来的页面
	 */
	def outBox(){
		if(params.nodeType=='online'){
			render(view:'/cn/com/wz/parent/system/log/logUser',model:[tabId:params.tabId])
		}
        if(params.nodeType=='sysonline'){
            render(view:'/cn/com/wz/parent/system/log/logSys',model:[tabId:params.tabId])
        }
		else if(params.nodeType=='activity'){
			render(view:'/cn/com/wz/parent/staffactivity/activity/register_list',model:[id:params.activityId])
		}
		else{
			render (view:'/cn/com/wz/parent/insidenote/outBox_list',model:[tabId:params.tabId])
		}
	}

	/**
	 * @Description 跳转到收件箱
	 * @params
	 * @return
	 * @createTime 2012-10-18
	 * @author wt
	 * @update huxx 2012-12-14 添加了操作标识opFlag，以区分是前台页面调用的还是后台页面调用的
	 * opFlag='index '表示前台调用；否则表示后台调用
	 */
	def inBox(){
		render (view:'/cn/com/wz/parent/insidenote/inBox_list',model:[opFlag:params.opFlag,tabId:params.tabId])
	}

	/**
	 * @Description 跳转到草稿箱
	 * @params
	 * @return
	 * @createTime 2012-10-18
	 * @author wt
	 */
	def draftBox(){
		
		render (view:'/cn/com/wz/parent/insidenote/draftBox_list',model:[tabId:params.tabId])
	}

	/**
	 * @Description 生成收件箱列表，并用该方法在收件箱中查询
	 * @params
	 * @return
	 * @createTime 2012-10-24
	 * @author wt
	 * @update huxx 2012-12-14 将处理放到了services中，并添加了操作条件
	 */
	def jsonInBoxList(){
		//设置分页条件
		params.max =params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		//设置查询条件
		params.sort='sendTime'
		params.order='desc'
		
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		params.receiverId=user?.id
		
		//设置查询短信状态
		if('index'.equals(params.opFlag)){
			params.status=[1,3]
		}else{
			   params.status=[1,2,3,4]
		}
		
		def result=insideNoteService.getNotesByParams(params)
		render result
	}
	/**
	 * @Description 生成草稿箱列表并查询方法
	 * @params
	 * @createTime 2012-10-24
	 * @author wt
	 */
	def jsonDraftBoxList(){
		params.max =params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		//设置排序条件
		params.sort='sendTime'
		params.order='desc'
		
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		params.senderId=user?.id
		
		//设置短信状态
		params.status=[0]
		
		def result=insideNoteService.getNotesByParams(params)
		render result
	}
	/**
	 * @Description 发件箱列表生成和查询方法
	 * @params
	 * @return
	 * @createTime 2012-10-24
	 * @author wt
	 */
	def jsonOutBoxList(){
		params.max = params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		
		//设置排序条件
		params.sort='sendTime'
		params.order='desc'
		
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		params.senderId=user?.id
		
		//设置短信状态
		params.status=[1,2,5]
		def result=insideNoteService.getNotesByParams(params)
		render result
	}
  
	/**
	 * @Description 通过用户详细信息获取用户信息(员工活动)
	 * @params
	 * @return
	 * @createTime 2012-12-24
	 * @author zhuwei
	 */
	def getUserByUserDetail(){
		def receiverIds=''
		def receiverName=""
		def dids=params.receiverId?.split("_")
		if(dids?.length>0){
			for(int i=0;i<dids.length;i++){
				def user=User.get(dids[i])
				def organNames=''
				user.organs?.each {
					organNames+=it.organNameC+"、"
				}
				if(organNames){
					organNames=organNames.substring(0,organNames.lastIndexOf("、"))
				}
				def userinfo=user.id+';'+user.userName+";"+user.userDetail.realName+';'+organNames+";"+user.userDetail?.mPhone+";"+"systemUser";
				receiverIds+=userinfo+"_"
				receiverName+=user.userDetail?.realName+";";
				}
			}
		 params.receiverIds=receiverIds
		 params.receiverName=receiverName
		 redirect(action: "create", params: params)
		}

    /**
     *
     * @return
     */
    def create() {
		def insideNoteInstance=new InsideNote(params)
		if(params.nodeType){
			insideNoteInstance.receiverName=params.receiverName
		}
		render(view: "/cn/com/wz/parent/insidenote/create",model:[tabId:params.tabId,insideNoteInstance: insideNoteInstance,receiverIds:params.receiverIds,nodeType:params.nodeType,activityId:params.activityId])
    }
	/**
	 * @Description 回复
	 * @params
	 * @return
	 * @createTime 2013-06-28
	 * @author liuly
	 */
	def newReply(){
		def insideNote=InsideNote.get(params.id)
		//回复时将消息设为已读状态
		if(insideNote.status==1){
			insideNote.status=2
			insideNote.save(flush: true)
		}else if(insideNote.status==3){
			insideNote.status=4
			insideNote.save(flush: true)
		}
		def insideNoteInstance=new InsideNote()
		insideNoteInstance.receiverId=insideNote.senderId
		insideNoteInstance.receiverName=insideNote.senderName
		render(view: "/cn/com/wz/parent/insidenote/reply",model:[insideNoteInstance: insideNoteInstance])
	}
	
	def reply(){
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def senderName=user?.userDetail?.realName
		def insideNoteInstance=new InsideNote(params)
		insideNoteInstance.senderId=user.id
		insideNoteInstance.senderName=senderName
		insideNoteInstance.sendTime=new Date()
		insideNoteInstance.status=1
		if(insideNoteInstance.save(flush:true)){
			render "回复成功！"
		}else{
			render "回复失败！"
		}
		
	}
	/**
	 * @Description 将信息保存到草稿箱，给状态status赋值为0。
	 * @params
	 * @return
	 * @createTime 2012-10-24
	 * @author zhuwei
	 * @update 修改添加不填不填项的校验提示
     * @update liuly 2013-07-02 删除了后台验证
	 */
    def save() {

		//获取接受人的名称和id,receiverIds实在form中的隐藏域保存的
		String receiverIds=params.receiverIds
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def senderName=user?.userDetail?.realName
		//分别保存接收人的内部消息
		def ids=receiverIds?.split("_")
		if(ids?.length>0){
		     for(int i=0;i<ids.length;i++){
				 def receiver=ids[i].split";"
                  InsideNote a=new InsideNote ();
				  a.receiverName=receiver[2]
				  a.receiverId=receiver[0]
				  a.content=params.content
				  //保存发件人信息
				  a.senderName=senderName
				  a.senderId=user.id
				  a.sendTime=new Date()
				  a.status=0
				  a.save(flush:true)
			}
		}
		
		flash.message = message(code: 'default.created.message', args: [message(code: 'insideNote.label', default: 'InsideNote'), params.id])
        redirect(action: "draftBox")
    }
	
	/**
	 * @Description 新建内部消息发送和草稿箱编辑页面发送按钮的发送方法
	 * @params
	 * @return
	 * @createTime 2012-10-24
	 * @author zhuwei
	 * @update zhuwei
	 * @update 修改添加不填不填项的校验提示
     * @update huxx 2013-07-02 1、删除了后台验证。2、redirect时参数不能太长
	 */
	def send(){
		//发送后跳转页面，针对我的活动列表的取消操作
		def nodeType=''
		params.nodeType.each{
			nodeType=it
		}
		def opFlag=params.opFlag
		//获取接受人的名称和id,receiverIds实在form中的隐藏域保存的
		def receiverIds=params.receiverIds
		//分别保存接收人的内部消息
		def ids=receiverIds?.split("_")
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def senderName=user.userDetail?.realName
		if(ids?.length>0){
			 for(int i=0;i<ids.length;i++){
				 def receiver=ids[i].split";"
				  InsideNote a=new InsideNote ();
				  a.receiverName=receiver[2]
				  a.receiverId=receiver[0]
				  a.content=params.content
				  //保存发件人信息
				  a.senderName=senderName
				  a.senderId=user.id
				  a.sendTime=new Date()
				  a.status=1
				  a.save(flush:true)
			}
		}
		//传递活动id
		def activityId=''
		params.activityId.each{
			activityId=it
		}
		if(nodeType=='activityList'){
			//跳转到活动列表
			redirect(controller:"activity",action: "index")
		}else if(nodeType=='activity'){
		//跳转到某活动的员工列表
			def content='发送成功！'
			redirect(controller:"activity",action: "registerList",params:[id:activityId,send:0,content:content])
		}else{
		    //根据传过来的opFlag值来判断删不删除草稿箱的信息
			if('1'.equals(opFlag)){
				flash.message = message(code: 'default.created.message', args: [message(code: 'insideNote.label', default: 'InsideNote'), params.id])
				redirect(action: "outBox")
			}else{
                flash.message = message(code: 'default.created.message', args: [message(code: 'insideNote.label', default: 'InsideNote'), params.id])
                redirect(action: "outBox", params: [tabId: params.tabId,activityId:params.activityId])
		    }
		}
		
	}


	/**
	 * @Description ajax send message(草稿箱列表发送按钮使用的发送方法)
	 * @author wt
	 * @createTime 2012-10-20
	 * @return
	 */
	def jsonSend(){
		def ids = params.sendIds
		def idsArray = ids.split('_')
		def flag = true
		def msg = message(code: 'insideNote.send.success')
		idsArray.each {id->
			def insideNote =InsideNote.get(id)
			insideNote.status=1
			insideNote.sendTime=new Date()
			if (!insideNote.save(flush:true)) {
				flag=false
			}

			if(!flag){
				msg = message(code:'insideNote.send.fail')
			}
		}
		
		render msg
	}
	
	
	
	/**
	 * @Description 草稿箱编辑按钮
	 * @params
	 * @return
	 * @createTime 2012-10-24
	 * @author wt
	 */
    def edit() {
        def insideNoteInstance = InsideNote.get(params.id)
		def receiverIds=insideNoteInstance.receiverId
        if (!insideNoteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'insideNote.label', default: 'InsideNote'), params.id])
            redirect(action: "draftBox")
            return
        }

		render (view:'/cn/com/wz/parent/insidenote/edit',model: [insideNoteInstance: insideNoteInstance,receiverIds:receiverIds])
	}

	/**
	 * @Description 草稿箱更新
	 * @params
	 * @return
	 * @createTime 2012-10-24
	 * @author wt
	 */
    def update() {
        def insideNoteInstance = InsideNote.get(params.id)
        if (!insideNoteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'insideNote.label', default: 'InsideNote'), params.id])
            redirect(action: "draftBox")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (insideNoteInstance.version > version) {
                insideNoteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'insideNote.label', default: 'InsideNote')] as Object[],
                          "Another user has updated this InsideNote while you were editing")
                render(view: "/cn/com/wz/parent/insidenote/edit", model: [insideNoteInstance: insideNoteInstance])
                return
            }
        }

        insideNoteInstance.properties = params

        if (!insideNoteInstance.save(flush: true)) {
            render(view: "/cn/com/wz/parent/insidenote/edit", model: [insideNoteInstance: insideNoteInstance,receiverIds:params.receiverIds])
            return
        }

//		flash.message = message(code: 'default.updated.message', args: [message(code: 'insideNote.label', default: 'InsideNote'), insideNoteInstance.title])
        redirect(action: "draftBox", id: insideNoteInstance.id)
    }
	/**
	 * @Description 发件箱删除
	 * @params
	 * @return
	 * @createTime 2012-10-25
	 * @author wt
	 */
	def jsonOutBoxDelete(){
		def ids = params.deleteIds
		def idsArray = ids.split('_')
		def flag = true
		def msg = message(code: 'default.deleted.simple.message')
		idsArray.each {id->
			def insideNote =InsideNote.get(id)
			if (!insideNote) {
				//msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				return
			}
			try {
				if(insideNote.status==1){
					insideNote.status=3
				}else if(insideNote.status==2){
					insideNote.status=4
				}else if(insideNote.status==5){
				insideNote.delete(flush: true)
				}
				//msg << message(code: 'default.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
			}catch (DataIntegrityViolationException e) {
				//msg << message(code: 'default.not.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				flag = false
			}
			if(!flag){
				msg = message(code: 'default.not.simple.deleted.message')
			}
		}
		
		render msg
		
	}
	/**
	 * @Description 收件箱删除
	 * @params
	 * @return
	 * @createTime 2012-10-25
	 * @author wt
	 */
	def jsonInBoxDelete(){
		def ids = params.deleteIds
		def idsArray = ids.split('_')
		def flag = true
		def msg = message(code: 'default.deleted.simple.message')
		idsArray.each {id->
			def insideNote =InsideNote.get(id)
			if (!insideNote) {
				//msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				return
			}
			try {
				if(insideNote.status==1||insideNote.status==2){
					insideNote.status=5
				}else if(insideNote.status==3||insideNote.status==4){
				insideNote.delete(flush: true)
				}
				//msg << message(code: 'default.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
			}catch (DataIntegrityViolationException e) {
				//msg << message(code: 'default.not.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				flag = false
			}
			if(!flag){
				msg = message(code: 'default.not.simple.deleted.message')
			}
		}
		
		render msg
		
	}
	/**
	 * @Description 收件箱将未读设为已读
	 * @params
	 * @return
	 * @createTime 2012-10-25
	 * @author wt
	 */
	def jsonInBoxRead(){
		def ids = params.deleteIds
		def idsArray = ids.split('_')
		def flag = true
		def msg = message(code: 'default.read.simple.message')
		idsArray.each {id->
			def insideNote =InsideNote.get(id)
			if (!insideNote) {
				//msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				return
			}
			try {
				if(insideNote.status==1){
					insideNote.status=2
					insideNote.save(flush: true)
				}else if(insideNote.status==3){
					insideNote.status=4
					insideNote.save(flush: true)
				}
				

				//msg << message(code: 'default.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
			}catch (DataIntegrityViolationException e) {
				//msg << message(code: 'default.not.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				flag = false
			}
			if(!flag){
				msg = message(code: 'default.not.simple.read.message')
			}
		}
		
		render msg
		
	}
	/**
	 * @Description 草稿箱箱删除
	 * @params
	 * @return
	 * @createTime 2012-10-25
	 * @author wt
	 */
	def jsonDraftBoxDelete(){
		def ids = params.deleteIds
		def idsArray = ids.split('_')
		def flag = true
		def msg = message(code: 'default.deleted.simple.message')
		idsArray.each {id->
			def insideNote =InsideNote.get(id)
			if (!insideNote) {
				//msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				return
			}
			try {
				insideNote.delete(flush: true)
				//msg << message(code: 'default.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
			}catch (DataIntegrityViolationException e) {
				//msg << message(code: 'default.not.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				flag = false
			}
			if(!flag){
				msg = message(code: 'default.not.simple.deleted.message')
			}
		}
		
		render msg
	}
	/**
	 *@Description 获取未读短信数
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-12-14 下午3:00:33
	 */
	def getUnReadCount(){
		int result=0
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		params.userId=user?.id
		params.status=[1,3]
		//获取当前登录的未读短信数
		result=insideNoteService.getUnReadInsideNoteCount(params)
		render result
	}	
	/**
	 *@Description 单条回复
	 *@param
	 *@return
	 *@Auther liuly
	 *@createTime 2013-06-28
	 */
	def changeStats(){
		
		def flag = true
		def msg = message(code: 'default.read.simple.message')
		
		def insideNote =InsideNote.get(params.id)
		if (!insideNote) {
			return
		}
		try {
			if(insideNote.status==1){
				insideNote.status=2
				insideNote.save(flush: true)
			}else if(insideNote.status==3){
				insideNote.status=4
				insideNote.save(flush: true)
			}
	
		}catch (DataIntegrityViolationException e) {
			flag = false
		}
		if(!flag){
			msg = message(code: 'default.not.simple.read.message')
		}
		
	render msg
	}
	/**
	 *@Description 单条删除
	 *@param
	 *@return
	 *@Auther liuly
	 *@createTime 2013-06-28
	 */
	def deleteSingle(){
		def flag = true
		def msg = message(code: 'default.deleted.simple.message')
		
		def insideNote =InsideNote.get(params.id)
		if (!insideNote) {
			return
		}
		try {
			if(insideNote.status==1||insideNote.status==2){
				insideNote.status=5
			}else if(insideNote.status==3||insideNote.status==4){
			insideNote.delete(flush: true)
			}
		}catch (DataIntegrityViolationException e) {
			flag = false
		}
		if(!flag){
			msg = message(code: 'default.not.simple.deleted.message')
		}

		render msg
	}
}
