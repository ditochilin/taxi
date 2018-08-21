package command;

import dao.exceptions.DaoException;
import service.ITaxiOrderService;
import service.implementation.TaxiOrderService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetOrdersCommand implements ICommand {

    private static ITaxiOrderService orderService = TaxiOrderService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) {
        request.setAttribute("orders",orderService.findTaxiOrders());
        return Config.getProperty(Config.ORDERS);
    }
}
