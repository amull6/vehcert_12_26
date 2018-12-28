package cn.com.wz.parent.login

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import parent.cas.authentication.AttributePrincipal
import parent.cas.authentication.AuthenticationFilter
import parent.cas.validation.AssertionImpl
import parent.security.OnlineUserBindingListener

import javax.servlet.ServletContext

/**
 * @Description 系统登陆管理
 * @author huxx
 * @Update 2013-09-27 添加sso处理
 */
class LoginController extends BaseController{

    def logService
    def authService
    /**
     * @Description 系统登陆判定
     * @creater huxx
     * @return
     * @Update 2013-09-26 huxx cas登陆时，返回不同的错误提示页面
     */
    def login(){
        def flag=true
        def user= session."${ConstantsUtil.LOGIN_USER}"
        if(!user||(params.userName!=null&&user.userName!=params.userName)){
            flag=false
        }
        if ("1".equals(params.isToLogout)){
           return render(view:'/caserror')
        }

        if(!flag){
            //判断是否为sso登陆
            def isSSO=grailsApplication.config.isSSO
            if ("1".equals(isSSO)){
                if (params.isCasLogin){ //sso登陆后，验证不通过
                    render(view:'/caserror')
                }else{//当session不存在时，如果没有登陆过并且是sso登陆方式，执行doLogin方法
                    doLogin()
                }
            } else{
                render(view:'/login')
            }
        }else{
            def appCode=grailsApplication.config.app.appCode
            render (view:"/index4",model: [appCode:appCode])
        }
    }
    /**
     * 执行登陆操作
     * @return
     * @update huxx 2012-09-13 修改了错误信息的国际化，并使登录信息错误后，保留了登录名信息
     * @update huxx 2013-02-18 添加了对用户启用状态以及类型有效时间的判断
     * @update huxx 2013-09-26 添加了cas登陆处理
     * @Update zhuwei 2017-05-08 在1.25服务上验证宋华、冯文静的登陆
     */
    def doLogin(){

        def isCasLogin=true
        AssertionImpl assertionImpl=(AssertionImpl)session.getAttribute(AuthenticationFilter.CONST_CAS_ASSERTION);

        String userName=''
        if(assertionImpl!=null){
            AttributePrincipal principal=assertionImpl.getPrincipal();
            userName = principal.getName();
        }
        if (!userName){
            isCasLogin=false
            userName=params.userName?.toString()
        }


        //先判断内容是否为空
        if(!userName){
            flash.message = message(code:'default.loginPage.userNameTip.label')
        }else{
            def user = User.findByUserName("${userName}")

            flash.userName=userName
            if(user){
                //当不是cas登陆时需要判断密码是否正确，当是cas登陆时不需要判断密码是否正确
                if( isCasLogin || (!isCasLogin&&params.password?.encodeAsPassword().equals(user.password))){
                    //判断登陆用户是否有应用系统的使用权限
                    def appCode=grailsApplication.config.app.appCode
                    //判断用户是否启用（system除外）
                    if('system'.equals(user.userName)){
                        saveLoginInfo(user,user.password,appCode)
                    }
                    //验证登陆账号是不是hua.song
                    else if('hua.song'.equals(user.userName)||'wenjing.feng'.equals(user.userName)){
                        userName='canNotLogin'
                        flash.message='合格证数据管理请登陆：192.168.1.91'
                    }
                    else{
                        ServletContext application = session.getServletContext()
                        List onlineUserList = (List) application.getAttribute("onlineUserList")
                        def canLogin=true
                        if(onlineUserList){
                            onlineUserList?.each {u->
                                def userInfo=u.split(':_:')
                                if(userInfo[1]==params.sessionId){
                                    canLogin=false
                                }
                            }
                        }
                        if(canLogin==true){
                            def roleList=authService.selectRolesByParams(['userName':"${userName}",appCode: "${appCode}"])
                            if (roleList){
                                if(user.status){
                                    //判断登陆用户类型，如果是临时用户需要判断是否在有效登陆时间
                                    if('tempUser'.equals(user.userType?.code)){
                                        //获取当前时间
                                        def now=new Date().format("yyyy-MM-dd HH:mm:ss")
                                        if(now>=user.validStartTime && now<=user.validEndTime){
                                            saveLoginInfo(user,user.password,appCode)
                                        }else{
                                            flash.message = message(code:'default.user.userType.false.message')
                                        }
                                    }else{
                                        saveLoginInfo(user,user.password,appCode)
                                    }

                                }else{
                                    flash.message = message(code:'default.user.status.false.message')
                                }
                            }else{
                                flash.message=message(code: 'default.auth.noLoginAuth',default:'No Auth')
                            }
                        }else{
                            userName='canNotLogin'
                            flash.message='浏览器已登录一个账户，请用其他浏览器登录！'
                        }
                    }

                }else{
                    flash.message = message(code:'default.loginPage.passwordError.label')
                }
            }else{
                flash.message=message(code:'default.loginPage.noUserNameTip.label',args:["$userName"])
            }
        }
        redirect(action:'login',params:[userName: userName,isCasLogin:isCasLogin])

    }
    /**
     *@Description 保存登陆信息
     *@param
     *@return
     *@Auther huxx
     *@createTime 2013-2-18 上午11:20:33
     */
    def saveLoginInfo(user,password,appCode){
        //将用户密码存入session中，工作流中使用
//        password= password.encodeAsBase64()
        session.setAttribute("password",password)
        //将用户数据写入到session中
        session.setAttribute(ConstantsUtil.LOGIN_USER,user)

        //设置应用程序标识
        session.setAttribute('appCode',appCode)

        //用户登陆客户端唯一性处理
        ServletContext application = session.getServletContext()
        List onlineUserList = (List) application.getAttribute("onlineUserList")
        onlineUserList?.each {

            def userInfo=it.split (':_:')
            if(userInfo[0]&&userInfo[0].equals(user.userName)){
                //保存已过期的session的id信息
                List<String> oldSessionList=(List<String>) application.getAttribute("oldSessionList");
                if(oldSessionList==null){
                    oldSessionList=new ArrayList<String>();
                    application.setAttribute("oldSessionList",oldSessionList);
                    oldSessionList.add(userInfo[1]);
                }else if(oldSessionList.contains(userInfo[1])){
                }else{
                    oldSessionList.add(userInfo[1]);
                }
            }
        }

        //写入在线统计信息中
        session.setAttribute("onlineUserBindingListener", new OnlineUserBindingListener(user.userName,appCode))

        //写入日志
        logService.insertLog(user.id, "login", "登录系统",'systemLog')

        //将用户的设置数据写入到session中
        //	preference2Session()
    }

    /**
     * @Description 登出系统
     * @return
     * @update huxx 2012-11-05 在登出系统前保存用户本次的登录时间
     */
    def doLogout(){
        //保存最后登录时间
        def user=getCurrentUser()
        if(user){
            def u=User.get(user.id)
            //修改用户的最后登录时间
            u.lastLoginTime=DateUtil.getCurrentTime()
            u.save(flush:true)

            //写入日志
            logService.insertLog(user.id, "login", "登出系统",'systemLog')
        }

        if(session){
            session.invalidate()
        }
        render "success"
    }

}
