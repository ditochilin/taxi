package service;

import entities.Order;
import entities.User;

import java.util.List;

/**
 * Service for working with orders
 *
 * @author Dmitry Tochilin
 */
public interface IOrderService {

    List<Order> findAll();

    List<Order> findByClient(User user);

    List<Order> findByDriver(User driver);

    List<Order> findCreated();


}
