package filter;

import utils.Config;

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
        String userName = (String) request.getSession().getAttribute("user");
        String roleName = (String) request.getSession().getAttribute("role");

        String command = request.getParameter("command");
        String uri = request.getRequestURI();
        if (command == null) {
            switch (uri) {
                case "/":
                    setAccountPageByRole(request,userName,roleName,Config.ERROR);
                    break;
                case "/login":
                    setAccountPageByRole(request,userName,roleName,Config.LOGIN);
                    break;
                case "/registration":
                    setAccountPageByRole(request,userName,roleName,Config.REGISTRATION);
                    break;
                default:
                    request.setAttribute("redirect", Config.getProperty(Config.MAIN));
                    break;
            }
        }
        if (uri.endsWith(".jsp")) {
            DispatcherType dt = request.getDispatcherType();
            if (dt == DispatcherType.FORWARD || dt == DispatcherType.INCLUDE)
                //handle dispatcher results
                filterChain.doFilter(request, servletResponse);
            else
                ((HttpServletResponse)servletResponse).sendError(404, "Direct access to JSP");
        } else {
            //let's struts handle the request
            filterChain.doFilter(request, servletResponse);
        }
    }

    private void setAccountPageByRole(HttpServletRequest request, String userName, String roleName, String page){
        if (userName.isEmpty()) {
            request.setAttribute("redirect", Config.getProperty(page));
        } else {
            if ("Client".equals(roleName)) {
                request.setAttribute("redirect", Config.getProperty(Config.EDIT_ORDER));
            } else if ("Driver".equals(roleName)) {
                request.setAttribute("redirect", Config.getProperty(Config.ORDERS));
            } else if ("Admin".equals(roleName)) {
                request.setAttribute("redirect", Config.getProperty(Config.ADMIN));
            }
        }
    }

    @Override
    public void destroy() {

    }

}
