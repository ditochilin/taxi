import dao.IOrderDao;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.OrderDaoImpl;
import entities.Order;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import service.IOrderService;
import service.businessLogic.BaseSystem;
import service.exceptions.ServiceException;
import service.implementation.OrderService;

import java.math.BigDecimal;

public class OrderCheckTest {

    private static final Logger LOGGER = LogManager.getLogger(OrderCheckTest.class.getName());
    private static IOrderService orderService = OrderService.getInstance();
    private static IOrderDao orderDao = OrderDaoImpl.getInstance();

    @Test
    public void testDistanceCounting() {
        try {
            Order order = orderService.getAll().stream().filter(o -> o.getDistance() == 0).findFirst().get();

            Integer d = order.getDistance();
            Assert.assertEquals(java.util.Optional.of(0), java.util.Optional.ofNullable(d));
            Long id = order.getId();
            BaseSystem.countDistance(order);

            BaseSystem.countBaseCostOfTrip(order);
            orderDao.findById(id);
            LOGGER.log(Level.INFO, order.getDistance());
            Assert.assertTrue(order.getDistance() > 0);
            BigDecimal cost = order.getCost();
            LOGGER.log(Level.INFO, cost);
            Assert.assertTrue(cost.compareTo(BigDecimal.valueOf(0))==1 );

            // *** when base cost presents, lets count it  with discount, loyalty, share

//            // 1. with discount
//            BaseSystem.countCostWithDiscount(order);
//
//            // 2. with loyalty
//            BaseSystem.countCostWithLoyalty(order);
//
//            // 3. with share
//            BaseSystem.countCostWithShare(order);

        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
        } catch (DaoException | NoSuchEntityException e) {
            e.printStackTrace();
        }

    }

}
