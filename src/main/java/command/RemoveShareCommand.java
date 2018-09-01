package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IShareService;
import service.exceptions.ServiceException;
import service.implementation.ShareService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveShareCommand implements ICommand {

    private static IShareService shareService = ShareService.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(RemoveShareCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        try {
            shareService.remove(Long.valueOf(request.getParameter("shareId")));
        }catch (ServiceException e){
            request.setAttribute("resultMessage", "You can't remove this share! May be it's used in orders.");
        }

        return new OpenListSharesCommand().execute(request,response);
    }
}
