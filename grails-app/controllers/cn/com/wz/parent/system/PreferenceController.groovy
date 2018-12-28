

package cn.com.wz.parent.system


import org.springframework.dao.DataIntegrityViolationException

import cn.com.wz.parent.ConstantsUtil;
import cn.com.wz.parent.base.BaseController;
import cn.com.wz.parent.system.Preference;
import cn.com.wz.parent.system.user.User;
class PreferenceController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]	
	
	def preferenceService

	/**
	 * @description 跳转到邮箱设置页面
	 * @return
	 */
	def setEmail(){
		def u=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def preference=servletContext.getAttribute("email_${u.id}")
		if(preference){
			def allValue=preference?.preValue
			def m=allValue.split(":")
			def emailName=m[0]
			def n=emailName.split("@")
			def userName=n[0]
			def password=m[1]
			render(view:"/cn/com/wz/parent/system/preference/setEmail",model:[userName:userName,password:password])
		}else{
            render(view:"/cn/com/wz/parent/system/preference/setEmail",model:[userName:u.userName])
        }
		
	}
	
	/**
	 * @description 保存邮箱信息
	 * @return
	 */
	def saveEmail(){
		def u=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def flag = preferenceService.saveEmail(u,params,servletContext)
		render flag
	}

    /**
     * @Descritpion 以json的形式保存preference
     * @param params中需要传入code、preName、preValue、preDesc这四个参数必须传递，并且变量名和domain中的一致
     * @return
     * @create huxx 2013-06-22
     */
    def jsonSave(){
        def u=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def flag=preferenceService.save(u,params,servletContext)
        render flag
    }
}
