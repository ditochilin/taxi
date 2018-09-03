package service.implementation;

import dao.IOrderDao;
import dao.exceptions.DaoException;
import dao.implementation.OrderDaoImpl;
import entities.Order;
import entities.Status;
import entities.Taxi;
import entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AbstractService;
import service.IOrderService;
import service.ITaxiService;
import service.businessLogic.BaseSystem;
import service.businessLogic.CounterMarketingService;
import service.businessLogic.marketingPrograms.CountingByDiscount;
import service.businessLogic.marketingPrograms.CountingByLoyalty;
import service.businessLogic.marketingPrograms.CountingByShare;
import service.businessLogic.marketingPrograms.IMarketingProgram;
import service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.*;

public class OrderService extends AbstractService<Order> implements IOrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class.getName());
    private static OrderService instance;
    private static IOrderDao orderDao;
    private static ITaxiService taxiService;

    private OrderService() {
        dao = OrderDaoImpl.getInstance();
        orderDao = OrderDaoImpl.getInstance();
        taxiService = TaxiService.getInstance();
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
    public boolean update(Order order, Long id, StringBuilder msg) throws ServiceException {

        defaultOperationsWithOrder(order);

        try {
            implementMarketingPrograms(order);
        }catch(ServiceException e){
            // log warn only
            LOGGER.warn("Could not implement shares to order :"+order);
        }

        updateEntity(order, id, msg);
        return true;
    }

    private static void defaultOperationsWithOrder(Order order) throws ServiceException {
        // count distance
        BaseSystem.countDistance(order);

        // count base cost of trip
        BaseSystem.countBaseCostOfTrip(order);

        // set default loyalty and share if not set
        BaseSystem.setDefaultShares(order);

        if (Status.CREATED.equals(order.getStatus()) && order.getTaxi() == null) {
            List<Taxi> taxis = taxiService.getFreeTaxis(ofNullable(order.getCarType()));
            if(taxis.size()>0) {
                Taxi taxi = taxis.get(0);

//                    .stream()
//                    .findFirst()
//                    .orElse(null);

                order.setTaxi(taxi);
                if (order.getTaxi() != null) {
                    order.setStatus(Status.INWORK);
                }
            }
        }

        // set time to wait
        if (order.getTaxi() != null) {
            BaseSystem.setTimeToWait(order);
        }
    }

    private static void implementMarketingPrograms(Order order) throws ServiceException {
        CounterMarketingService marketingService = CounterMarketingService.getInstance();

        //  discount
        doMarketingProgram(order, marketingService, CountingByDiscount.getInstance());

        // with loyalty
        doMarketingProgram(order, marketingService, CountingByLoyalty.getInstance());

        // with share
        doMarketingProgram(order, marketingService, CountingByShare.getInstance());
    }

    private static void doMarketingProgram(Order order, CounterMarketingService marketingService, IMarketingProgram program) throws ServiceException {
        marketingService.setMarketingProgram(program);
        marketingService.executeMarketingProgram(order);
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
