package command;

import entities.User;
import service.IUserService;
import service.implementation.UserService;
import utils.Config;
import utils.Messenger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements ICommand {

    // todo check if everything has been done
    private static IUserService userService = UserService.getInstance();

    public LoginCommand() {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User userDump = new User(login, password);

        if (userService.userIsCorrect(userDump)) {
            request.setAttribute("user", login);
            return Config.getProperty(Config.MAIN);
        }

        request.setAttribute("error", Messenger.getProperty(Messenger.LOGIN_ERROR));
        return Config.getProperty(Config.ERROR);
    }
}
