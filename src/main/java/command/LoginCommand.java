package command;

import entities.Role;
import entities.Status;
import entities.User;
import service.IUserService;
import service.exceptions.ServiceException;
import service.implementation.UserService;
import utils.Config;
import utils.Messenger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

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
                session.setAttribute("user", userDump.getUserName());
                session.setAttribute("role", role.getRoleName());
                session.setAttribute("locale", "en");  // todo :
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
                return Config.getProperty(Config.EDIT_ORDER);
            default:
                return Config.getProperty(Config.MAIN);
        }
    }

    public void prepareBeforeRendering(String page, HttpServletRequest request, HttpServletResponse response) {
        if(page.equals(Config.getProperty(Config.EDIT_ORDER))){
            request.setAttribute("statusList",Status.values());
            request.setAttribute("clientList",userService.getAllClients());
     //       request.setAttribute("taxiList",taxiService.findFreeTaxis());
        }
    }
}
