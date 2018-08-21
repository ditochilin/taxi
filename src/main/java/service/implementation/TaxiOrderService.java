package service.implementation;

import dao.ITaxiOrderDao;
import dao.exceptions.DaoException;
import dao.implementation.TaxiOrderDaoImpl;
import entities.Status;
import entities.TaxiOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ITaxiOrderService;

import java.util.ArrayList;
import java.util.List;

public class TaxiOrderService implements ITaxiOrderService {

    private static final Logger LOGGER = LogManager.getLogger(TaxiOrderService.class.getName());
    private static TaxiOrderService instance;
    private static ITaxiOrderDao taxiOrderDao;

    private TaxiOrderService() {
        taxiOrderDao = TaxiOrderDaoImpl.getInstance();
    }

    public static TaxiOrderService getInstance() {
        if (instance == null) {
            instance = new TaxiOrderService();
        }
        return instance;
    }

    @Override
    public List<TaxiOrder> findTaxiOrders() {
        try {
            return taxiOrderDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Could not get all orders");
            return new ArrayList<>();
        }
    }

}
