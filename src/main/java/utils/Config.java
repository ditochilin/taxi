package utils;

import java.util.ResourceBundle;

/**
 * @author Dmitry Tochilin
 */
public class Config {

    private static final ResourceBundle resource  = ResourceBundle.getBundle("config");
    public static final String EDIT_USER = "EDIT_USER";
    public static final String ADMIN = "ADMIN";
    public static final String REGISTRATION = "REGISTRATION";
    public static final String TAXIS = "TAXIS";
    public static final String EDIT_ORDER = "EDIT_ORDER";
    public static final String ERROR = "ERROR";
    public static final String LOGIN = "LOGIN";
    public static final String ORDERS = "ORDERS";

    public static String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
