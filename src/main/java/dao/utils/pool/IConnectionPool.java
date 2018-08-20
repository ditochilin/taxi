package dao.utils.pool;

import dao.exceptions.DaoException;

import java.sql.Connection;

/** Interface for implementation own connection pool.
 *  newConnection() return connection from pool or create new one if not pool is empty
 * @author Dmitry Tochilin
 */
public interface IConnectionPool {

    Connection newConnection() throws DaoException;

}
