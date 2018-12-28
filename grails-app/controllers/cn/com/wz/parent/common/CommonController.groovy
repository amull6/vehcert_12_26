package cn.com.wz.parent.common
import cn.com.wz.parent.ConstantsUtil;
import cn.com.wz.parent.base.BaseController;
import cn.com.wz.parent.system.Preference;
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.user.User;
/**
 * @Description 一些公用controller
 * @Author huxx
 * @CreateTime 2013-04-26
 */
class CommonController extends BaseController {
	
	//事件触发消息提醒模板，暂用，以后可删。王涛
	def remindModel(){
		def msgCon=''
		def smsCon=''
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def loginUser=User.get(user.id)
		
		//获取用户系统消息提醒配置信息
		def msg=Preference.findByUserAndCode(loginUser,'msgCon')
		if(msg){
			msgCon=msg.preValue
		}else{
			msgCon=servletContext.getAttribute('defaultMsgCon')
		}
		//判断本事件是否提醒
		def msgIds=msgCon?.split'_'
		msgIds?.each{
			if(it=='repleyed'){
				def message=[:]
				message.createrId=loginUser.id
				message.code=it
				message.receiverId=''
			}
		}
		
		//判断用户是否有接收手机短信权限
		loginUser?.roles?.each{
			if(it.roleCode=='smsReceiver'){
				//获取用户手机短信提醒配置信息
				def sms=Preference.findByUserAndCode(loginUser,'smsCon')
				if(sms){
					smsCon=sms.preValue
				}else{
					smsCon=servletContext.getAttribute('defaultSmsCon')
				}
			}
		}
		
	}

    /**
     * @Description 当session失效后，ajax申请将执行的controller，可以通过在js中判断返回的值是否与此action返回的值相等，
     * 判断是否session已过时，然后跳转到login页面
     * @Author huxx
     * @CreateTime 2013-04-26
     */
    def ajax(){
        render "session is loginout"
    }


    def getImg() throws IOException {
//        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg"); // 设置图片格式格式，这里不可以忽略
        FileInputStream fis = new FileInputStream(params.picPath);
        OutputStream os = response.getOutputStream();
        try {
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1)
                os.write(buffer, 0, count);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                os.close();
            if (fis != null)
                fis.close();
        }
        return null;
    }
}
