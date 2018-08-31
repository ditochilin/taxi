package command;

import entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IUserService;
import service.exceptions.ServiceException;
import service.implementation.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveUserCommand extends AbstractCommand<User> implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(SaveUserCommand.class.getName());
    private static IUserService userService;

    public SaveUserCommand() {
        service = UserService.getInstance();
        userService = UserService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException, IOException {
        return updateUser(request, response,
                new EditUserCommand(),
                new OpenAdministrationCommand());
    }

}
