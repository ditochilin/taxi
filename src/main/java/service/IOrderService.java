package service;

import entities.Order;
import entities.User;

import java.util.List;

/**
 * Service for working with orders
 *
 * @author Dmitry Tochilin
 */
public interface IOrderService extends IService<Order> {

    List<Order> getByClient(User user);

    List<Order> getByDriver(User driver);

    List<Order> getCreated();

}
