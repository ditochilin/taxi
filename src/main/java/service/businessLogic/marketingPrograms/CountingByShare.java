package service.businessLogic.marketingPrograms;

import entities.Order;
import entities.Share;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.businessLogic.SystemHelper;
import service.exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Optional.of;

public class CountingByShare implements IMarketingProgram {

    private static final Logger LOGGER = LogManager.getLogger(CountingByShare.class.getName());
    private static IMarketingProgram instance;

    public static IMarketingProgram getInstance() {
        if (instance == null) {
            instance = new CountingByShare();
        }
        return instance;
    }

    @Override
    public void countCost(Order order) throws ServiceException {
        Share share = SystemHelper.getShareNotLoyalty(order.getShares(), of(Boolean.TRUE));

        if (!Optional.ofNullable(share).isPresent()) {
            LOGGER.log(Level.WARN, "Share is not set before discounting!");
            return;
        }

        // is sum of share !=0 than count like absolute discount
        // else if percent != 0 than count percent of base sum
        if (share.getSum().compareTo(BigDecimal.ZERO) > 0) {
            // cost value is used cause we should consider previous counting
            order.setCost(getCostChecked(order).subtract(share.getSum()));
        } else if (share.getPercent().compareTo(Float.NaN) > 0) {
            countCostByPercent(order, BigDecimal.valueOf(share.getPercent()));
        }
    }
}
