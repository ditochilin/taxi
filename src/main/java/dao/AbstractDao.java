package dao;

import dao.enricher.IEnricher;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.extractor.IExtractor;
import dao.propSetter.IPropSetter;
import dao.transactionManager.TransactionManagerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> implements IDao<T> {

    private static final Logger LOGGER = LogManager.getLogger(TransactionManagerImpl.class.getName());

    protected Connection getConnection() throws DaoException, IllegalStateException {
        Connection connection = TransactionManagerImpl.getConnection();
        if (connection == null) {
            throw new IllegalStateException("Couldn't get jdbc connection");
        }
        return connection;
    }

    protected Connection getSerializableConnection() throws DaoException {
        try {
            Connection connection = getConnection();
            if (connection.getTransactionIsolation() != Connection.TRANSACTION_SERIALIZABLE) {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
            }
            return connection;
        } catch (SQLException e) {
            throw catchError("Can't create connection", "", e);
        }
    }

    protected T findById(String sql, String selectedField, Object value, IExtractor<T> extractor, IEnricher<T> enricher) throws DaoException, NoSuchEntityException {
        List<T> list = findBy(sql, selectedField, value, extractor, enricher);
        if (list.isEmpty()) {
            throw new NoSuchEntityException(String.format("Entity by id {%s} not found", value));
        }
        return list.get(0);
    }

    protected List<T> findBy(String sql, String selectionField, Object value, IExtractor<T> extractor, IEnricher<T> enricher) throws DaoException {
        List<T> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = createStatement(connection, sql, selectionField, value);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                T record = extractor.extractEntityData(resultSet);
                enricher.enrich(record, resultSet);
                result.add(record);
            }
        } catch (SQLException e) {
            throw catchError(String.format("Can't execute sql: %s {param=%s}", sql, value), sql, e);
        } catch (NoSuchEntityException e) {
            LOGGER.warn("Entity not found:" + e.getMessage());
        }
        return result;
    }

    protected Long insert(T entity, String sql, IPropSetter<T> propSetter) throws DaoException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            return insertHandler(entity, sql, statement, propSetter);
        } catch (SQLException e) {
            throw catchError("Can't execute sql:", sql, e);
        }
    }

    protected Long insertHandler(T entity, String sql, PreparedStatement statement, IPropSetter<T> propSetter) throws SQLException {
        propSetter.setProperties(statement, entity);
        statement.executeUpdate();
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw catchError("Can't get new id for inserted entity sql:", sql, e);
        }
    }

    protected T updateHandler(T entity, String sql, PreparedStatement statement, IPropSetter<T> propSetter) throws SQLException {
        propSetter.setProperties(statement, entity);
        statement.executeUpdate();
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            resultSet.next();
            return entity;
        } catch (SQLException e) {
            //  todo   ...
            throw catchError("Can't get id for updated entity sql:", sql, e);
        }
    }

    protected T update(T entity, String sql, IPropSetter<T> propSetter) throws DaoException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            return updateHandler(entity, sql, statement, propSetter);
        } catch (SQLException e) {
            throw catchError("Can't execute sql:", sql, e);
        }
    }

    protected boolean deleteById(Long id, String sql) throws DaoException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return deleteByIdHandler(id,statement);
        } catch (SQLException e) {
            throw catchError("Can't execute sql:", sql, e);
        }
    }

    protected boolean deleteByIdHandler(Long id, PreparedStatement statement) throws SQLException {
        statement.setLong(1, id);
        int result = statement.executeUpdate();
        return result > 0;
    }

    /**
     * Local method for setting up prepareStatement params
     *
     * @param connection - current connection instance
     * @param sqlIncome  - sql string
     * @param value      - value of field for selection
     * @return - prepareStatement
     * @throws SQLException
     */
    protected PreparedStatement createStatement(Connection connection, String sqlIncome, String selectedField, Object value) throws SQLException {
        StringBuilder sql = new StringBuilder(sqlIncome);
        if (selectedField != null) {
            sql.append(" WHERE " + selectedField + " = ?");
        }
        PreparedStatement statement = connection.prepareStatement(sql.toString());

        if (value == null) {
            return statement;
        }
        Class<?> typeOfValue = value.getClass();
        if (typeOfValue == String.class) {
            setStringProperty(statement, (String) value);
        } else if (typeOfValue == Long.class) {
            setLongProperty(statement, (Long) value);
        } else if (typeOfValue == Boolean.class) {
            setBooleanProperty(statement, (Boolean) value);
        }

        return statement;
    }

    private void setLongProperty(PreparedStatement statement, Long value) throws SQLException {
        statement.setLong(1, value);
    }

    private void setStringProperty(PreparedStatement statement, String value) throws SQLException {
        statement.setString(1, value);
    }

    private void setBooleanProperty(PreparedStatement statement, Boolean value) throws SQLException {
        statement.setBoolean(1, value);
    }

    /**
     * Method for catching, logging and trowing up error exceptions
     *
     * @param errorMessage
     * @param sql
     * @param e
     * @return
     */
    protected DaoException catchError(String errorMessage, String sql, Exception e) {
        String message = String.format("%s %s ;cause:{%s}",
                errorMessage,
                sql,
                e.getMessage());
        LOGGER.error(message);
        return new DaoException(message, e);
    }

}
