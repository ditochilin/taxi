package service.implementation;

import dao.IOrderDao;
import dao.exceptions.DaoException;
import dao.implementation.OrderDaoImpl;
import entities.Order;
import entities.Status;
import entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AbstractService;
import service.IOrderService;
import service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class OrderService extends AbstractService<Order> implements IOrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class.getName());
    private static OrderService instance;
    private static IOrderDao orderDao;

    private OrderService() {
        dao = OrderDaoImpl.getInstance();
        orderDao = OrderDaoImpl.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    @Override
    public List<Order> getAll() throws ServiceException {
        return getAllEntities();
    }

    @Override
    public boolean update(Order entityDTO, Long id, StringBuilder msg) throws ServiceException {
        updateEntity(entityDTO, id, msg);
        return true;
    }

    @Override
    public Order getById(Long id) throws ServiceException {
        return getEntityById(id);
    }

    @Override
    public void remove(Long id) throws ServiceException {
        removeEntity(id);
    }

    @Override
    public List<Order> getByClient(User client) {
        try {
            return orderDao.findByClient(client);
        } catch (DaoException e) {
            LOGGER.error("Could not get orders by client " + client, e.getCause());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Order> getByDriver(User driver) {
        try {
            return orderDao.findByDriver(driver);
        } catch (DaoException e) {
            LOGGER.error("Could not get orders by driver " + driver, e.getCause());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Order> getCreated() {
        try {
            return orderDao.findByStatus(Status.CREATED);
        } catch (DaoException e) {
            LOGGER.error("Could not get just created orders", e.getCause());
        }
        return new ArrayList<>();
    }

}
