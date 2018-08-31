package service;

import entities.Order;
import entities.User;
import service.exceptions.ServiceException;

import java.util.List;

/**
 * Service for working with orders
 *
 * @author Dmitry Tochilin
 */
public interface IOrderService extends IService<Order> {

    List<Order> getByClient(User user) throws ServiceException;

    List<Order> getByDriver(User driver) throws ServiceException;

    List<Order> getCreated() throws ServiceException;

}
