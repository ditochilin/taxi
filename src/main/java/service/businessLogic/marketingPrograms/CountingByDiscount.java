package service.businessLogic.marketingPrograms;

import entities.Order;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.Optional;

public class CountingByDiscount implements IMarketingProgram {

    private static final Logger LOGGER = LogManager.getLogger(CountingByDiscount.class.getName());
    private static IMarketingProgram instance;

    public static IMarketingProgram getInstance() {
        if (instance == null) {
            instance = new CountingByDiscount();
        }
        return instance;
    }

    @Override
    public void countCost(Order order) throws ServiceException {

        if (!Optional.ofNullable(order.getDiscount()).isPresent()) {
            LOGGER.log(Level.WARN ,"Discount is not set before discounting!");
            return;
        }
        BigDecimal discount = BigDecimal.valueOf(order.getDiscount().intValue());

        countCostByPercent(order, discount);
    }
}
