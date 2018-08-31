package service;

import entities.Taxi;
import entities.User;
import service.exceptions.ServiceException;

import java.util.List;

/**
 * Service for working with taxi
 *
 * @author Dmitry Tochilin
 */
public interface ITaxiService extends IService<Taxi> {

    List<Taxi> getFreeTaxis() throws ServiceException;

    List<Taxi> getByDriver(User driver) throws ServiceException;

}
