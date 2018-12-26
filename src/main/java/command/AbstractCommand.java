package command;

import controller.ControllerHelper;
import entities.Role;
import entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IRoleService;
import service.IService;
import service.IUserService;
import service.exceptions.EmptyException;
import service.exceptions.ServiceException;
import service.implementation.RoleService;
import service.implementation.UserService;
import utils.Messenger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Class with common for commands methods
 */
public abstract class AbstractCommand<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractCommand.class.getName());

    protected IService service;
    protected static Pattern patternPhone;
    private static IRoleService roleService = RoleService.getInstance();
    private static IUserService userService = UserService.getInstance();

    static {
        patternPhone = Pattern.compile("^\\+380[4-9][0-9]\\d{7}$");
    }

    /**
     *
     * @return  page
     */
    protected <T> String doUpdateEntity(T entity,
                                         Set<String> errors,
                                         Long id,
                                         HttpServletRequest request, HttpServletResponse response,
                                         ICommand commandForReturning,
                                         ICommand commandForSuccess) throws ServletException, IOException, ServiceException {
        StringBuilder msg = new StringBuilder();
        //  union two acts in one common catch
        try{
            if (!errors.isEmpty()) {
                throw new EmptyException();
            }
            service.update(
                    entity,
                    id,
                    msg);
        } catch (Exception e){
            // if not EmptyException than exceptions from update() are waited to get
            if(!(e instanceof EmptyException)) {
                errors.add(String.valueOf(e.getMessage()));
            }
            request.setAttribute("errors", errors);
            return commandForReturning.execute(request,response);
        }

        request.setAttribute("resultMessage", msg.toString());
        return commandForSuccess.execute(request, response);
    }

    /**
     * This method is common for commands: Registration and SaveUser
     */
    protected String updateUser( HttpServletRequest request, HttpServletResponse response,
                                 ICommand commandForReturning,
                                 ICommand commandForSuccess) throws ServletException, IOException, ServiceException {

        if(!currentUserIsAdmin(request.getSession()) && request.getParameter("actionRegister")==null){
            String msg = String.format(Messenger.ACCESS_LEVEL_ERROR, request.getSession().getAttribute("user"));
            LOGGER.warn(msg);
            throw new ServiceException(msg, null);
        }


        String idParam = request.getParameter("userId");
        String roleName = request.getParameter("roleName");
        String userName = ControllerHelper.getParameterInUTF8(request, "userName");
        String phone = request.getParameter("phone");
        String password = ControllerHelper.getParameterInUTF8(request, "password");
        String confirmPassword = ControllerHelper.getParameterInUTF8(request, "confirmPassword");

        Set<String> errors = checkUserFieldsErrors(idParam, userName, phone, password, confirmPassword);
        Long id = (idParam==null)?Long.valueOf(0):Long.valueOf(idParam);

        return doUpdateEntity(new User(id, phone, password, userName, roleService.getByName(roleName)),
                errors,
                id,
                request,
                response,
                commandForReturning,
                commandForSuccess);
    }

    private boolean currentUserIsAdmin(HttpSession session) throws ServiceException {
        String roleName = (String) session.getAttribute("role");
        if(roleName==null){
            return false;
        }
        Role role = roleService.getByName(
                roleName);
        return "ADMIN".equals(role.getRoleName());
    }

    private static Set<String> checkUserFieldsErrors(String id, String userName, String phone, String password, String confirmPassword) {
        Set<String> errors = new HashSet<>();
        try {
            if (userService.suchNameIsPresent(userName) && id == null) {
                errors.add("User with name " + userName + " is present already!");
            }
            if (userService.suchPhoneIsPresent(phone) && id == null) {
                errors.add("User with phone " + phone + " is present already!");
            }
            if (password.length() < 4) {
                errors.add("Password is too short! Must be not less then 4 signs.");
            }
            if (!password.equals(confirmPassword)) {
                errors.add("Password are not equal!");
            }
            if (!AbstractCommand.patternPhone.matcher(phone).matches()) {
                errors.add("Phone number is not correct!");
            }
        }catch (Exception e){
            errors.add(e.getMessage());
            LOGGER.error(e.getCause());
        }
        return errors;
    }

    protected static Long getLongParameter(HttpServletRequest request, String name){
        String value = request.getParameter(name);
        if(value==null || value.isEmpty()){
            return null;
        }
        return Long.valueOf(value);
    }

    protected static Integer getIntegerParameter(HttpServletRequest request, String name){
        String value = request.getParameter(name);
        if(value==null || value.isEmpty()){
            return null;
        }
        return Integer.valueOf(value);
    }

    // Returns true if x is in range [low..high], else false
    protected static boolean inRange(float low, float high, float x)
    {
        return ((x-high)*(x-low) <= 0);
    }
}

