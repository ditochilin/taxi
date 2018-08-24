package dao;

import dao.exceptions.DaoException;
import entities.Taxi;
import entities.User;

import java.util.List;

/**
 * Serve to extend IDao interface for Taxi entity with additional behavior
 * For example, taxi may be found by driver (user), found busy/free taxi...
 * @author Dmitry Tochilin
 */
public interface ITaxiDao extends IDao<Taxi> {

    List<Taxi> findByUser(User user) throws DaoException;

    List<Taxi> findAllBusyFree(boolean busy) throws DaoException;

    Taxi turnBusynessInTransaction(Taxi taxi, boolean busy) throws DaoException;

    Taxi turnBusyness(Taxi taxi, boolean busy) throws DaoException;

}
