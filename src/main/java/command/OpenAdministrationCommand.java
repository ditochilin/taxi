package command;

import service.IUserService;
import service.exceptions.ServiceException;
import service.implementation.UserService;
import utils.Config;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenAdministrationCommand implements ICommand {

    private IUserService userService;

    public OpenAdministrationCommand() {
        userService = UserService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        request.setAttribute("userList",userService.getAll());
        request.setAttribute("itemMenuSelected","users");

        return Config.getProperty(Config.ADMIN);
    }
}
