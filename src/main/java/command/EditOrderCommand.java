package command;

import entities.Order;
import entities.Share;
import entities.Status;
import service.IOrderService;
import service.businessLogic.SystemHelper;
import service.exceptions.ServiceException;
import service.implementation.OrderService;
import utils.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.function.Predicate;

public class EditOrderCommand implements ICommand {

    private static IOrderService orderService;

    public EditOrderCommand() {
        orderService = OrderService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String orderId = request.getParameter("orderId");
        if (orderId != null && !orderId.isEmpty()) {
            Order order = orderService.getById(Long.valueOf(orderId));
            List<Share> shares = order.getShares();

            // given that order may have only one loyalty and/or one share
            Share loyalty = shares.stream()
                    .filter(loy -> loy.getIsLoyalty())
                    .findAny()
                    .orElse(null);
            if(loyalty!=null) {
                request.setAttribute("loyaltyList", order);
            }


            Share share = shares.stream()
                    .filter(shar -> !shar.getIsLoyalty())
                    .findAny()
                    .orElse(null);
            if(loyalty!=null) {
                request.setAttribute("shareList", order);
            }
            //setShares(request, "loyalty", shares, new SystemHelper.CheckLoyalty());

            request.setAttribute("orderDTO", order);
        }

        request.setAttribute("statusList", Status.values());

        return Config.getProperty(Config.EDIT_ORDER);
    }

    /**
     * sets  loyalty and share in attributes
     * @param  shares - all shares of order
     * @param  ifIsLoyalty - Predicate : is share loyalty or not
     */
    private void setShares(HttpServletRequest request, String shareType, List<Share> shares, Predicate ifIsLoyalty ){
        Share share = SystemHelper.getShare(shares, ifIsLoyalty);
        if(share!=null){
            request.setAttribute(shareType, share);
        }
    }
}
