package listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * @author Dmitry Tochilin
 */
//todo ...change sout with LOGGER
public class ServletListener implements HttpSessionAttributeListener {

    private String counterName = "counter";

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String name = event.getName();
        String url = "URL";

        if (name.equals(counterName)) {
            Integer currentName = (Integer) event.getValue();
            System.out.println("add Counter=" + currentName);
        } else if (name.equals(url)) {
            String currentURL = (String) event.getValue();
            System.out.println("add URL");
        } else {
            System.out.println("add attribute");
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {        
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if(event.getName().equals(counterName)){
            System.out.println("changed counter=" + event.getValue());
        }
    }
}
