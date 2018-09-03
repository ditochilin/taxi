package service.businessLogic;

import entities.Order;
import service.businessLogic.marketingPrograms.IMarketingProgram;
import service.exceptions.ServiceException;

/**
 * Pattern Strategy impl.
 */
public class CounterMarketingService {

    private static CounterMarketingService instance;
    private IMarketingProgram marketingProgram;

    public static CounterMarketingService getInstance() {
        if (instance == null) {
            instance = new CounterMarketingService();
        }
        return instance;
    }

    public void setMarketingProgram(IMarketingProgram marketingProgram) {
        this.marketingProgram = marketingProgram;
    }

    public void executeMarketingProgram(Order order) throws ServiceException {
        marketingProgram.countCost(order);
    }
}
