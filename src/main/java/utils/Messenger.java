package utils;

import java.util.ResourceBundle;

/**
 *
 * @author Dmitry Tochilin
 */
public class Messenger {

    private static Messenger instance;
    private ResourceBundle resource;
    public static final String SERVLET_EXCEPTION = "SERVLET_EXCEPTION";
    public static final String IO_EXCEPTION = "IO_EXCEPTION";
    public static final String LOGIN_ERROR = "LOGIN_ERROR";

    public static Messenger getInstance() {
        if (instance == null) {
            instance = new Messenger();
            instance.resource = ResourceBundle.getBundle("messenger");
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
