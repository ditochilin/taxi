package service.implementation;

import dao.IOrderDao;
import dao.exceptions.DaoException;
import dao.implementation.OrderDaoImpl;
import entities.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IOrderService;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class.getName());
    private static OrderService instance;
    private static IOrderDao orderDao;

    private OrderService() {
        orderDao = OrderDaoImpl.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    @Override
    public List<Order> findOrders() {
        try {
            return orderDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Could not get all orders");
            return new ArrayList<>();
        }
    }

}
