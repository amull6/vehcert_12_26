package parent.security;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import cn.com.wz.parent.DateUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
/**
 * @Description 在线人数统计监听器
 * @Author huxx
 * @CreateTime 2013-03-29
 */
public class OnlineUserBindingListener implements HttpSessionBindingListener {
    private String userName="";
    private String appCode="";
    /**
     * @Description 类构造函数
     * @param username 登录人的登陆账号
     * @Author huxx
     * @CreateTime 2013-03-29
     */
    public OnlineUserBindingListener(String userName,String appCode){
        this.userName=userName;
        this.appCode=appCode;
    }

    /**
     * @Description 将登陆用户的登陆名放入onlineUserList中
     * @param event
     * @Author huxx
     * @CreateTime 2013-03-29
     */
    public void valueBound(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();

        // 把用户名放入在线列表
        List onlineUserList = (List) application.getAttribute("onlineUserList");
        // 第一次使用前，需要初始化
        if (onlineUserList == null) {
            onlineUserList = new ArrayList();
            application.setAttribute("onlineUserList", onlineUserList);
        }
        onlineUserList.add(this.userName+":_:"+session.getId()+":_:"+this.appCode+":_:"+DateUtil.getCurrentTime());
    }

    /**
     * @Description 将登出用户的登录名从onlineUserList中删除
     * @param event
     * @Author huxx
     * @CreateTime 2013-03-29
     */
    public void valueUnbound(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();
        // 从在线列表中删除用户名
        List<String> onlineUserList = (List<String>) application.getAttribute("onlineUserList");
        //将用户从session变量中删除
        String sessionFlag=this.userName+":_:"+session.getId()+":_:"+this.appCode;
        for(int i=0;i<onlineUserList.size();i++){
            String[] t=onlineUserList.get(i).toString().split(":_:");
            if((t[0]+":_:"+t[1]+":_:"+t[2]).equals(sessionFlag)){
                onlineUserList.remove(i);
            }
        }

        // 把过期session从已下线用户列表中删除
        List<String> oldSessionlist = (List<String>) application.getAttribute("oldSessionList");
        String oldSession=session.getId();
        if(oldSessionlist!=null&&oldSessionlist.size()>0){
            for(int i=0;i<oldSessionlist.size();i++){
                String t=oldSessionlist.get(i).toString();
                if(t.equals(oldSession)){
                    oldSessionlist.remove(i);
                    break;
                }
            }
        }
        System.out.println(this.userName + "退出。");
    }
}
