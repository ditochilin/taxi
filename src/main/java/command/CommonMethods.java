package command;

import controller.ControllerHelper;
import entities.User;
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
import java.util.regex.Pattern;

/**
 * Class with common for commands methods
 */
public class CommonMethods {

    private static IUserService userService = UserService.getInstance();
    private static Pattern patternPhone;
    private static IRoleService roleService = RoleService.getInstance();

    static {
        patternPhone = Pattern.compile("^\\+380[4-9][0-9]\\d{7}$");
    }
    /**
     * This method is common for commands: Registration and SaveUser
     * @return  page
     */
    public static String addNewUser(HttpServletRequest request, HttpServletResponse response,
                                    ICommand commandForReturning,
                                    ICommand commandForSuccess) throws ServletException, IOException {

        String id = request.getParameter("userId");
        String roleName = request.getParameter("roleName");
        String userName = ControllerHelper.getParamiterInUTF8(request,"userName");
        String phone = request.getParameter("phone");
        String password = ControllerHelper.getParamiterInUTF8(request,"password");
        String confirmPassword = ControllerHelper.getParamiterInUTF8(request,"confirmPassword");

        Set<String> errors = getErrors(userName, phone, password, confirmPassword);

        //  union two acts in one common catch
        try{
            if (!errors.isEmpty()) {
                throw new Exception();
            }
            userService.update(
                    new User(phone, password, userName, roleService.getByName(roleName)),
                    Long.getLong(id));
        } catch (Exception e){
            errors.add(String.valueOf(e.getCause()));
            request.setAttribute("errors", errors);
            return commandForReturning.execute(request,response);
        }

        request.setAttribute("messageBeforeLogin", "Success Registration!");
        return commandForSuccess.execute(request, response);
    }

    private static Set<String> getErrors(String userName, String phone, String password, String confirmPassword) {
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
