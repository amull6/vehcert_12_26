package cn.com.wz.parent.system.user
import grails.converters.JSON;
import cn.com.wz.parent.ConstantsUtil;
import cn.com.wz.parent.base.BaseController;


class ChangePasswordController extends BaseController{
/**
 * @description 鎸囧悜淇敼瀵嗙爜椤甸潰
 * @return
 * @author wt
 * @creatTime 2012-11-14涓婂崍10锛�4锛�0
 */
    def index() {
		render(view:"/cn/com/wz/parent/system/user/changePassword")
	}
	/**
	 * @description 淇敼骞朵繚瀛樺瘑鐮�	 * @return
	 * @author wt
	 * @creatTime 2012-11-14涓婂崍10锛�4锛�0
	 */
	def save(){
		def u=session.getAttribute(ConstantsUtil.LOGIN_USER)
		User user=User.get(u.id)
		def suc=false
		def msg = message(code: 'default.change.simple.message',default:'Password changed success')
		if(user.password!=params.password.encodeAsPassword()){
			msg=message(code:'default.wrong.password.message',default:'The password you entered is incorrect')
		}else{//鏇存柊瀵嗙爜
			user.password=params.newPassword.encodeAsPassword()
			if(!user.save(flush:true)){
				msg=message(code:'default.faild.message',default:'Faild to save')
				
			}else{
                //密码修改成功后，修改session中的password
                def password= params.newPassword.encodeAsBase64()
                session.setAttribute("password",password)
				session.setAttribute(ConstantsUtil.LOGIN_USER,user)
				suc=true
			}
			
		}
		def m=[:]
		m.msg=msg
		m.password=params.newPassword.encodeAsBase64()
		m.suc=suc
		render m as JSON
	}
}
