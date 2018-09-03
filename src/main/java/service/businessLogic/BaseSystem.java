package service.businessLogic;

import entities.Order;
import entities.Share;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IShareService;
import service.exceptions.ServiceException;
import service.implementation.ShareService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Optional.*;
import static java.util.Optional.ofNullable;

public class BaseSystem {

    private static final Logger LOGGER = LogManager.getLogger(BaseSystem.class.getName());
    private static IShareService shareService = ShareService.getInstance();

    // base cost  - cost without any promotions / shares, discount....
    public static void countBaseCostOfTrip(Order order) throws ServiceException {
        try {
            // price - price of  money/km
            BigDecimal price = order.getCarType().getPrice();
            // price in (money / 1000 meters)  * distance in meters
            BigDecimal costBuff = price.multiply(BigDecimal.valueOf(order.getDistance()));
            // get money / km
            BigDecimal costValue = costBuff.divide(BigDecimal.valueOf(1000));
            order.setBaseCost(costValue);
            // set by default (without any discounts)
            order.setCost(costValue);

        } catch (Exception e) {
            throw new ServiceException("Could not count base cost", e);
        }
    }

    public static void countDistance(Order order) throws ServiceException {
        if (ifStartPointAndDestinationAreEmpty(order)) {
            String msg = "Attempt to get distance, but points are/is empty!";
            LOGGER.warn(msg);
            throw new ServiceException(msg, null);
        }
        if(!ofNullable(order.getDistance()).isPresent() || order.getDistance()==0) {
            order.setDistance(distanceSupplier(order.getStartPoint(), order.getEndPoint()));
        }
    }

    //  generate distance random [300 ... 10 000] meters
    private static Integer distanceSupplier(String startPoint, String endPoint) {
        return ThreadLocalRandom.current().nextInt(300, 10_000 + 1);
    }

    private static boolean ifStartPointAndDestinationAreEmpty(Order order) {
        return order.getStartPoint().isEmpty() ||
                order.getEndPoint().isEmpty();
    }

    // if no loyalty in order - set default loyalty, if no share  - set default share
    public static void setDefaultShares(Order order) throws ServiceException {
        List<Share> currentShareList = order.getShares();
        if (currentShareList.size() >= 2) {
            return;
        }

        List<Share> sharesAll = shareService.getAll();
        Share loyalty = SystemHelper.getLoyalty(sharesAll, of(Boolean.TRUE));
        Share share = SystemHelper.getShareNotLoyalty(sharesAll, of(Boolean.TRUE));

        if (loyalty!=null && SystemHelper.getLoyalty(currentShareList, Optional.empty())==null) {
            order.addShare(loyalty);
        }

        if (share!=null && SystemHelper.getShareNotLoyalty(currentShareList, Optional.empty())==null) {
            order.addShare(share);
        }
    }

    public static void setTimeToWait(Order order) {
        order.setWaitingTime( ThreadLocalRandom.current().nextInt(1, 30));
    }
}
