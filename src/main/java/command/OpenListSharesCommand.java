package command;

import service.IShareService;
import service.exceptions.ServiceException;
import service.implementation.ShareService;
import utils.Config;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenListSharesCommand implements ICommand {

    private static IShareService shareService;

    public OpenListSharesCommand() {
        shareService = ShareService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        request.setAttribute("shareList",shareService.getAll());
        request.setAttribute("itemMenuSelected","shares");

        return Config.getProperty(Config.ADMIN);
    }
}
