package service.implementation;

import dao.ITaxiDao;
import dao.exceptions.DaoException;
import dao.implementation.TaxiDaoImpl;
import entities.Taxi;
import entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AbstractService;
import service.ITaxiService;
import service.exceptions.ServiceException;

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
    public List<Taxi> getFreeTaxis() throws ServiceException {
        try {
            return taxiDao.findAllBusyFree(false);
        } catch (DaoException e) {
            catchServiceException(e, "Could not get free taxis");
        }
        return new ArrayList<>();
    }

    @Override
    public List<Taxi> getAll() throws ServiceException {
        return getAllEntities();
    }

    @Override
    public boolean update(Taxi entityDTO, Long id, StringBuilder msg) throws ServiceException {
        updateEntity(entityDTO, id, msg);
        return true;
    }

    @Override
    public Taxi getById(Long id) throws ServiceException {
        return getEntityById(id);
    }

    @Override
    public void remove(Long id) throws ServiceException {
        removeEntity(id);
    }

    @Override
    public List<Taxi> getByDriver(User driver) throws ServiceException {
        try {
            return taxiDao.findByUser(driver);
        } catch (DaoException e) {
            catchServiceException(e, "Could not get taxis by driver {" + driver + "}");
        }
        return new ArrayList<>();
    }
}
