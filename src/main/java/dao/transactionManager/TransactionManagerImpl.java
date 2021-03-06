package dao.transactionManager;

import dao.exceptions.DaoException;
import dao.utils.JdbcUtils;
import dao.utils.pool.ConnectionPoolImpl;
import dao.utils.pool.IConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * Class is for transaction providing.
 * connectionHolder is a local variable for holding connection in current thread (one in thread scope)
 * CONNECTION_FACTORY - connection pool variable
 * doInTransaction - excepts method for delayed call in transaction
 *
 * @author Dmitry Tochilin
 */
public class TransactionManagerImpl {

    private static TransactionManagerImpl instance;
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private static ThreadLocal<Integer> depth = new ThreadLocal<>();
    private static final IConnectionPool CONNECTION_POOL = new ConnectionPoolImpl();
    private static final Logger LOGGER = LogManager.getLogger(TransactionManagerImpl.class.getName());

    private TransactionManagerImpl() {
    }

    public static TransactionManagerImpl getInstance() {
        if (instance == null) {
            instance = new TransactionManagerImpl();
        }
        return instance;
    }

    private static void incDepth(ThreadLocal<Integer> depth){
        Integer prevDepth = depth.get();
        depth.set((prevDepth==null?0:prevDepth)+1);
    }

    private static void decDepth(ThreadLocal<Integer> depth){
        Integer prevDepth = depth.get();
        depth.set((prevDepth==null?0:prevDepth)-1);
    }

    /**
     * @param unitOfWork a transmitted method
     * @return result of transmitted method
     * @throws Exception if transmitted method throws exception, transaction must be rollback and exception will
     *                   be rethrown in execution stack
     */
    public static <T> T doInTransaction(Callable<T> unitOfWork) throws DaoException {
        Connection connection = getConnection();
        incDepth(depth);
        LOGGER.debug(String.format("Get connection: %s from pool and set to Thread: %s", connection, Thread.currentThread()));
        try {
            T result = unitOfWork.call();
            LOGGER.debug(String.format("Do unit of work. result: %s", unitOfWork));
            decDepth(depth);
            if(depth.get()==0) {
                connection.commit();
                LOGGER.debug("Commit success.");
            }
            return result;
        } catch (Exception e) {
            try {
                connection.rollback();
                depth.set(0);  // if rollback, then reset connection!
                LOGGER.warn("Rollback success.",e.getCause());
            } catch (SQLException e1) {
                LOGGER.error("Could not rollback transaction. "+e1.getMessage());
            }
            throw new DaoException("rollback transaction during " + unitOfWork, e);
        } finally {
            if(depth.get()==0) {
                JdbcUtils.closeQuietly(connection);
                connectionHolder.remove();
                LOGGER.debug(String.format("Close connection success. Unset connection %s from Thread: %s", connection, Thread.currentThread()));
            }
        }
    }

    /**
     * gets connection and place it in connectionHolder for transaction
     * or gets it from connection pool
     * @return connection from connection pool for current tread
     */
    public static Connection getConnection() throws DaoException {
        Connection connection = connectionHolder.get();
        if (connection == null){
            connection = CONNECTION_POOL.newConnection();
            connectionHolder.set(connection);
        }
        return connection;
    }
}
