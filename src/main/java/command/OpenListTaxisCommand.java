package command;

import entities.Taxi;
import entities.User;
import service.ITaxiService;
import service.IUserService;
import service.exceptions.ServiceException;
import service.implementation.TaxiService;
import service.implementation.UserService;
import utils.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OpenListTaxisCommand implements ICommand {

    private static ITaxiService taxiService = TaxiService.getInstance();
    private static IUserService userService = UserService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        List<Taxi> taxiList;
        HttpSession session = request.getSession();
        if(session.getAttribute("role").equals("DRIVER")){
            User driver = userService.getUsersByName(session.getAttribute("user").toString()).get(0);
            taxiList = taxiService.getByDriver(driver);
        } else {
            taxiList = taxiService.getAll();
        }

        request.setAttribute("taxiList", taxiList);
        return Config.getProperty(Config.TAXIS);
    }
}
