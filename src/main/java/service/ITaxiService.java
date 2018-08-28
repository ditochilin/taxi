package service;

import entities.Taxi;
import entities.User;

import java.util.List;

/**
 * Service for working with taxi
 *
 * @author Dmitry Tochilin
 */
public interface ITaxiService {

    List<Taxi> findFreeTaxis();

    List<Taxi> findAll();

    List<Taxi> findByDriver(User driver);

}
