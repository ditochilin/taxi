package service.implementation;

import dao.ICarTypeDao;
import dao.exceptions.DaoException;
import dao.implementation.CarTypeDaoImpl;
import entities.CarType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ICarTypeService;

import java.util.ArrayList;
import java.util.List;

public class CarTypeService implements ICarTypeService {

    private static final Logger LOGGER = LogManager.getLogger(CarTypeService.class.getName());
    private static ICarTypeDao carTypeDao;
    private static CarTypeService instance;

    private CarTypeService() {
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
    public boolean update(CarType entityDTO, Long id) {
        return false;
    }
}
