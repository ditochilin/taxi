package command;

import entities.CarType;
import service.ICarTypeService;
import service.IShareService;
import service.implementation.CarTypeService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenListCarTypesCommand implements ICommand {

    private static ICarTypeService carTypeService;

    public OpenListCarTypesCommand() {
        carTypeService = CarTypeService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("carTypeList",carTypeService.getAll());
        request.setAttribute("itemMenuSelected","carTypes");

        return Config.getProperty(Config.ADMIN);
    }
}
