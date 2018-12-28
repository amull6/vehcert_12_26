package parent.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.com.wz.parent.ConstantsUtil;
import cn.com.wz.parent.CustomConfigUtil;
import cn.com.wz.parent.StringUtil;

/**
 * 设置字符集、过滤未登录的非法请求
 */
public class LoginFilter implements Filter {
    protected String encoding = null;
    protected FilterConfig filterConfig = null;
    protected boolean ignore = false;
    protected String forwardPath = null;

    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }

    /**
     * @Description 系统登陆验证过滤方法
     * @param request
     * response
     * chain
     * @update huxx 2013-04-24   修改了session失效后，ajax申请的处理。（解决了在首页定时读取未读短信的数量时，当session失效时多处显示登陆信息的问题）
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 设置编码方式，web.xml里面有filter参数的初始化设置
        if (ignore || (request.getCharacterEncoding() == null)) {
            String encoding = selectEncoding(request);
            if (encoding != null)
                request.setCharacterEncoding(encoding);
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //获取当前执行路径
        String requesturi = httpServletRequest.getRequestURI();
        //判断是否是ajax申请
        String ajax=httpServletRequest.getHeader("x-requested-with");
        boolean  isAjax=false;
        if(ajax!=null&&ajax.length()>0){
            isAjax=true;
        }
        System.out.println("当前执行路径为:【"+requesturi+(isAjax?"】是ajax申请":"】"));

        // 通过检查session中的变量，过虑请求
        HttpSession session = httpServletRequest.getSession();

        Object currentUser = session.getAttribute(ConstantsUtil.LOGIN_USER.toString());

        //从配置文件中取出不需要登录验证的uri申请
        String noNeedLoginUrl= CustomConfigUtil.getNoNeedLoginUri(session.getServletContext());
        String[] urlList=noNeedLoginUrl.split("_");

        //处理requesturi，去掉jsessionid
        int index=requesturi.indexOf(";") ;
        if(index>0){
            requesturi=requesturi.substring(0,index);
        }
        //判断当前申请是否需要登录验证
        boolean needLogin=true;
        if(urlList!=null&&urlList.length>0){
            for(int i=0;i<urlList.length;i++){
                if(requesturi.equals(httpServletRequest.getContextPath() + urlList[i])){
                    needLogin=false;
                    break;
                }
            }
        }
        // 当前会话用户为空而且不是请求登录，退出登录，欢迎页面和根目录则退回到应用的根目录，如果是ajax申请就返回错误代码444
        if (currentUser == null&&needLogin ) {
            //修改了session失效后，ajax申请的处理。
            if(isAjax){
                //httpServletResponse.setStatus(404);
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/common/ajax?isToLogout=1");
            }else{
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/?isToLogout=1&errorMessag=请登陆系统！");
            }

            return;
        }

        //判断用户是否已经从其它地方登陆
        // 用户唯一等的处理方式是在登陆时判定用户是否已登陆过，如果登陆过就将登陆过的session存入到oldSessionList中，在过滤器中将过期的session注销
        ServletContext application = session.getServletContext();
        List<String> oldSessionList=(List<String>) application.getAttribute("oldSessionList");
        if(oldSessionList!=null&&oldSessionList.contains(session.getId())){
            session.invalidate();
            if(isAjax){
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/common/ajax?isToLogout=1");
            }else{
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/?isToLogout=1&errorMessag=用户已从其它客户端登陆！");
            }
            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * @Description 初始化过滤器
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
        this.forwardPath = filterConfig.getInitParameter("forwardpath");
        String value = filterConfig.getInitParameter("ignore");
        if (value == null)
            this.ignore = true;
        else if (value.equalsIgnoreCase("true"))
            this.ignore = true;
        else if (value.equalsIgnoreCase("yes"))
            this.ignore = true;
        else
            this.ignore = false;
    }

    protected String selectEncoding(ServletRequest request) {
        return (this.encoding);
    }
}