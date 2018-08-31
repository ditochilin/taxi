package command;

import service.IRoleService;
import service.IUserService;
import service.exceptions.ServiceException;
import service.implementation.RoleService;
import service.implementation.UserService;
import utils.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserCommand implements ICommand {

    private static IRoleService roleService = RoleService.getInstance();
    private static IUserService userService = UserService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String userId = request.getParameter("userId");
        if(userId!= null && !userId.isEmpty()){
            request.setAttribute("userDTO", userService.getById(Long.valueOf(userId)));
        }
        request.setAttribute("roleList", roleService.getAll());

        return Config.getProperty(Config.EDIT_USER);
    }
}
