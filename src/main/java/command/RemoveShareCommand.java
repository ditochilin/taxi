package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IShareService;
import service.implementation.ShareService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveShareCommand implements ICommand {

    private static IShareService shareService = ShareService.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(RemoveShareCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            shareService.remove(Long.valueOf(request.getParameter("shareId")));
        } catch (Exception e) {
            LOGGER.error(e.getCause());
        }

        return new OpenListSharesCommand().execute(request,response);
    }
}
