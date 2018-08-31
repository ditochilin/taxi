package service.implementation;

import dao.ICarTypeDao;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.CarTypeDaoImpl;
import entities.CarType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AbstractService;
import service.ICarTypeService;

import java.util.ArrayList;
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
    public List<CarType> getAll() {
        try {
            return carTypeDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Could not get all car types", e.getCause());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean update(CarType entityDTO, Long id, StringBuilder msg) throws Exception {
        try {
            updateEntity(entityDTO, id, msg);
            return true;
        } catch (Exception e) {
            LOGGER.error("Could not update/insert car type.", e.getCause());
            throw e;
        }
    }

    @Override
    public CarType getById(Long id) {
        try {
            return carTypeDao.findById(id);
        } catch (DaoException | NoSuchEntityException e) {
            LOGGER.error("Could not get all car types by id: "+id, e.getCause());
        }
        return null;
    }

    @Override
    public void remove(Long id) throws Exception {
        try {
            carTypeDao.delete(id);
        } catch (DaoException e) {
            LOGGER.error("Could not remove user.", e.getCause());
            throw new Exception(e);
        }
    }

    @Override
    public boolean suchCarTypeIsPresent(String typeName) {
        try {
            return !carTypeDao.findByName(typeName).isEmpty();
        }catch (Exception e){
            LOGGER.error(e.getCause());
        }
        return false;
    }
}
