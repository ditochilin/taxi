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

public class CountingByLoyalty implements IMarketingProgram {

    private static final Logger LOGGER = LogManager.getLogger(CountingByLoyalty.class.getName());
    private static IMarketingProgram instance;

    public static IMarketingProgram getInstance() {
        if (instance == null) {
            instance = new CountingByLoyalty();
        }
        return instance;
    }

    @Override
    public void countCost(Order order) throws ServiceException {

        Share loyalty = SystemHelper.getLoyalty(order.getShares(), of(Boolean.TRUE));

        if (!Optional.ofNullable(loyalty).isPresent()) {
            LOGGER.log(Level.WARN ,"Loyalty is not set before discounting!");
            return;
        }

        // base cost is more or equal to sum in loyalty (like barrier)
        if(getBaseCostChecked(order).compareTo(loyalty.getSum())>=0){
            countCostByPercent(order, BigDecimal.valueOf(loyalty.getPercent()));
        }
    }
}
