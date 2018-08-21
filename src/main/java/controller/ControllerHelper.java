package controller;

import command.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ControllerHelper  {

    private static ControllerHelper instance = null;
    private static Map<String, ICommand> commands = new HashMap<String, ICommand>();

    private ControllerHelper() {
        commands.put("login", new LoginCommand());
        commands.put("getOrders", new GetOrdersCommand());
        commands.put("editOrder", new EditOrderCommand());
        commands.put("showOrders", new GetOrdersCommand());
    }

    public ICommand getCommand(HttpServletRequest request) {
        ICommand command = commands.get(request.getParameter("command"));
        if (command == null) {
            command = new EmptyCommand();
        }
        return command;
    }

    public static ControllerHelper getInstance() {
        if (instance == null) {
            instance = new ControllerHelper();
        }
        return instance;
    }

}
