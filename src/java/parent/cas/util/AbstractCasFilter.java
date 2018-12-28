package parent.cas.util;

import cn.com.wz.parent.CustomConfigUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *  Abstract filter that contains code that is common to all CAS filters.
 *  <p>
 * The following filter options can be configured (either at the context-level or filter-level).
 * <ul>
 * <li><code>serverName</code> - the name of the CAS server, in the format: localhost:8080 or localhost:8443 or localhost</li>
 * <li><code>service</code> - the completely qualified service url, i.e. https://localhost/cas-client/app</li>
 * </ul>
 * <p>Please note that one of the two above parameters must be set.</p>
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.1
 */
public abstract class AbstractCasFilter extends AbstractConfigurationFilter {

    /** Represents the constant for where the assertion will be located in memory. */
    public static final String CONST_CAS_ASSERTION = "_const_cas_assertion_";

    /** Instance of commons logging for logging purposes. */
    protected final Log log = LogFactory.getLog(getClass());

    /** Defines the parameter to look for for the artifact. */
    private String artifactParameterName = "ticket";

    /** Defines the parameter to look for for the service. */
    private String serviceParameterName = "service";
    
    /** Sets where response.encodeUrl should be called on service urls when constructed. */
    private boolean encodeServiceUrl = true;

    /**
     * The name of the server.  Should be in the following format: {protocol}:{hostName}:{port}.
     * Standard ports can be excluded. */
    private String serverName;

    /** The exact url of the service. */
    private String service;

    public final void init(final FilterConfig filterConfig) throws ServletException {
        setServerName(getPropertyFromInitParams(filterConfig, "serverName", null));
        setService(getPropertyFromInitParams(filterConfig, "service", null));
        setArtifactParameterName(getPropertyFromInitParams(filterConfig, "artifactParameterName", "ticket"));
        setServiceParameterName(getPropertyFromInitParams(filterConfig, "serviceParameterName", "service"));
        setEncodeServiceUrl(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "encodeServiceUrl", "true")));

        initInternal(filterConfig);
        init();
    }

    /** Controls the ordering of filter initialization and checking by defining a method that runs before the init. */
    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        // template method
    }

    /**
     * Initialization method.  Called by Filter's init method or by Spring.
     */
    public void init() {
        CommonUtils.assertNotNull(this.artifactParameterName, "artifactParameterName cannot be null.");
        CommonUtils.assertNotNull(this.serviceParameterName, "serviceParameterName cannot be null.");
        CommonUtils.assertTrue(CommonUtils.isNotEmpty(this.serverName) || CommonUtils.isNotEmpty(this.service), "serverName or service must be set.");
    }

    public final void destroy() {
        // nothing to do
    }

    /**
     * @Description 构建本地url路径
     * @param request
     * @param response
     * @param type 调用类型 =validation表示验证filter调用；=auth表示登陆filter调用
     * @return
     *
     * @Update 2013-09-27 huxx 添加了对内外网的地址支持
     */
    protected final String constructServiceUrl(final HttpServletRequest request, final HttpServletResponse response,final String type) {
        //从配置文件中取出cas服务器的登陆地址
        Map<String,String> config= CustomConfigUtil.getCasInfo(request.getServletContext(), request);
        if("auth".equals(type)){
            this.serverName=config.get("localAuthServerName").toString();
            this.service=config.get("localAuthServiceUrl").toString();
        }else if("validation".equals(type)){
            this.serverName=config.get("localValidationServerName").toString();
            this.service=config.get("localValidationServiceUrl").toString();
        }

        return CommonUtils.constructServiceUrl(request, response, this.service, this.serverName, this.artifactParameterName, this.encodeServiceUrl);
    }

    public final void setServerName(final String serverName) {
        this.serverName = serverName;
    }

    public final void setService(final String service) {
        this.service = service;
    }

    public final void setArtifactParameterName(final String artifactParameterName) {
        this.artifactParameterName = artifactParameterName;
    }

    public final void setServiceParameterName(final String serviceParameterName) {
        this.serviceParameterName = serviceParameterName;
    }
    
    public final void setEncodeServiceUrl(final boolean encodeServiceUrl) {
    	this.encodeServiceUrl = encodeServiceUrl;
    }

    public final String getArtifactParameterName() {
        return this.artifactParameterName;
    }

    public final String getServiceParameterName() {
        return this.serviceParameterName;
    }
}
