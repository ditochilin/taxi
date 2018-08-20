package dao.utils.pool;

import dao.exceptions.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * It's an implementation of IConnectionPool interface.
 * This class realises own connection manager (pool)
 *
 * @author Dmitry Tochilin
 */
public class ConnectionPoolImpl implements IConnectionPool {

    /**
     * Thread safe stack of connections.
     */
    private static final LinkedBlockingQueue<Connection> connectionQueue = new LinkedBlockingQueue<>();

    /**
     * New connection getter for transaction
     *
     * @return connection
     * @throws DaoException
     */
    @Override
    public Connection newConnection() throws DaoException {
        return getConnection(ResourceBundle.getBundle("database"));
    }

    /**
     * gets actual connection of the pool. Overrides doClose() method.
     * When connection's Close() method will be called
     * first will be come back ino the pool and not closed
     *
     * @param resources
     * @return
     * @throws DaoException
     */
    private Connection getConnection(ResourceBundle resources) throws DaoException {
        String jdbcUrl = resources.getString("url");
        String login = resources.getString("user");
        String password = resources.getString("password");
        final Connection connection = getActualConnection(jdbcUrl, login, password);
        return new ConnectionWrapper(connection) {
            @Override
            public void doClose() throws SQLException {
                try {
                    connectionQueue.put(connection);
                } catch (Exception e) {
                    throw new DaoException("Could not return connection in the pool", e);
                }
            }
        };
    }

    /**
     * Gives connection from pool or created new one, pushed into the pool (connectionQueue)
     *
     * @param jdbcUrl  - url of database
     * @param login    - user's login
     * @param password - user's password
     * @return connection for wrapping
     * @throws DaoException
     */
    private Connection getActualConnection(String jdbcUrl, String login, String password) throws DaoException {
        Connection connection = connectionQueue.poll();
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(jdbcUrl, login, password);
//                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new DaoException("Could not get connection from driver for : " + jdbcUrl, e);
            }
        }
        return connection;
    }
}
