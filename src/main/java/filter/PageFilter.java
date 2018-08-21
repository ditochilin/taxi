package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dmitry Tochilin
 */
public class PageFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().endsWith(".jsp")) {
            DispatcherType dt = request.getDispatcherType();
            if (dt == DispatcherType.FORWARD || dt == DispatcherType.INCLUDE)
                //handle dispatcher results
                filterChain.doFilter(request, response);
            else
                response.sendError(404, "Direct access to JSP");
        } else {
            //let's struts handle the request
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
