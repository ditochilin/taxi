package dao;

import dao.enricher.IEnricher;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.extractor.IExtractor;
import dao.propSetter.IPropSetter;
import dao.transactionManager.TransactionManagerImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> implements IDao<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractDao.class.getName());

    protected Connection getConnection() throws DaoException, IllegalStateException {
        Connection connection = TransactionManagerImpl.getConnection();
        if (connection == null) {
            throw new IllegalStateException("Couldn't get jdbc connection");
        }
        return connection;
    }

    protected List<T> findBy(String sql, String selectedField, Object value, IExtractor<T> extractor, IEnricher<T> enricher) throws DaoException {
        List<T> list = findByInTransaction(sql, selectedField, value, extractor, enricher);
        if (list.isEmpty()) {
            LOGGER.warn(String.format("Entity by {%s} {%s} not found", selectedField, value));
        }
        return list;
    }

    protected List<T> findByInTransaction(String sql, String selectionField, Object value, IExtractor<T> extractor, IEnricher<T> enricher) throws DaoException {
        List<T> result = new ArrayList<>();
        try (PreparedStatement statement = createStatement(getConnection(), sql, selectionField, value);
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

    protected Long insertInTransaction(T entity, String sql, IPropSetter<T> propSetter) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            propSetter.setProperties(statement, entity);
            int effected = statement.executeUpdate();

            //todo : this realised in service...  leave?
            if(effected==0){
                return Long.valueOf(0);
            }
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            Long id = keys.getLong(1);
            try {
                entity.getClass().getMethod("setId", Long.class).invoke(entity,id);
                statement.setLong(1, id);
            }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
                LOGGER.error(String.format("Entity {%s} does not have setId()... Id didn't set",entity));
                return Long.valueOf(0);
            }
            LOGGER.log(Level.INFO, "New record of entity in database: " + entity);
            return id;
        } catch (SQLException e) {
            throw catchError("Can't execute sql:", sql, e);
        }
    }

    protected T updateInTransaction(T entity, String sql, IPropSetter<T> propSetter) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             ResultSet resultSet = statement.getGeneratedKeys()) {
            propSetter.setProperties(statement, entity);
            statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw catchError("Can't execute sql:", sql, e);
        }
    }

    protected boolean deleteInTransaction(Long id, String sql) throws DaoException {
        if(id == null){
            return false;
        }
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);
            boolean result = statement.executeUpdate() > 0;
            if(result){
                LOGGER.log(Level.INFO, String.format("Entity id=%s was deleted from db",id));
            }
            return result;
        } catch (SQLException e) {
            throw catchError("Can't execute sql:", sql, e);
        }
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
