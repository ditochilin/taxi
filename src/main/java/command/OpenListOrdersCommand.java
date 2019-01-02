package command;

import entities.Order;
import entities.User;
import service.IOrderService;
import service.IUserService;
import service.exceptions.ServiceException;
import service.implementation.OrderService;
import service.implementation.TaxiService;
import service.implementation.UserService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenListOrdersCommand implements ICommand {

    private static IOrderService orderService = OrderService.getInstance();
    private static IUserService userService = UserService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        Long id = (Long)session.getAttribute("userId");
        User currentUser = userService.getById(id);
        String currentRole = (String)session.getAttribute("role");
        List<Order> orderList = new ArrayList<>();

        if(currentRole.equals("DRIVER")) {
            orderList = orderService.getByDriver(currentUser);
        } else if (currentRole.equals("CLIENT")) {
            orderList = orderService.getByClient(currentUser);
        } else if(currentRole.equals("ADMIN")){
            orderList = orderService.getAll();
        }

        request.setAttribute("orderList", orderList);

        return Config.getProperty(Config.ORDERS);
    }
}
