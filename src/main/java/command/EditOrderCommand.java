package command;

import entities.Order;
import entities.Share;
import entities.Status;
import service.IOrderService;
import service.IShareService;
import service.businessLogic.SystemHelper;
import service.exceptions.ServiceException;
import service.implementation.OrderService;
import service.implementation.ShareService;
import utils.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class EditOrderCommand implements ICommand {

    private static IOrderService orderService;
    private static IShareService shareService;

    public EditOrderCommand() {
        shareService = ShareService.getInstance();
        orderService = OrderService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String orderId = request.getParameter("orderId");
        if (orderId != null && !orderId.isEmpty()) {
            Order order = orderService.getById(Long.valueOf(orderId));
            List<Share> sharesOfOrder = order.getShares();
            List<Share> sharesAll = shareService.getAll();

            // given that order may have only one loyalty and/or one share
            Share loyalty = sharesOfOrder.stream()
                    .filter(loy -> loy.getIsLoyalty())
                    .findAny()
                    .orElse(null);
            if(loyalty!=null) {
                request.setAttribute("loyalty", loyalty);
            }

            Object[] loyaltyArray = sharesAll.stream()
                    .filter(loy -> loy.getIsLoyalty())
                    .toArray();
            if(loyaltyArray.length>0) {
                request.setAttribute("loyaltyList", Arrays.asList(loyaltyArray));
            }


            Share share = sharesOfOrder.stream()
                    .filter(shar -> !shar.getIsLoyalty())
                    .findAny()
                    .orElse(null);
            if(share!=null) {
                request.setAttribute("share", share);
            }

            Object[] shareArray = sharesAll.stream()
                    .filter(shar -> !shar.getIsLoyalty())
                    .toArray();
            if(shareArray.length>0) {
                request.setAttribute("shareList", Arrays.asList(shareArray));
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
