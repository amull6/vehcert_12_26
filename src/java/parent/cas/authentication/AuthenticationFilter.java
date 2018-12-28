/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/index.html
 */
package parent.cas.authentication;

import parent.cas.util.AbstractCasFilter;
import parent.cas.util.CommonUtils;
import parent.cas.validation.Assertion;
import cn.com.wz.parent.CustomConfigUtil;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Filter implementation to intercept all requests and attempt to authenticate
 * the user by redirecting them to CAS (unless the user has a ticket).
 * <p>
 * This filter allows you to specify the following parameters (at either the context-level or the filter-level):
 * <ul>
 * <li><code>casServerLoginUrl</code> - the url to log into CAS, i.e. https://cas.rutgers.edu/login</li>
 * <li><code>renew</code> - true/false on whether to use renew or not.</li>
 * <li><code>gateway</code> - true/false on whether to use gateway or not.</li>
 * </ul>
 *
 * <p>Please see AbstractCasFilter for additional properties.</p>
 *
 * @author Scott Battaglia
 * @version $Revision: 11768 $ $Date: 2007-02-07 15:44:16 -0500 (Wed, 07 Feb 2007) $
 * @since 3.0
 */
public class AuthenticationFilter extends AbstractCasFilter {

    public static final String CONST_CAS_GATEWAY = "_const_cas_gateway_";

    /**
     * The URL to the CAS Server login.
     */
    private String casServerLoginUrl;

    /**
     * Whether to send the renew request or not.
     */
    private boolean renew = false;

    /**
     * Whether to send the gateway request or not.
     */
    private boolean gateway = false;

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        super.initInternal(filterConfig);
        setCasServerLoginUrl(getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null));
        setRenew(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
        setGateway(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
    }

    /**
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     * @Update 2013-09-27 huxx 添加了对cas服务器内外网地址的支持
     * @Update 2013-09-29 huxx 添加了对注销路径的过滤
     */
    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 通过检查session中的变量，过虑请求
        HttpSession session = request.getSession();
        //获取当前执行路径
        String requesturi = request.getRequestURI();
        //处理requesturi，去掉jsessionid
        int index=requesturi.indexOf(";") ;
        if(index>0){
            requesturi=requesturi.substring(0,index);
        }

        //从配置文件中取出不需要登录验证的uri申请
        String noNeedLoginUrl= CustomConfigUtil.getNoNeedLoginUri(session.getServletContext());
        String[] urlList=noNeedLoginUrl.split("_");
        //判断当前申请是否需要登录验证
        boolean needLogin=true;
        if(!requesturi.equals(request.getContextPath()+"/")){
            if(urlList!=null&&urlList.length>0){
                for(int i=0;i<urlList.length;i++){
                    if(requesturi.equals(request.getContextPath() + urlList[i])){
                        needLogin=false;
                        break;
                    }
                }
            }
        }

        if(needLogin){
            final String ticket = request.getParameter(getArtifactParameterName());
            final Assertion assertion = session != null ? (Assertion) session
                    .getAttribute(CONST_CAS_ASSERTION) : null;
            final boolean wasGatewayed = session != null
                    && session.getAttribute(CONST_CAS_GATEWAY) != null;

            //判断是否为注销的url 参数中带有isToLogout=1参数的url均认为是系统注销的url
            String isToLogout=request.getParameter("isToLogout");

            if (CommonUtils.isBlank(ticket) && assertion == null && !wasGatewayed && !("1".equals(isToLogout))) {
                log.debug("no ticket and no assertion found");
                if (this.gateway) {
                    log.debug("setting gateway attribute in session");
                    request.getSession(true).setAttribute(CONST_CAS_GATEWAY, "yes");
                }

                final String serviceUrl = constructServiceUrl(request, response,"auth");

                //从配置文件中取出cas服务器的登陆地址
                Map<String,String> config= CustomConfigUtil.getCasInfo(request.getServletContext(), request);
                this.casServerLoginUrl=config.get("authServerUrl").toString();

                final String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), serviceUrl, this.renew, this.gateway);

                if (log.isDebugEnabled()) {
                    log.debug("redirecting to \"" + urlToRedirectTo + "\"");
                }

                response.sendRedirect(urlToRedirectTo);
                return;
            }

            if (session != null) {
                log.debug("removing gateway attribute from session");
                session.setAttribute(CONST_CAS_GATEWAY, null);
            }
        }

        filterChain.doFilter(request, response);
    }

    public final void setRenew(final boolean renew) {
        this.renew = renew;
    }

    public final void setGateway(final boolean gateway) {
        this.gateway = gateway;
    }

    public final void setCasServerLoginUrl(final String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }
}
