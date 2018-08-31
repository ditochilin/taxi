package command;

import entities.CarType;
import service.ICarTypeService;
import service.IShareService;
import service.implementation.CarTypeService;
import service.implementation.ShareService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditCarTypeCommand implements ICommand {

    private static ICarTypeService carTypeService = CarTypeService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String carTypeId = request.getParameter("carTypeId");
        if(carTypeId!= null && !carTypeId.isEmpty()){
            request.setAttribute("shareDTO", carTypeService.getById(Long.valueOf(carTypeId)));
        }

        return Config.getProperty(Config.EDIT_CARTYPE);

    }
}
