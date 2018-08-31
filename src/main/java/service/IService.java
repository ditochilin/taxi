package service;

import service.exceptions.ServiceException;

import java.util.List;

/**
 * Holds all common operations for all services
 *
 * @param <T> - type of entity
 * @author Dmitry Tochilin
 */
public interface IService<T> {

    List<T> getAll() throws ServiceException;

    boolean update(T entityDTO, Long id, StringBuilder msg) throws ServiceException;

    T getById(Long id) throws ServiceException;

    void remove(Long id) throws ServiceException;
}
