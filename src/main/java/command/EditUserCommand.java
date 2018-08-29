package command;

import dao.implementation.RoleDaoImpl;
import service.IRoleService;
import service.IService;
import service.implementation.RoleService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserCommand implements ICommand {

    private static IRoleService roleService = RoleService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("roleList", roleService.getAll());

        return Config.getProperty(Config.EDIT_USER);
    }
}
