package utils;

import java.util.ResourceBundle;

/**
 * @author Dmitry Tochilin
 */
public class Config {

    private static Config instance;
    private ResourceBundle resource;
    public static final String MAIN = "MAIN";
    public static final String ERROR = "ERROR";
    public static final String LOGIN = "LOGIN";

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
            instance.resource = ResourceBundle.getBundle("config");
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
