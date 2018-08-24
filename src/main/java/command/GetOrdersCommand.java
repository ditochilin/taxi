package command;

import dao.exceptions.DaoException;
import service.IOrderService;
import service.implementation.OrderService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetOrdersCommand implements ICommand {

    private static IOrderService orderService = OrderService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) {
        request.setAttribute("orders",orderService.findOrders());
        return Config.getProperty(Config.ORDERS);
    }
}
