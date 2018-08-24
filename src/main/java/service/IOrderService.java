package service;

import entities.Status;
import entities.Order;

import java.util.List;

/**
 * Service for working with orders
 * @author Dmitry Tochilin
 */
public interface IOrderService {

    List<Order> findOrders();


}
