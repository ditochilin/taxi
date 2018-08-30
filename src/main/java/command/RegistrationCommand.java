package command;

import entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command for registration new user
 *
 * @author Dmitry Tochilin
 */
public class RegistrationCommand extends AbstractCommand<User> implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String action = request.getParameter("action");
        if (action == null ||
                request.getAttribute("errors") != null) {
            return Config.getProperty(Config.REGISTRATION);
        } else if (action.equals("exit")) {
            return Config.getProperty(Config.LOGIN);
        }

        try {
            return updateUser(request, response,
                    new RegistrationCommand(),
                    new OpenLoginPageCommand());
        } catch (ServletException | IOException e) {
            LOGGER.error("Could not execute command registration");
        }

        return Config.getProperty(Config.LOGIN);
    }


}
