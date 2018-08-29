package command;

import dao.IDao;
import entities.User;
import service.IRoleService;
import service.IUserService;
import service.implementation.RoleService;
import service.implementation.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveUserCommand implements ICommand {

    private IRoleService roleService = RoleService.getInstance();
    private IUserService userService = UserService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String roleName = request.getParameter("roleName");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        User userDTO = new User(phone, password, userName, roleService.getByName(roleName));
        if(!userService.update(userDTO, Long.getLong(id))){
            request.setAttribute("errorMessage", "User has not been added! Please, check all fields.");
            return new EditUserCommand().execute(request, response);
        }

        request.setAttribute("operationMessage", "User has been added successfully!");
        return new OpenAdministrationCommand().execute(request, response);
    }
}
