package command;

import entities.Role;
import entities.Status;
import entities.User;
import service.IUserService;
import service.exceptions.IncorrectPassword;
import service.exceptions.ServiceException;
import service.implementation.UserService;
import utils.Config;
import utils.Messenger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class LoginCommand implements ICommand {

    private static IUserService userService = UserService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User userDump = new User(login, password);

        try {
            userDump = userService.checkUserByPassword(userDump);
            HttpSession session = request.getSession(false);
            if (userDump != null ) {
                Role role = userDump.getRole();
                session.setAttribute("user", userDump.getUserName());
                session.setAttribute("role", role.getRoleName());
                if(session.getAttribute("locale")==null) {
                    session.setAttribute("locale", "en_En");
                }
                return getCorrectPage(role);
            }
        } catch (IncorrectPassword e) {
            request.setAttribute("messageBeforeLogin", "Incorrect password");
            return Config.getProperty(Config.LOGIN);
        } catch (ServiceException e) {
            request.setAttribute("errorDescription", e.getMessage());
            return Config.getProperty(Config.ERROR);
        }

        request.setAttribute("messageBeforeLogin", Messenger.getProperty(Messenger.LOGIN_ERROR));
        return Config.getProperty(Config.LOGIN);
    }

    private String getCorrectPage(Role role) {
        switch (role.getRoleName()) {
            case "Driver":
                return Config.getProperty(Config.ORDERS);
            case "Client":
                return Config.getProperty(Config.EDIT_ORDER);
            case "Admin":
                return Config.getProperty(Config.ADMIN);
            default:
                return Config.getProperty(Config.LOGIN);
        }
    }

}
