package dao.enricher;

import dao.exceptions.NoSuchEntityException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Enriches fields of entity with related entities
 * @param <T> gets enricher for concrete entity
 * @author Dmitry Tochilin
 */
public interface IEnricher<T> {

    IEnricher NULL = (record, resultSet) -> {
        /*NOP*/
    };

    void enrich(T record, ResultSet resultSet) throws SQLException, NoSuchEntityException;

}
