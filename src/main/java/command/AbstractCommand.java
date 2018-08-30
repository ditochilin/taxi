package command;

import controller.ControllerHelper;
import entities.Share;
import entities.User;
import service.IRoleService;
import service.IService;
import service.implementation.RoleService;
import service.implementation.ShareService;
import service.implementation.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Class with common for commands methods
 */
public abstract class AbstractCommand<T> {

    protected static IService service;
    protected static Pattern patternPhone;
    private static IRoleService roleService = RoleService.getInstance();

    static {
        patternPhone = Pattern.compile("^\\+380[4-9][0-9]\\d{7}$");
    }

    /**
     * This method is common for commands: Registration and SaveUser
     * @return  page
     */
    protected <T> String doUpdateEntity(T entity,
                                         Set<String> errors,
                                         String id,
                                         HttpServletRequest request, HttpServletResponse response,
                                         ICommand commandForReturning,
                                         ICommand commandForSuccess) throws ServletException, IOException {
        StringBuilder msg = new StringBuilder();
        //  union two acts in one common catch
        try{
            if (!errors.isEmpty()) {
                throw new Exception();
            }
            service.update(
                    entity,
                    Long.valueOf(id),
                    msg);
        } catch (Exception e){
            if(e.getCause()!=null) {
                errors.add(String.valueOf(e.getCause()));
            }
            request.setAttribute("errors", errors);
            return commandForReturning.execute(request,response);
        }

        request.setAttribute("resultMessage", msg.toString());
        return commandForSuccess.execute(request, response);
    }

    protected String updateUser( HttpServletRequest request, HttpServletResponse response,
                                 ICommand commandForReturning,
                                 ICommand commandForSuccess) throws ServletException, IOException {
        String id = request.getParameter("userId");
        String roleName = request.getParameter("roleName");
        String userName = ControllerHelper.getParameterInUTF8(request, "userName");
        String phone = request.getParameter("phone");
        String password = ControllerHelper.getParameterInUTF8(request, "password");
        String confirmPassword = ControllerHelper.getParameterInUTF8(request, "confirmPassword");

        Set<String> errors = SaveUserCommand.checkUserFieldsErrors(id, userName, phone, password, confirmPassword);

        return doUpdateEntity(new User(phone, password, userName, roleService.getByName(roleName)),
                errors,
                id,
                request,
                response,
                commandForReturning,
                commandForSuccess);

    }

    // Returns true if x is in range [low..high], else false
    protected static boolean inRange(float low, float high, float x)
    {
        return ((x-high)*(x-low) <= 0);
    }
}
