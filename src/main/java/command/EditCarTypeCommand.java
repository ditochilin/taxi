package command;

//todo  : delete unused imports
import service.ICarTypeService;
import service.exceptions.ServiceException;
import service.implementation.CarTypeService;
import utils.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditCarTypeCommand implements ICommand {

    private static ICarTypeService carTypeService = CarTypeService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String carTypeId = request.getParameter("carTypeId");
        if(carTypeId!= null && !carTypeId.isEmpty()){
            request.setAttribute("carTypeDTO", carTypeService.getById(Long.valueOf(carTypeId)));
        }

        return Config.getProperty(Config.EDIT_CARTYPE);

    }
}
