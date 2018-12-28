package cn.com.wz.parent

import org.springframework.web.context.support.WebApplicationContextUtils

/**
 * @Description 系统配置公共方法类
 * @Author: huxx
 * @createTime: 2013-5-28 上午11:25
 */
class CustomConfigUtil {
    /**
     * @Description 从配置中获取cas信息
     * @param servletContext ，request 可以为null，如果为null就取内部地址
     * @return
     * @create huxx 2013-09-28
     */
    static Map<String,String> getCasInfo(def servletContext,def request){
        def result=[:]
        def appCtx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

        result.appCode=appCtx.grailsApplication.config.app.appCode    //应用系统编码
        result.appInUrl=appCtx.grailsApplication.config.app.inUrl //系统内部跟地址
        result.appOutUrl=appCtx.grailsApplication.config.app.outUrl//系统外部地址
        result.isSSO=appCtx.grailsApplication.config.isSSO //是否为sso登陆 1是；0否

        //判断esso请求是从外网访问还是从内网访问
        String authServerUrl=""
        String localAuthServiceUrl=""
        String localAuthServerName=""

        String validationServerUrl=""
        String localValidationServiceUrl=""
        String localValidationServerName=""
        String logoutUrl=""
        if (request){
            def url=request.getRequestURL()
            def outAPPRootUrl=appCtx.grailsApplication.config.app.outUrl

            if (url.toString().toUpperCase().indexOf(outAPPRootUrl.toString().toUpperCase())>=0){

                authServerUrl=appCtx.grailsApplication.config.cas.authServerUrl.outUrl
                localAuthServiceUrl=appCtx.grailsApplication.config.cas.localAuthServiceUrl.outUrl
                localAuthServerName= appCtx.grailsApplication.config.cas.localAuthServerName.outUrl

                validationServerUrl=appCtx.grailsApplication.config.cas.validationServerUrl.outUrl
                localValidationServiceUrl=appCtx.grailsApplication.config.cas.localValidationServiceUrl.outUrl
                localValidationServerName= appCtx.grailsApplication.config.cas.localValidationServerName.outUrl
                logoutUrl=appCtx.grailsApplication.config.logoutUrl.outUrl
            }else{

                authServerUrl=appCtx.grailsApplication.config.cas.authServerUrl.inUrl
                localAuthServiceUrl=appCtx.grailsApplication.config.cas.localAuthServiceUrl.inUrl
                localAuthServerName= appCtx.grailsApplication.config.cas.localAuthServerName.inUrl

                validationServerUrl=appCtx.grailsApplication.config.cas.validationServerUrl.inUrl
                localValidationServiceUrl=appCtx.grailsApplication.config.cas.localValidationServiceUrl.inUrl
                localValidationServerName= appCtx.grailsApplication.config.cas.localValidationServerName.inUrl

                logoutUrl=appCtx.grailsApplication.config.logoutUrl.inUrl
            }
        }else{
            authServerUrl=appCtx.grailsApplication.config.cas.authServerUrl.inUrl
            localAuthServiceUrl=appCtx.grailsApplication.config.cas.localAuthServiceUrl.inUrl
            localAuthServerName= appCtx.grailsApplication.config.cas.localAuthServerName.inUrl

            validationServerUrl=appCtx.grailsApplication.config.cas.validationServerUrl.inUrl
            localValidationServiceUrl=appCtx.grailsApplication.config.cas.localValidationServiceUrl.inUrl
            localValidationServerName= appCtx.grailsApplication.config.cas.localValidationServerName.inUrl

            logoutUrl=appCtx.grailsApplication.config.logoutUrl.inUrl
        }
        result.authServerUrl=authServerUrl?authServerUrl:''
        result.localAuthServiceUrl=localAuthServiceUrl?localAuthServiceUrl:''
        result.localAuthServerName= localAuthServerName?localAuthServerName:''
        result.logoutUrl=logoutUrl?logoutUrl:""

        result.validationServerUrl=validationServerUrl?validationServerUrl:''
        result.localValidationServiceUrl=localValidationServiceUrl?localValidationServiceUrl:''
        result.localValidationServerName= localValidationServerName?localValidationServerName:''
        return result
    }
    /**
     * @Description 从配置文件中获取不需验证的uri信息
     * @param servletContext
     * @return
     */
    static String getNoNeedLoginUri(servletContext){
        def appCtx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        def noNeedLoginUri=appCtx.grailsApplication.config.security.noNeedLoginUrl
        return noNeedLoginUri
    }
}
