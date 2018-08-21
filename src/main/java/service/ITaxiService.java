package service;

import entities.Taxi;

import java.util.List;

/**
 *  Service for working with taxi
 * @author Dmitry Tochilin
 */
public interface ITaxiService {

    List<Taxi> findFreeTaxis();

}
