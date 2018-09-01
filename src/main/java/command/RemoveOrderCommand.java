package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IOrderService;
import service.exceptions.ServiceException;
import service.implementation.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveOrderCommand implements ICommand {

    private static IOrderService orderService = OrderService.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(RemoveOrderCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        try {
            orderService.remove(Long.valueOf(request.getParameter("orderId")));
        } catch (Exception e) {
            request.setAttribute("resultMessage", "Could not remove this order! Sorry, please, message support service.");
            LOGGER.error(e.getCause());
        }

        return new OpenListOrdersCommand().execute(request,response);

    }
}
