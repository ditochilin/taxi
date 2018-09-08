package service.businessLogic.marketingPrograms;

import entities.Order;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Describes method countCost for various marketing programs.
 * Is used in orderService to count cost value
 *
 * @author Dmitry Tochilin
 */
public interface IMarketingProgram {

    Logger LOGGER = LogManager.getLogger(IMarketingProgram.class.getName());

    void countCost(Order order) throws ServiceException;




    default void countCostByPercent(Order order, BigDecimal percent) throws ServiceException {

        BigDecimal baseCost = getBaseCostChecked(order);
        BigDecimal costValue = getCostChecked(order);

        if(!Optional.ofNullable(percent).isPresent() || percent.equals(BigDecimal.ZERO)){
            String msg = "Percent is 0 before discounting!";
            LOGGER.log(Level.WARN ,msg);
        //    throw new ServiceException(msg, null);
        }

        // base * discount / 100
        BigDecimal discountSum = baseCost.multiply(percent).divide(BigDecimal.valueOf(100));
        order.setCost(costValue.subtract(discountSum));
    }

    default BigDecimal getBaseCostChecked(Order order) throws ServiceException{
        BigDecimal baseCost = order.getBaseCost();

        if(!Optional.ofNullable(baseCost).isPresent() || baseCost.equals(BigDecimal.ZERO)){
            String msg = "Base cost is not counted before discounting!";
            LOGGER.log(Level.WARN ,msg);
            throw new ServiceException(msg, null);
        }
        return baseCost;
    }

    default BigDecimal getCostChecked(Order order) throws ServiceException{
        BigDecimal costValue = order.getBaseCost();

        if(!Optional.ofNullable(costValue).isPresent() || costValue.equals(BigDecimal.ZERO)){
            String msg = "Cost is not counted before discounting!";
            LOGGER.log(Level.WARN ,msg);
            throw new ServiceException(msg, null);
        }
        return costValue;
    }

}
