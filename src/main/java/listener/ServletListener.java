package listener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * @author Dmitry Tochilin
 */
//todo ...change sout with LOGGER
public class ServletListener implements HttpSessionAttributeListener {

    private static final Logger LOGGER = LogManager.getLogger(ServletListener.class.getName());

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {

        LOGGER.log(Level.INFO, (String) event.getValue());

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
    }

}
