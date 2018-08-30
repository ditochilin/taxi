package command;

import service.IShareService;
import service.implementation.RoleService;
import service.implementation.ShareService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditShareCommand implements ICommand {

    private static IShareService shareService = ShareService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String shareId = request.getParameter("shareId");
        if(shareId!= null && !shareId.isEmpty()){
            request.setAttribute("shareDTO", shareService.getById(Long.valueOf(shareId)));
        }

        return Config.getProperty(Config.EDIT_SHARE);
    }
}
