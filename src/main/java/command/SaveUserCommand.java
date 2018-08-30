package command;

import controller.ControllerHelper;
import entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IRoleService;
import service.IUserService;
import service.implementation.RoleService;
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

    static Set<String> checkUserFieldsErrors(String id, String userName, String phone, String password, String confirmPassword) {
        Set<String> errors = new HashSet<>();
        if (userService.suchNameIsPresent(userName) && id.isEmpty()) {
            errors.add("User with name " + userName + " is present already!");
        }
        if (userService.suchPhoneIsPresent(phone) && id.isEmpty()) {
            errors.add("User with phone " + phone + " is present already!");
        }
        if (password.length() < 4) {
            errors.add("Password is too short! Must be not less then 4 signs.");
        }
        if (!password.equals(confirmPassword)) {
            errors.add("Password are not equal!");
        }
        if (!AbstractCommand.patternPhone.matcher(phone).matches()) {
            errors.add("Phone numbber is not correct!");
        }
        return errors;
    }
}
