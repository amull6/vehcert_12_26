package cn.com.wz.parent

import cn.com.wz.parent.system.Preference
import cn.com.wz.parent.system.user.User;
import cn.com.wz.parent.system.user.UserDetail;

import org.springframework.web.servlet.support.RequestContextUtils as RCU
class UtilTagLib {
	static namespace="c"
	/**
	 * 获取设置信息
	 * 暂时还没有加如用户登陆的信息
	 */
	def preference = {attrs,body->
		def name = attrs.name
		if(!name){
			out << ''
		}else{
			def value = session."VehCert"?session."VehCert":servletContext."VehCert"
			out << value
		}
	}
	def isChinese = {attrs,body->
		def page_lang = RCU.getLocale(request)
		if(page_lang.toString().equals("zh_CN")){
			out << body()
		}
	}
	def isEnglish = {attrs,body->
		def page_lang = RCU.getLocale(request)
		if(page_lang.toString().equals("en_US")){
			out << body()
		}
	}
	/**
	 * 获取当前登录用户的realName
	 */
	def currentUser = {attrs,body->
		out<< getUserName([userId:session."${ConstantsUtil.LOGIN_USER}".id])
	}
	
	/**
	 * 根据传入的用户id获取用户名称
	 */
	def getUserName={attrs,body->
		def userId=attrs.userId
		def user=User.get(userId)
		def realName='未知'
		if(user?.userDetail){
			realName=user.userDetail?.realName
		}
		if(user){
			out << realName
		}else{
			out << '用户已被删除！'
		}
	}
	/**
	 * 获取登录用户的岗位和部门信息
	 */
	def currentUserInfo={
		
		def user=User.get(session."${ConstantsUtil.LOGIN_USER}".id)
		def organs=changeListToStr(user.organs)
		def posts=changeListToStr(user.posts)
		
		if(!organs){
			organs='未知部门'
		}
		if(!posts){
			posts='未知岗位'
		}
		out<<organs+' '+posts
	}
	/**
	 *@Description 将list转化为字符串
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-12-27 上午11:39:40
	 */
	def changeListToStr(lst){
		def result=''
		lst?.each {l->
			result+=l.toString()+';'
		}
		if(result){
			result=result.substring(0,result.lastIndexOf(';'))
		}
	}
}
