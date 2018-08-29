package command;

import controller.ControllerHelper;
import entities.User;
import service.IRoleService;
import service.IUserService;
import service.implementation.RoleService;
import service.implementation.UserService;
import utils.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Command for registration new user
 *
 * @author Dmitry Tochilin
 */
public class RegistrationCommand implements ICommand {

    private static IUserService userService = UserService.getInstance();
    private static Pattern patternPhone;
    private static IRoleService roleService = RoleService.getInstance();

    static {
        patternPhone = Pattern.compile("^\\+380[4-9][0-9]\\d{7}$");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        if (action == null) {
            return Config.getProperty(Config.REGISTRATION);
        } else if (action.equals("exit")) {
            return Config.getProperty(Config.LOGIN);
        }

        String roleName = request.getParameter("role");
        String userName = ControllerHelper.getParamiterInUTF8(request,"login");
        String phone = request.getParameter("phone");
        String password = ControllerHelper.getParamiterInUTF8(request,"password");
        String confirmPassword = ControllerHelper.getParamiterInUTF8(request,"confirmPassword");

        Set<String> errors = getErrors(userName, phone, password, confirmPassword);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            return Config.getProperty(Config.REGISTRATION);
        }

        if (!userService.update(new User(phone, password, userName, roleService.getByName(roleName)), null)) {
            return Config.getProperty(Config.ERROR);
        }

        request.setAttribute("messageBeforeLogin", "Success Registration!");
        return Config.getProperty(Config.LOGIN);
    }

    private Set<String> getErrors(String userName, String phone, String password, String confirmPassword) {
        Set<String> errors = new HashSet<>();
        if (userService.suchNameIsPresent(userName)) {
            errors.add("User with name " + userName + " is present already!");
        }
        if (userService.suchPhoneIsPresent(phone)) {
            errors.add("User with phone " + phone + " is present already!");
        }
        if (password.length() < 4) {
            errors.add("Password is too short! Must be not less then 4 signs.");
        }
        if (!password.equals(confirmPassword)) {
            errors.add("Password are not equal!");
        }
        if (!patternPhone.matcher(phone).matches()) {
            errors.add("Phone numbber is not correct!");
        }
        return errors;
    }

}
