package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ICarTypeService;
import service.exceptions.ServiceException;
import service.implementation.CarTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveCarTypeCommand implements ICommand {

    private static ICarTypeService carTypeService = CarTypeService.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(RemoveCarTypeCommand.class.getName());


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            carTypeService.remove(Long.valueOf(request.getParameter("carTypeId")));
        }catch (ServiceException e){
            request.setAttribute("resultMessage", "You can't remove this car type! May be it's used in taxis or orders.");
        }

        return new OpenListCarTypesCommand().execute(request,response);

    }
}
