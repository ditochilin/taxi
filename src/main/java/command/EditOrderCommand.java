package command;

import entities.Status;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditOrderCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("statusList", Status.values());

        return Config.getProperty(Config.EDIT_ORDER);
    }
}
