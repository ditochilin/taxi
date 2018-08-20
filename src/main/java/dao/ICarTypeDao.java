package dao;

import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import entities.CarType;

import java.util.List;

/**
 * Serve to extend IDao interface for carType entity with additional behavior
 * For example, carType may be found by name
 * @author Dmitry Tochilin
 */
public interface ICarTypeDao extends IDao<CarType> {

    List<CarType> findByName(String carTypeName) throws DaoException, NoSuchEntityException;

}
