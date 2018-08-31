package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IUserService;
import service.exceptions.ServiceException;
import service.implementation.UserService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveUserCommand implements ICommand {

    private static IUserService userService = UserService.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(RemoveUserCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        try {
            userService.remove(Long.valueOf(request.getParameter("userId")));
        } catch (Exception e) {
            LOGGER.error(e.getCause());
        }

        return new OpenAdministrationCommand().execute(request,response);
    }
}
