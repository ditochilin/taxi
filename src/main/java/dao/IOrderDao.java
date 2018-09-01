package dao;

import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import entities.*;

import java.util.List;

public interface IOrderDao extends IDao<Order> {

    List<Order> findByClient(User user) throws DaoException;

    List<Order> findByDriver(User user) throws DaoException;

    List<Order> findByTaxi(Taxi taxi) throws DaoException;

    List<Order> findByStatus(Status created) throws DaoException;

    List<Order> findByCarType(CarType carType) throws DaoException;
}
