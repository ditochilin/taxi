package utils;

import java.util.ResourceBundle;

/**
 * @author Dmitry Tochilin
 */
public class Config {

    private static final ResourceBundle resource  = ResourceBundle.getBundle("config");
    public static final String MAIN = "MAIN";
    public static final String ERROR = "ERROR";
    public static final String LOGIN = "LOGIN";

    public static String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
