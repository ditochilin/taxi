package service.implementation;

import dao.ICarTypeDao;
import dao.implementation.CarTypeDaoImpl;
import entities.CarType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AbstractService;
import service.ICarTypeService;
import service.exceptions.ServiceException;

import java.util.List;

public class CarTypeService extends AbstractService<CarType> implements ICarTypeService {

    private static final Logger LOGGER = LogManager.getLogger(CarTypeService.class.getName());
    private static ICarTypeDao carTypeDao;
    private static CarTypeService instance;

    private CarTypeService() {
        dao = CarTypeDaoImpl.getInstance();
        carTypeDao = CarTypeDaoImpl.getInstance();
    }

    public static CarTypeService getInstance() {
        if (instance == null) {
            instance = new CarTypeService();
        }
        return instance;
    }

    @Override
    public List<CarType> getAll() throws ServiceException {
        return getAllEntities();
    }

    @Override
    public boolean update(CarType entityDTO, Long id, StringBuilder msg) throws ServiceException {
        updateEntity(entityDTO, id, msg);
        return true;
    }

    @Override
    public CarType getById(Long id) throws ServiceException {
        return getEntityById(id);
    }

    @Override
    public void remove(Long id) throws ServiceException {
        removeEntity(id);
    }

    @Override
    public boolean suchCarTypeIsPresent(String typeName) throws ServiceException {
        try {
            return !carTypeDao.findByName(typeName).isEmpty();
        } catch (Exception e) {
            catchServiceException(e, "Could not find car type by name:  " + typeName);
        }
        return false;
    }
}
