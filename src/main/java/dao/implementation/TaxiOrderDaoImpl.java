package dao.implementation;

import dao.*;
import dao.enricher.IEnricher;
import dao.enricher.TaxiOrderEnricher;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.extractor.IExtractor;
import dao.extractor.TaxiOrderExtractor;
import dao.propSetter.IPropSetter;
import dao.propSetter.TaxiOrderPropSetter;
import dao.transactionManager.ITransactionManager;
import dao.transactionManager.TransactionManagerImpl;
import entities.Share;
import entities.Taxi;
import entities.TaxiOrder;
import entities.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TaxiOrderDaoImpl extends AbstractDao<TaxiOrder> implements ITaxiOrderDao {

    private static final Logger LOGGER = LogManager.getLogger(TaxiOrderDaoImpl.class.getName());
    private static TaxiOrderDaoImpl instance;
    private static IExtractor<TaxiOrder> extractor;
    private static IPropSetter<TaxiOrder> propSetter;
    private static IEnricher<TaxiOrder> enricher;
    private static IUserDao userDao;
    private static ITaxiDao taxiDao;
    private static IShareDao shareDao;

    //SQL
    private static final String FIND_ALL = "SELECT order_date, start_point, end_point, distance, cost, feed_time, status_order, id_user, id_taxi, id_order FROM orders";
    private static final String FIND_BY_DRIVER = "SELECT order_date, start_point, end_point, distance, cost, feed_time, status_order, orders.id_user, orders.id_taxi, id_order FROM orders" +
            " INNER JOIN taxis ON taxis.id_taxi = orders.id_taxi AND taxis.id_user = ?";
    private static final String INSERT = "INSERT INTO orders (order_date, start_point, end_point, distance, cost, feed_time, status_order, id_user, id_taxi) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE orders SET order_date=?, start_point=?, end_point=?, distance=?, cost=?, feed_time=?, status_order=?, id_user=?, id_taxi=? WHERE id_order = ?";
    private static final String DELETE = "DELETE FROM orders WHERE id_order = ?";
    private static final String DELETE_SHARE = "DELETE FROM orders_shares";
    private static final String INSERT_SHARES = "INSERT INTO orders_shares (id_order, id_share) VALUES ";

    private TaxiOrderDaoImpl() {
        userDao = UserDaoImpl.getInstance();
        taxiDao = TaxiDaoImpl.getInstance();
        shareDao = ShareDaoImpl.getInstance();
        extractor = new TaxiOrderExtractor();
        propSetter = new TaxiOrderPropSetter();
        enricher = new TaxiOrderEnricher(userDao, taxiDao, shareDao);
    }

    public static TaxiOrderDaoImpl getInstance() {
        if (instance == null) {
            instance = new TaxiOrderDaoImpl();
        }
        return instance;
    }

    @Override
    public List<TaxiOrder> findAll() throws DaoException {
        return findBy(FIND_ALL, null, null, extractor, enricher);
    }

    @Override
    public TaxiOrder findById(Long id) throws DaoException, NoSuchEntityException {
        return findById(FIND_ALL, "id_order", id, extractor, enricher);
    }

    @Override
    public List<TaxiOrder> findByClient(User user) throws DaoException {
        return findBy(FIND_ALL, "id_user", user.getId(), extractor, enricher);
    }

    @Override
    public List<TaxiOrder> findByTaxi(Taxi taxi) throws DaoException {
        return findBy(FIND_ALL, "id_taxi", taxi.getId(), extractor, enricher);
    }

    @Override
    public Long insert(TaxiOrder newOrder) throws DaoException {
        ITransactionManager transactionManager = TransactionManagerImpl.getInstance();
        Long id = transactionManager.doInTransaction(() -> {
                    Long id_order = insertTransactional(newOrder);
                    newOrder.setId(id_order);
                    updateShares(newOrder);
                    return id_order;
                }
        );
        LOGGER.log(Level.INFO, "New order in database: " + newOrder);
        return id;
    }

    private void updateShares(TaxiOrder taxiOrder) throws DaoException {
        deleteShares(taxiOrder);
        insertShares(taxiOrder);
    }

    private Long insertTransactional(TaxiOrder taxiOrder) throws DaoException {
        Connection connection = getSerializableConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            return insertHandler(taxiOrder, INSERT, statement, propSetter);
        } catch (SQLException e) {
            throw catchError("Can't execute sql:", INSERT, e);
        }
    }

    @Override
    public TaxiOrder update(TaxiOrder taxiOrder) throws DaoException {
        ITransactionManager transactionManager = TransactionManagerImpl.getInstance();
        TaxiOrder newOrder = transactionManager.doInTransaction(() -> {
                    TaxiOrder updatedOrder = updateTransactional(taxiOrder);
                    updateShares(taxiOrder);
                    return updatedOrder;
                }
        );
        LOGGER.log(Level.INFO, "Updated order in database: " + taxiOrder);
        return newOrder;
    }

    private TaxiOrder updateTransactional(TaxiOrder taxiOrder) throws DaoException {
        Connection connection = getSerializableConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS)) {
            return updateHandler(taxiOrder, UPDATE, statement, propSetter);
        } catch (SQLException e) {
            throw catchError("Can't execute sql:", UPDATE, e);
        }
    }

    @Override
    public boolean delete(TaxiOrder taxiOrder) throws DaoException {
        ITransactionManager transactionManager = TransactionManagerImpl.getInstance();
        boolean success = transactionManager.doInTransaction(() -> {
                    deleteShares(taxiOrder);
                    boolean result = deleteTransactional(taxiOrder);
                    return result;
                }
        );
        LOGGER.log(Level.INFO, "Order has been deleted: " + taxiOrder);
        return success;
    }

    private boolean deleteTransactional(TaxiOrder taxiOrder) throws DaoException {
        Connection connection = getSerializableConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE, Statement.RETURN_GENERATED_KEYS)) {
            return deleteByIdHandler(taxiOrder.getId(),statement);
        } catch (SQLException e) {
            throw catchError("Can't execute sql:", DELETE, e);
        }
    }

    @Override
    public List<TaxiOrder> findByDriver(User user) throws DaoException {
        return findBy(FIND_BY_DRIVER, null, user.getId(), extractor, enricher);
    }


    /**
     * delete related with order rows in transit orders_shares table
     *
     * @param taxiOrder - order for clear
     */
    private void deleteShares(TaxiOrder taxiOrder) throws DaoException {
        Connection connection = getSerializableConnection();
        Long id = taxiOrder.getId();
        try (PreparedStatement statement =
                     createStatement(connection, DELETE_SHARE, "id_order", id)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Could not delete orders_shares for order " + id);
        }
    }

    /**
     * Add new shares into transit table
     *
     * @param taxiOrder
     */
    private void insertShares(TaxiOrder taxiOrder) throws DaoException {
        Connection connection = getSerializableConnection();
        Long id = taxiOrder.getId();
        try (PreparedStatement statement =
                     createStatementForInsertShares(connection, taxiOrder)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            String msg = "Could not insert orders_shares for order " + id;
            LOGGER.error(msg);
            throw new DaoException(msg, e);
        }
    }

    private PreparedStatement createStatementForInsertShares(Connection connection, TaxiOrder taxiOrder) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder(INSERT_SHARES);
        Queue<Share> shares = new LinkedList<>(taxiOrder.getShares());
        Long id_order = taxiOrder.getId();
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
