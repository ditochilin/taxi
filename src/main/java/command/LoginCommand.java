package command;

import entities.Role;
import entities.User;
import service.IUserService;
import service.exceptions.IncorrectPassword;
import service.exceptions.ServiceException;
import service.implementation.UserService;
import utils.Config;
import utils.Messenger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements ICommand {

    private static IUserService userService = UserService.getInstance();
    private static ICommand commandOpenAdmin;
    private static ICommand commandEditOrder;
    private static ICommand commandOpenOrders;

    // todo check everywhere instances
    public LoginCommand() {
        commandOpenAdmin = new OpenAdministrationCommand();
        commandEditOrder = new EditOrderCommand();
        commandOpenOrders = new OpenListOrdersCommand();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User userDump = new User(login, password);

        try {
            userDump = userService.checkUserByPassword(userDump);
            HttpSession session = request.getSession(false);
            if (userDump != null) {
                Role role = userDump.getRole();
                session.setAttribute("user", userDump.getUserName());
                session.setAttribute("role", role.getRoleName());
                if (session.getAttribute("locale") == null) {
                    session.setAttribute("locale", "en_En");
                }
                return doCorrectRedirection(role, request, response);
            }
        } catch (IncorrectPassword e) {
            request.setAttribute("messageBeforeLogin", "Incorrect password");
            return Config.getProperty(Config.LOGIN);
        } catch (ServiceException | ServletException | IOException e) {
            request.setAttribute("errorDescription", e.getMessage());
            return Config.getProperty(Config.ERROR);
        }

        request.setAttribute("messageBeforeLogin", Messenger.getProperty(Messenger.LOGIN_ERROR));
        return Config.getProperty(Config.LOGIN);
    }

    private String doCorrectRedirection(Role role, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (role.getRoleName()) {
            case "DRIVER":
                return commandOpenOrders.execute(request, response);
            case "CLIENT":
                return commandEditOrder.execute(request, response);
            case "ADMIN":
                return commandOpenAdmin.execute(request, response);
            default:
                return Config.getProperty(Config.LOGIN);
        }
    }

}
