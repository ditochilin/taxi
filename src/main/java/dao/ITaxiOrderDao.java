package dao;

import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import entities.Taxi;
import entities.TaxiOrder;
import entities.User;

import java.util.List;

public interface ITaxiOrderDao extends IDao<TaxiOrder> {

    List<TaxiOrder> findByClient(User user) throws DaoException, NoSuchEntityException;

    List<TaxiOrder> findByDriver(User user) throws DaoException;

    List<TaxiOrder> findByTaxi(Taxi taxi) throws DaoException;

}
