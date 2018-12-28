package parent;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description  使用gzip算法对网页内容进行压缩，然后传给浏览器，以减少数据传输量、提高响应速度
 * @Create: 13-12-20 下午4:41  huxx
 */
public class GZipFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
