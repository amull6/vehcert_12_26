package cn.com.wz.parent.base

import org.springframework.web.servlet.support.RequestContextUtils as RCU

import cn.com.wz.parent.ConstantsUtil;
import cn.com.wz.parent.DateUtil;
import cn.com.wz.parent.system.user.User
import cn.com.wz.parent.system.Preference

/**
 * 基础公共控制器
 * @author haojia
 *
 */
class BaseController{
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	def getCurrentUser(){
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		return user
	}

	/**
	 * 判断是英文请求
	 */
	def isEnglish(){
		def lang = RCU.getLocale(request)
		lang.toString().equals('en_US')
	}
	/**
	 * 判断是中文请求
	 * @return
	 */
	def isChinese(){
		!isEnglish()
	}

	/**
	* 判断当前的请求是否是Ajax请求
	* @return
	*/
   def isAjaxRequest(){
	   request.getHeader("x-requested-with")?true:false
   }
}
