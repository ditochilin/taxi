package utils;

import java.util.ResourceBundle;

/**
 * @author Dmitry Tochilin
 */
public class Config {

    public static final String EDIT_SHARE = "EDIT_SHARE";
    public static final String EDIT_CARTYPE = "EDIT_CARTYPE";
    public static final String EDIT_TAXI = "EDIT_TAXI";
    public static final String EDIT_USER = "EDIT_USER";
    public static final String ADMIN = "ADMIN";
    public static final String REGISTRATION = "REGISTRATION";
    public static final String TAXIS = "TAXIS";
    public static final String EDIT_ORDER = "EDIT_ORDER";
    public static final String ERROR = "ERROR";
    public static final String LOGIN = "LOGIN";
    public static final String ORDERS = "ORDERS";
    private static final ResourceBundle resource  = ResourceBundle.getBundle("config");

    public static String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
