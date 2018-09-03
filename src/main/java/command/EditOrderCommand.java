package command;

import entities.CarType;
import entities.Order;
import entities.Share;
import entities.Status;
import service.*;
import service.businessLogic.SystemHelper;
import service.exceptions.ServiceException;
import service.implementation.*;
import utils.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Optional.*;

public class EditOrderCommand implements ICommand {

    private static IOrderService orderService;
    private static IShareService shareService;
    private static IUserService  userService;
    private static ITaxiService taxiService;
    private static ICarTypeService carTypeService;

    public EditOrderCommand() {
        shareService = ShareService.getInstance();
        userService = UserService.getInstance();
        orderService = OrderService.getInstance();
        taxiService = TaxiService.getInstance();
        carTypeService = CarTypeService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        List<Share> sharesAll = shareService.getAll();
        Object[] loyaltyArray = sharesAll.stream()
                .filter(loy -> loy.getIsLoyalty() && (Boolean.TRUE.equals(loy.getIsOn())))
                .toArray();
        if(loyaltyArray.length>0) {
            request.setAttribute("loyaltyList", Arrays.asList(loyaltyArray));
        }

        Object[] shareArray = sharesAll.stream()
                .filter(shar -> !shar.getIsLoyalty() && (Boolean.TRUE.equals(shar.getIsOn())))
                .toArray();
        if(shareArray.length>0) {
            request.setAttribute("shareList", Arrays.asList(shareArray));
        }

        String orderId = request.getParameter("orderId");
        CarType selectedCarType = null;
        if (orderId != null && !orderId.isEmpty()) {
            Order order = orderService.getById(Long.valueOf(orderId));
            Share loyalty = SystemHelper.getLoyalty(order.getShares(), of(Boolean.TRUE));

            if(loyalty!=null) {
                request.setAttribute("loyalty", loyalty);
            }

            Share share = SystemHelper.getShareNotLoyalty(order.getShares(),of(Boolean.TRUE));
            if(share!=null) {
                request.setAttribute("share", share);
            }

            selectedCarType = order.getCarType();

            request.setAttribute("orderDTO", order);
        }

        request.setAttribute("statusList", Status.values());
        request.setAttribute("carTypeList", carTypeService.getAll());
        request.setAttribute("clientList", userService.getAllClients());
        request.setAttribute("taxiList", taxiService.getFreeTaxis(ofNullable(selectedCarType)));

        return Config.getProperty(Config.EDIT_ORDER);
    }

    /**
     * sets  loyalty and share in attributes
     * @param  shares - all shares of order
     * @param  ifIsLoyalty - Predicate : is share loyalty or not
     */
//    private void setShares(HttpServletRequest request, String shareType, List<Share> shares, Predicate ifIsLoyalty ){
//        Share share = SystemHelper.getShare(shares, ifIsLoyalty);
//        if(share!=null){
//            request.setAttribute(shareType, share);
//        }
//    }
}
