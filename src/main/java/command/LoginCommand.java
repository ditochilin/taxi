package command;

import entities.Role;
import entities.User;
import service.IUserService;
import service.exceptions.ServiceException;
import service.implementation.UserService;
import utils.Config;
import utils.Messenger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ICommand {

    // todo check if everything has been done
    private static IUserService userService = UserService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User userDump = new User(login, password);

        try {
            userDump = userService.checkUserByPassword(userDump);
            HttpSession session = request.getSession(false);
            // todo:  && session.isNew() ???
            if (userDump != null ) {
                Role role = userDump.getRole();
                session.setAttribute("user", login);
                session.setAttribute("role", role);
                //response.addCookie(new Cookie("JSESSIONID", session.getId()));
                return getCorrectPage(role);
            }
        } catch (ServiceException e) {
            request.setAttribute("errorDescription", e.getMessage());
        }

        request.setAttribute("error", Messenger.getProperty(Messenger.LOGIN_ERROR));
        return Config.getProperty(Config.ERROR);
    }

    private String getCorrectPage(Role role) {
        switch (role.getRoleName()) {
            case "Driver":
                return Config.getProperty(Config.ORDERS);
            case "Client":
                return Config.getProperty(Config.ONE_ORDER);
            default:
                return Config.getProperty(Config.MAIN);
        }
    }
}
