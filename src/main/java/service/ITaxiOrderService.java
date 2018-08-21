package service;

import entities.TaxiOrder;

import java.util.List;

/**
 * Service for working with orders
 * @author Dmitry Tochilin
 */
public interface ITaxiOrderService {

    List<TaxiOrder> findTaxiOrders();

}
