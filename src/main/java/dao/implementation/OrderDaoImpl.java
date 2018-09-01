package dao.implementation;

import dao.AbstractDao;
import dao.IOrderDao;
import dao.ITaxiDao;
import dao.enricher.IEnricher;
import dao.enricher.OrderEnricher;
import dao.exceptions.DaoException;
import dao.extractor.IExtractor;
import dao.extractor.OrderExtractor;
import dao.propSetter.IPropSetter;
import dao.propSetter.OrderPropSetter;
import dao.transactionManager.TransactionManagerImpl;
import entities.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrderDaoImpl extends AbstractDao<Order> implements IOrderDao {

    private static final Logger LOGGER = LogManager.getLogger(OrderDaoImpl.class.getName());
    private static OrderDaoImpl instance;
    private static IExtractor<Order> extractor;
    private static IPropSetter<Order> propSetter;
    private static IEnricher<Order> enricher;
    private static ITaxiDao taxiDao;

    //SQL
    private static final String FIND_ALL = "SELECT order_date, start_point, end_point, distance, cost, feed_time, status_order, waiting_time, discount, id_user, id_taxi, id_car_type, id_order FROM orders";
    private static final String FIND_BY_DRIVER = "SELECT order_date, start_point, end_point, distance, cost, feed_time, status_order, waiting_time, discount, orders.id_user, orders.id_taxi, orders.id_car_type, id_order FROM orders" +
            " INNER JOIN taxis ON taxis.id_taxi = orders.id_taxi AND taxis.id_user = ?";
    private static final String INSERT = "INSERT INTO orders (order_date, start_point, end_point, distance, cost, feed_time, status_order, waiting_time, discount, id_user, id_taxi, id_car_type) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE orders SET order_date=?, start_point=?, end_point=?, distance=?, cost=?, feed_time=?, status_order=?, waiting_time=?, discount=?, id_user=?, id_taxi=?, id_car_type=? WHERE id_order = ?";
    private static final String DELETE = "DELETE FROM orders WHERE id_order = ?";
    private static final String DELETE_TRANSIT_SHAREORDER = "DELETE FROM orders_shares";
    private static final String INSERT_TRANSIT_SHARES = "INSERT INTO orders_shares (id_order, id_share) VALUES ";

    private OrderDaoImpl() {
        taxiDao = TaxiDaoImpl.getInstance();
        extractor = new OrderExtractor();
        propSetter = new OrderPropSetter();
        enricher = new OrderEnricher(UserDaoImpl.getInstance(),
                taxiDao,
                ShareDaoImpl.getInstance(),
                CarTypeDaoImpl.getInstance());
    }

    public static OrderDaoImpl getInstance() {
        if (instance == null) {
            instance = new OrderDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->
                findByInTransaction(FIND_ALL, null, null, extractor, enricher));
    }

    @Override
    public Order findById(Long id) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() -> {
            List<Order> orders = findBy(FIND_ALL, "id_order", id, extractor, enricher);
            if (orders.isEmpty()) {
                return null;
            }
            return orders.get(0);
        });
    }

    @Override
    public List<Order> findByClient(User user) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->
                findByInTransaction(FIND_ALL, "id_user", user.getId(), extractor, enricher));
    }

    @Override
    public List<Order> findByTaxi(Taxi taxi) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->
                findByInTransaction(FIND_ALL, "id_taxi", taxi.getId(), extractor, enricher));
    }

    @Override
    public List<Order> findByStatus(Status status) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->
                findByInTransaction(FIND_ALL, "status_order", status.name(), extractor, enricher));
    }

    @Override
    public List<Order> findByCarType(CarType carType) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->
                findByInTransaction(FIND_ALL, "id_car_type", carType.getId(), extractor, enricher));
    }

    @Override
    public List<Order> findByDriver(User user) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->
                findByInTransaction(FIND_BY_DRIVER, null, user.getId(), extractor, enricher));
    }

    @Override
    public Long insert(Order order) throws DaoException {
        Long id = TransactionManagerImpl.doInTransaction(() -> {
                    checkOrderBeforeInsert(order);
                    Taxi taxi = order.getTaxi();
                    Long id_order = insertInTransaction(order, INSERT, propSetter);
                    if (!order.getShares().isEmpty()) {
                        updateShares(order);
                    }
                    if (taxi != null) {
                        taxiDao.turnBusynessInTransaction(taxi, order.getStatus().equals(Status.INWORK));
                    }
                    return id_order;
                }
        );
        LOGGER.log(Level.INFO, "New order in database: " + order);
        return id;
    }

    private void checkOrderBeforeInsert(Order order) {
        if (order.getDateTime() == null) {
            order.setDateTime(Calendar.getInstance().getTime());
        }
    }

    /**
     * A Logic of dependency taxi (business) and orders (INWORK/not in work) is a Dao-level logic
     * So it should be changed taxi table as well
     *
     * @param order
     * @return
     * @throws DaoException
     */
    @Override
    public Order update(Order order) throws DaoException {
        Order newOrder = TransactionManagerImpl.doInTransaction(() -> {
            Taxi taxi = order.getTaxi();
            Order updatedOrder = updateInTransaction(order, UPDATE, propSetter);
            if (!order.getShares().isEmpty()) {
                updateShares(order);
            }
            if (taxi != null) {
                taxiDao.turnBusynessInTransaction(taxi, Status.INWORK.equals(order.getStatus()));
            }
            return updatedOrder;
        });
        LOGGER.log(Level.INFO, "Updated order in database: " + order);
        return newOrder;
    }

    private void updateShares(Order order) throws DaoException {
        deleteShares(order.getId());
        insertShares(order);
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        boolean success = TransactionManagerImpl.doInTransaction(() -> {
            Order order = findById(id);
            taxiDao.turnBusynessInTransaction(order.getTaxi(), false);
            deleteShares(id);
            return deleteInTransaction(id, DELETE);
        });
        LOGGER.log(Level.INFO, "Order has been deleted, id: " + id);
        return success;
    }

    /**
     * delete related with order rows in transit orders_shares table
     *
     * @param id - id of order for clear
     */
    private void deleteShares(Long id) throws DaoException {
        if (id == null) {
            return;
        }
        try (PreparedStatement statement =
                     createStatement(getConnection(), DELETE_TRANSIT_SHAREORDER, "id_order", id)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw catchError("Could not delete orders_shares for order " + id, DELETE_TRANSIT_SHAREORDER, e);
        }
    }

    /**
     * Add new shares into transit table
     *
     * @param order
     */
    private void insertShares(Order order) throws DaoException {
        try (PreparedStatement statement =
                     createStatementForInsertShares(getConnection(), order)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw catchError("Could not insert orders_shares for order " + order, INSERT_TRANSIT_SHARES, e);
        }
    }

    private PreparedStatement createStatementForInsertShares(Connection connection, Order order) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder(INSERT_TRANSIT_SHARES);
        Queue<Share> shares = new LinkedList<>(order.getShares());
        Long id_order = order.getId();
        boolean sharesIsEmpty = shares.isEmpty();
        while (!sharesIsEmpty) {
            Share share = shares.poll();
            sqlBuilder.append(" (" + id_order + "," + share.getId() + ")");
            sharesIsEmpty = shares.isEmpty();
            if (!sharesIsEmpty) {
                sqlBuilder.append(",");
            }
        }
        PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());

        return statement;
    }

}
