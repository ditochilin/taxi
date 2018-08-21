package service.implementation;

import dao.ITaxiDao;
import dao.exceptions.DaoException;
import dao.implementation.TaxiDaoImpl;
import entities.Taxi;
import service.ITaxiService;

import java.util.ArrayList;
import java.util.List;

public class TaxiService implements ITaxiService {

    private static TaxiService instance;
    private static ITaxiDao taxiDao = TaxiDaoImpl.getInstance();

    public static TaxiService getInstance() {
        if (instance == null) {
            instance = new TaxiService();
        }
        return instance;
    }

    @Override
    public List<Taxi> findFreeTaxis() {
        try {
            return taxiDao.findAllBusyFree(false);
        } catch (DaoException e) {
            return new ArrayList<>();
        }
    }
}
