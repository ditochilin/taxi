package command.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Customer tag.
 * Sends to jsp URI of requested view.
 *
 * @author Dmitry Tochilin
 */
public class RequestedViewTag extends SimpleTagSupport {

    @Override
    public void doTag() throws IOException {
        HttpServletRequest request = getRequest();
        sendViewUriToJsp(request);
    }

    private HttpServletRequest getRequest() {
        PageContext pageContext = (PageContext) getJspContext();
        return (HttpServletRequest) pageContext.getRequest();
    }

    private void sendViewUriToJsp(HttpServletRequest request)
            throws IOException {
        JspWriter out = getJspContext().getOut();
        out.print(request.getRequestURI());
    }
}