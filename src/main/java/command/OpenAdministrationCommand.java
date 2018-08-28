package command;

import service.ITaxiService;
import service.IUserService;
import service.implementation.UserService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenAdministrationCommand implements ICommand {

    private IUserService userService = UserService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("userList",userService.getAllClients());

        return Config.getProperty(Config.ADMIN);
    }
}
