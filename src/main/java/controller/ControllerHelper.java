package controller;

import command.*;
import entities.Status;
import service.IUserService;
import service.ITaxiService;
import service.implementation.TaxiService;
import service.implementation.UserService;
import utils.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ControllerHelper  {

    private static ControllerHelper instance = null;
    private static Map<String, ICommand> commands = new HashMap<String, ICommand>();
    private static IUserService userService;
    private static ITaxiService taxiService;


    static {
        taxiService = TaxiService.getInstance();
        userService = UserService.getInstance();
    }

    private ControllerHelper() {
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("registration", new RegistrationCommand());
//        commands.put("getOrders", new GetOrdersCommand());
        commands.put("editOrder", new EditOrderCommand());
        commands.put("showOrders", new GetOrdersCommand());
        commands.put("changeLocale", new ChangeLocale());
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
