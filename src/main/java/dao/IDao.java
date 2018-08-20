package dao;

import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;

import java.util.List;

/**
 * Describes common CRUD operations with entities
 *
 * @param <T> - type of implemented DAO
 * @author Dmitry Tochilin
 */
public interface IDao<T> {

    T findById(Long id) throws DaoException, NoSuchEntityException;

    List<T> findAll() throws DaoException;

    Long insert(T newEntity) throws DaoException;

    T update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;
}
