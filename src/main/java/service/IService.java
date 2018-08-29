package service;

import java.util.List;

/**
 * Holds all common operations for all services
 *
 * @author Dmitry Tochilin
 * @param <T>  - type of entity
 */
public interface IService<T> {

    List<T> getAll();

}
