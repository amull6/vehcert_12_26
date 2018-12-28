package cn.com.wz.parent.system.log

import grails.converters.JSON

import java.text.SimpleDateFormat

import javax.servlet.ServletContext

import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User

/**
 *@Description 管理员对在线用户控制及发信息controller类
 *@Auther liucj
 *@createTime 2013-3-30 下午4:15:27
 */

class LogUserController extends BaseController{
    def organizationService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def index() {
        render(view:'/cn/com/wz/parent/system/log/logUser',params:params)
    }
    def sysControl(){
        render(view:'/cn/com/wz/parent/system/log/logSys',params:params)
    }
    /**
     * @Description 获取当前用户数显示在主页home上
     * @return
     */
    def userCount(){
        ServletContext application = session.getServletContext();
        //获取在线人列表
        List onlineUserList = (List) application.getAttribute("onlineUserList");
        List oldUserList = (List) application.getAttribute("oldSessionList");
        def result=onlineUserList.size()-((oldUserList?.size())?:0)
        render result
    }
    /**
     *@Description 获取当前用户列表
     *@param onlineUserList的元素的组成为：userName_sessionId_loginTime 例如：li.li_23123123_2012-05-30 12:00:00
     *@return
     *@Auther liucj
     *@createTime 2013-3-30 下午4:16:27
     */
    def jsonList(){
//        ServletContext application = session.getServletContext();
        //获取在线人列表
        List onlineUserList = (List) servletContext.getAttribute("onlineUserList");
        //获取双重登录或已被强制下线用户的sessionId列表
        List oldSessionList = (List) servletContext.getAttribute("oldSessionList");
        def userList=[]
        onlineUserList?.each{o->
            def oo=o.split (':_:')
            //将已经被强制下线的用户在在线用户中去掉
            if(oldSessionList){
                oldSessionList?.each{
                    if(it!=oo[1]){
                        userList.add(oo[0])
                    }
                }
            }else{
                userList.add(oo[0])
            }
        }
        def cel={
            if(params.realName){
                userDetail{
                    like('realName',"%${params.realName}%")
                }
            }
            if(params.organNames){
                organs{
                    like("organNameC", "%${params.organNames}%")
                }
            }
            inList("userName",userList)
        }
        //去掉重复项的在线用户列表
        def onlylst=User.createCriteria().list(cel)
        //获取当前时间
        Date date=new Date()
        def results=[]
        onlylst?.each {a->
            def m=[:]
            m. id = a.id
            m. userName = a.userName
            m. tempName = a.userDetail?.realName
            m. userTypeName=a.userType?.dicValueNameC
            m. startTime=''
            //获取用户的登录时间
            onlineUserList?.each{o->
                def oo=o.split ('_')
                if(oo[0]==a.userName){
                    m. startTime = oo[3]
                }
            }
            Date start=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(m. startTime)
            long live=date.getTime()-start.getTime()
            long hour=live/(60*60*1000)
            long min =live/(60*1000)-hour*60
            long sec =live/1000-hour*60*60-min*60
            def onlineTime=message(code:'onlineUser.onlineTime.lable',args:["$hour","$min","$sec"])
            m. onlineTime=onlineTime
            //---------------组装在线时间完成
            m. organNames=''
            a.organs?.each {
                m.organNames+=organizationService.getParentOrganNameC(it.id)+it.organNameC+"；"
            }
            if(m.organNames){
                m.organNames=m.organNames.substring(0,m.organNames.lastIndexOf("；"))
            }
            m. postNames=''
            a.posts?.each {
                m.postNames+=it.postNameC+"；"
            }
            if(m.postNames){
                m.postNames=m.postNames.substring(0,m.postNames.lastIndexOf("；"))
            }
            //对在线人员按登录时间进行排序
            if(results.size()>0){
                def count=results.size()
                for(int i=0;i<count;i++){
                    if(results[i].startTime<m.startTime){
                        results.add(i,m)
                        break
                    }else if(i==count-1){
                        results.add(m)
                    }
                }
            }else{
                results.add(m)
            }
        }
        def result = [rows: results, total: onlylst.size]
        render result as JSON
    }

    /**
     *@Description 将登录用户的sessionId加入到oldSessionList中，然后在过滤器中将其注销
     *@param userName:用户的userName
     *@return
     *@Auther liucj
     *@createTime 2013-5-31 上午9:16:27
     */
    def sessionout(){
        ServletContext application = session.getServletContext();
        //获取在线人列表
        List onlineUserList = (List) application.getAttribute("onlineUserList")
        onlineUserList?.each {

            def userInfo=it.split (':_:')
            if(userInfo[0]&&userInfo[0].equals(params.userName)){
                //保存要注销的用户session的id信息
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
        redirect(action:'sysControl',params:params)
    }

}
