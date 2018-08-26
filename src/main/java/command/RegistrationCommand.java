package command;

import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command for registration new user
 * @author Dmitry Tochilin
 */
public class RegistrationCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String role = request.getParameter("role");
        String userName = request.getParameter("login");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");



        return Config.getProperty(Config.REGISTRATION);
    }
}
