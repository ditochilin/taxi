package utils;

import java.util.ResourceBundle;

/**
 * @author Dmitry Tochilin
 */
public class Messenger {

    private static final ResourceBundle resource = ResourceBundle.getBundle("messenger");
    public static final String SERVLET_EXCEPTION = "SERVLET_EXCEPTION";
    public static final String IO_EXCEPTION = "IO_EXCEPTION";
    public static final String LOGIN_ERROR = "LOGIN_ERROR";
    public static final String ACCESS_LEVEL_ERROR = "ACCESS_LEVEL_ERROR";
    public static final String NOENTITIES = "NOENTITIES";
    public static final String NOENTITYBYID = "NOENTITYBYID";

    public static String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
