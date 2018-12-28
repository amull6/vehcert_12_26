/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/index.html
 */
package parent.cas.validation;

import cn.com.wz.parent.CustomConfigUtil;
import parent.cas.util.AbstractCasFilter;
import parent.cas.util.CommonUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The filter that handles all the work of validating ticket requests.
 * <p>
 * This filter can be configured with the following values:
 * <ul>
 * <li><code>redirectAfterValidation</code> - redirect the CAS client to the same URL without the ticket.</li>
 * <li><code>exceptionOnValidationFailure</code> - throw an exception if the validation fails.  Otherwise, continue
 *  processing.</li>
 * <li><code>useSession</code> - store any of the useful information in a session attribute.</li>
 * </ul>
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.1
 */
public abstract class AbstractTicketValidationFilter extends AbstractCasFilter {

    /** The TicketValidator we will use to validate tickets. */
    private TicketValidator ticketValidator;

    /**
     * Specify whether the filter should redirect the user agent after a
     * successful validation to remove the ticket parameter from the query
     * string.
     */
    private boolean redirectAfterValidation = false;

    /** Determines whether an exception is thrown when there is a ticket validation failure. */
    private boolean exceptionOnValidationFailure = true;

    private boolean useSession = true;

    /**
     * Template method to return the appropriate validator.
     *
     * @param filterConfig the FilterConfiguration that may be needed to construct a validator.
     * @return the ticket validator.
     */
    protected TicketValidator getTicketValidator(FilterConfig filterConfig) {
        return this.ticketValidator;
    }

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        super.initInternal(filterConfig);
        setExceptionOnValidationFailure(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "exceptionOnValidationFailure", "true")));
        setRedirectAfterValidation(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "redirectAfterValidation", "false")));
        setUseSession(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "useSession", "true")));
        setTicketValidator(getTicketValidator(filterConfig));
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.ticketValidator, "ticketValidator cannot be null.");
    }

    /**
     * Pre-process the request before the normal filter process starts.  This could be useful for pre-empting code.
     *
     * @param servletRequest The servlet request.
     * @param servletResponse The servlet response.
     * @param filterChain the filter chain.
     * @return true if processing should continue, false otherwise.
     * @throws java.io.IOException if there is an I/O problem
     * @throws javax.servlet.ServletException if there is a servlet problem.
     */
    protected boolean preFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        return true;
    }

    /**
     * Template method that gets executed if ticket validation succeeds.  Override if you want additional behavior to occur
     * if ticket validation succeeds.  This method is called after all ValidationFilter processing required for a successful authentication
     * occurs.
     *
     * @param request the HttpServletRequest.
     * @param response the HttpServletResponse.
     * @param assertion the successful Assertion from the server.
     */
    protected void onSuccessfulValidation(final HttpServletRequest request, final HttpServletResponse response, final Assertion assertion) {
        // nothing to do here.                                                                                            
    }

    /**
     * Template method that gets executed if validation fails.  This method is called right after the exception is caught from the ticket validator
     * but before any of the processing of the exception occurs.
     *
     * @param request the HttpServletRequest.
     * @param response the HttpServletResponse.
     */
    protected void onFailedValidation(final HttpServletRequest request, final HttpServletResponse response) {
        // nothing to do here.
    }

    /**
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     * @Update 2013-11-09 添加了不需登陆验证的url处理
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
        if(urlList!=null&&urlList.length>0){
            for(int i=0;i<urlList.length;i++){
                if(requesturi.equals(request.getContextPath()+"/login/doLogin")){
                    break;
                }
                if(requesturi.equals(request.getContextPath() + urlList[i])){
                    needLogin=false;
                    break;
                }
            }
        }
        if(needLogin){
            if (!preFilter(servletRequest, servletResponse, filterChain)) {
                return;
            }

            final String ticket = request.getParameter(getArtifactParameterName());

            if (CommonUtils.isNotBlank(ticket)) {
                if (log.isDebugEnabled()) {
                    log.debug("Attempting to validate ticket: " + ticket);
                }

                try {
                    final Assertion assertion = this.ticketValidator.validate(
                            ticket, constructServiceUrl(request,
                            response,"validation"),request);

                    if (log.isDebugEnabled()) {
                        log.debug("Successfully authenticated user: "
                                + assertion.getPrincipal().getName());
                    }

                    request.setAttribute(CONST_CAS_ASSERTION, assertion);

                    if (this.useSession) {
                        request.getSession().setAttribute(CONST_CAS_ASSERTION,
                                assertion);
                    }
                    onSuccessfulValidation(request, response, assertion);
                } catch (final TicketValidationException e) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    log.warn(e, e);

                    onFailedValidation(request, response);

                    if (this.exceptionOnValidationFailure) {
                        throw new ServletException(e);
                    }
                }

                if (this.redirectAfterValidation) {
                    response.sendRedirect(response
                            .encodeRedirectURL(constructServiceUrl(request, response,"validation")));
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);

    }

    public final void setTicketValidator(final TicketValidator ticketValidator) {
    this.ticketValidator = ticketValidator;
}

    public final void setRedirectAfterValidation(final boolean redirectAfterValidation) {
        this.redirectAfterValidation = redirectAfterValidation;
    }

    public final void setExceptionOnValidationFailure(final boolean exceptionOnValidationFailure) {
        this.exceptionOnValidationFailure = exceptionOnValidationFailure;
    }

    public final void setUseSession(final boolean useSession) {
        this.useSession = useSession;
    }
}
