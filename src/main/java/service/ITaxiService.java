package service;

import entities.CarType;
import entities.Taxi;
import entities.User;
import service.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Service for working with taxi
 *
 * @author Dmitry Tochilin
 */
public interface ITaxiService extends IService<Taxi> {

    List<Taxi> getFreeTaxis(Optional<CarType> carType) throws ServiceException;

    List<Taxi> getByDriver(User driver) throws ServiceException;

}
