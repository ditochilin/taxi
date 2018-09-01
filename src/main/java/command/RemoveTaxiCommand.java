package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ITaxiService;
import service.exceptions.ServiceException;
import service.implementation.TaxiService;
import service.implementation.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveTaxiCommand implements ICommand {

    private static ITaxiService taxiService;
    private static final Logger LOGGER = LogManager.getLogger(RemoveTaxiCommand.class.getName());


    public RemoveTaxiCommand() {
        taxiService = TaxiService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            taxiService.remove(Long.valueOf(request.getParameter("taxiId")));
        } catch (Exception e) {
            request.setAttribute("resultMessage", "Could not remove this taxi! Sorry, please, message support service.");
            LOGGER.error(e.getCause());
        }

        return new OpenListTaxisCommand().execute(request,response);
    }
}
