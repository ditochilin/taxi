package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Dmitry Tochilin
 */
//todo make exceptions handler  !!!
public class ServletInitializedListener implements ServletRequestListener {

    private static int counter;

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        System.out.println("Destroy");
    }

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        ServletContext context = event.getServletContext();
        ServletRequest request = event.getServletRequest();
        //todo ???  why synchronized?
        synchronized (context) {
            String name = ((HttpServletRequest) request).getRequestURI();
            System.out.println("Request= " + name + "  Counter=" + ++counter);
            context.log("Request= " + name + "  Counter=" + counter);
        }
    }
}
