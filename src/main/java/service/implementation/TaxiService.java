package service.implementation;

import dao.AbstractDao;
import dao.ITaxiDao;
import dao.exceptions.DaoException;
import dao.implementation.TaxiDaoImpl;
import entities.Taxi;
import entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AbstractService;
import service.ITaxiService;

import java.util.ArrayList;
import java.util.List;


public class TaxiService extends AbstractService<Taxi> implements ITaxiService {

    // todo : check if everywhere own class for class name is used
    private static final Logger LOGGER = LogManager.getLogger(TaxiService.class.getName());
    private static TaxiService instance;
    private static ITaxiDao taxiDao;

    public TaxiService() {
        dao = TaxiDaoImpl.getInstance();
        taxiDao = TaxiDaoImpl.getInstance();
    }

    public static TaxiService getInstance() {
        if (instance == null) {
            instance = new TaxiService();
        }
        return instance;
    }

    @Override
    public List<Taxi> getFreeTaxis() {
        try {
            return taxiDao.findAllBusyFree(false);
        } catch (DaoException e) {
            LOGGER.error("Could not get free taxis",e.getCause());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Taxi> getAll() {
        try {
            return taxiDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Could not get all taxis",e.getCause());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean update(Taxi entityDTO, Long id, StringBuilder msg) {
//todo
        return false;
    }

    @Override
    public Taxi getById(Long id) {
        return null;
    }

    @Override
    public void remove(Long id) throws Exception {
        try {
            taxiDao.delete(id);
        } catch (DaoException e) {
            LOGGER.error("Could not remove user.", e.getCause());
            throw new Exception(e);
        }
    }

    @Override
    public List<Taxi> getByDriver(User driver) {
        try {
            return taxiDao.findByUser(driver);
        } catch (DaoException e) {
            LOGGER.error("Could not get taxis by driver {"+driver+"}",e.getCause());
        }
        return new ArrayList<>();
    }
}
