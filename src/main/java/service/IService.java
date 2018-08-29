package service;

import entities.User;

import java.util.List;

/**
 * Holds all common operations for all services
 *
 * @author Dmitry Tochilin
 * @param <T>  - type of entity
 */
public interface IService<T> {

    List<T> getAll();

    boolean update(T entityDTO, Long id);
}
