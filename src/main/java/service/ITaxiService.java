package service;

import entities.Taxi;
import entities.User;

import java.util.List;

/**
 * Service for working with taxi
 *
 * @author Dmitry Tochilin
 */
public interface ITaxiService extends IService<Taxi> {

    List<Taxi> getFreeTaxis();

    List<Taxi> getByDriver(User driver);

}
