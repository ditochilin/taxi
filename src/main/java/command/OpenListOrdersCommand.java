package command;

import service.IOrderService;
import service.implementation.OrderService;
import service.implementation.TaxiService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenListOrdersCommand implements ICommand {

    private static IOrderService orderService = OrderService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("orderList", orderService.getAll());

        return Config.getProperty(Config.ORDERS);
    }
}
