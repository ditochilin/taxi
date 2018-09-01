package dao.propSetter;

import dao.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Sets properties of prepared statement with entity's fields values
 *
 * @param <T> gets property setter of concrete entity
 * @author Dmitry Tochilin
 */
public interface IPropSetter<T> {

    void setProperties(PreparedStatement statement, T entity) throws DaoException;

    default void setIdIfNotNull(PreparedStatement statement, int parameterIndex, Long id) throws SQLException {
        if (id != null && id != 0) {
            statement.setLong(parameterIndex, id);
        }
    }

    default void setValueOrNull(PreparedStatement statement, int parameterIndex, Number id) throws SQLException {
        if (id != null) {
            statement.setLong(parameterIndex, id.longValue());
        } else {
            statement.setNull(parameterIndex, Types.BIGINT);
        }
    }
}
