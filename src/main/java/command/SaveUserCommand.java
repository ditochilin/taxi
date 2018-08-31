package command;

import entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IUserService;
import service.implementation.UserService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SaveUserCommand extends AbstractCommand<User> implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(SaveUserCommand.class.getName());
    private static IUserService userService;

    public SaveUserCommand() {
        service = UserService.getInstance();
        userService = UserService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
                return updateUser(request,response,
                        new EditUserCommand(),
                        new OpenAdministrationCommand());
        } catch (ServletException | IOException e) {
            LOGGER.error("Could not execute command to add/update user!", e.getCause());
        }
        return Config.getProperty(Config.ADMIN);
    }

}
