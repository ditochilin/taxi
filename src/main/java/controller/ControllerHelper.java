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
import java.nio.charset.StandardCharsets;
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
        commands.put("openRegistration", new OpenRegistrationCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("editOrder", new EditOrderCommand());
        commands.put("editUser", new EditUserCommand());
        commands.put("saveUser", new SaveUserCommand());
        commands.put("openAdministration", new OpenAdministrationCommand());
        commands.put("openListUsers", new OpenAdministrationCommand());   // the same with OpenAdministrationCommand()
        commands.put("openListShares", new OpenListSharesCommand());
        commands.put("openListCarTypes", new OpenListCarTypesCommand());
        commands.put("openListTaxis", new OpenListTaxisCommand());
        commands.put("openListOrders", new OpenListOrdersCommand());
        commands.put("changeLocale", new ChangeLocale());
    }

    public ICommand getCommand(HttpServletRequest request) {
        if(ifMakeLogout(request)){
            return new LogoutCommand();
        }
        ICommand command = commands.get(request.getParameter("command"));
        if (command == null) {
            command = new EmptyCommand();
        }
        return command;
    }

    private boolean ifMakeLogout(HttpServletRequest request) {
        return request.getAttribute("redirect")
                .equals(Config.getProperty(Config.LOGIN));
    }

    public static String getParamiterInUTF8(HttpServletRequest request, String param){
        String value = request.getParameter(param);
        byte[] bytes = value.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static ControllerHelper getInstance() {
        if (instance == null) {
            instance = new ControllerHelper();
        }
        return instance;
    }

}
