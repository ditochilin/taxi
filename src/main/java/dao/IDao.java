package dao;

import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import entities.CarType;

import java.util.List;

/**
 * Describes common CRUD operations with entities
 *
 * @param <T> - type of implemented DAO
 * @author Dmitry Tochilin
 */
public interface IDao<T> {

    List<T> findAll() throws DaoException;

    T findById(Long id) throws DaoException, NoSuchEntityException;

    Long insert(T newEntity) throws DaoException;

    T update(T entity) throws DaoException;

    boolean delete(Long id) throws DaoException;
}
