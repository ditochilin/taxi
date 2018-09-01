package command;

import service.ICarTypeService;
import service.ITaxiService;
import service.IUserService;
import service.exceptions.ServiceException;
import service.implementation.CarTypeService;
import service.implementation.TaxiService;
import service.implementation.UserService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class EditTaxiCommand implements ICommand {
    private static ITaxiService taxiService;
    private static IUserService userService;
    private static ICarTypeService carTypeService;

    public EditTaxiCommand() {
        userService = UserService.getInstance();
        taxiService = TaxiService.getInstance();
        carTypeService = CarTypeService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String taxiId = request.getParameter("taxiId");
        if(Optional.ofNullable(taxiId).isPresent()){
            request.setAttribute("taxiDTO", taxiService.getById(Long.valueOf(taxiId)));
        }
        request.setAttribute("driverList", userService.getAllDrivers());
        request.setAttribute("carTypeList", carTypeService.getAll());

        return Config.getProperty(Config.EDIT_TAXI);
    }
}
