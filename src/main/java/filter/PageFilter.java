package filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Dmitry Tochilin
 */
public class PageFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Enter");
        request.setAttribute("page", "I am Filter");

        chain.doFilter(request, response);
        System.out.println("Filter End");
    }

    @Override
    public void destroy() {
        System.out.println("filter destroy");
    }

}
