package command;

import service.ICarTypeService;
import service.exceptions.ServiceException;
import service.implementation.CarTypeService;
import utils.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenListCarTypesCommand implements ICommand {

    private static ICarTypeService carTypeService;

    public OpenListCarTypesCommand() {
        carTypeService = CarTypeService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        request.setAttribute("carTypeList", carTypeService.getAll());
        request.setAttribute("itemMenuSelected", "carTypes");

        return Config.getProperty(Config.ADMIN);
    }
}
