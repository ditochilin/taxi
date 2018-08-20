package dao.implementation;

import dao.AbstractDao;
import dao.ICarTypeDao;
import dao.enricher.IEnricher;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.extractor.CarTypeExtractor;
import dao.extractor.IExtractor;
import dao.propSetter.CarTypePropSetter;
import dao.propSetter.IPropSetter;
import entities.CarType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CarTypeDaoImpl extends AbstractDao<CarType> implements ICarTypeDao {

    private static final Logger LOGGER = LogManager.getLogger(CarTypeDaoImpl.class.getName());
    private static CarTypeDaoImpl instance;
    private static IExtractor<CarType> extractor;
    private static IPropSetter<CarType> propSetter;

    //SQL
    private static final String FIND_ALL = "SELECT id_car_type, type_name, price FROM car_types";
    private static final String INSERT = "INSERT INTO car_types (type_name, price ) VALUES(?,?)";
    private static final String UPDATE = "UPDATE car_types SET type_name = ?, price = ? WHERE id_car_type = ?";
    private static final String DELETE = "DELETE FROM car_types WHERE id_car_type = ?";

    private CarTypeDaoImpl() {
        extractor = new CarTypeExtractor();
        propSetter = new CarTypePropSetter();
    }

    public static CarTypeDaoImpl getInstance() {
        if (instance == null) {
            instance = new CarTypeDaoImpl();
        }
        return instance;
    }

    @Override
    public CarType findById(Long id) throws DaoException, NoSuchEntityException {
        return findById(FIND_ALL, "id_car_type", id, extractor, IEnricher.NULL);
    }

    @Override
    public List<CarType> findAll() throws DaoException {
        return findBy(FIND_ALL, null, null, extractor, IEnricher.NULL);
    }

    @Override
    public List<CarType> findByName(String typeName) throws DaoException {
        return findBy(FIND_ALL, "type_name", typeName, extractor, IEnricher.NULL);
    }

    @Override
    public Long insert(CarType carType) throws DaoException {
        Long id = insert(carType, INSERT, propSetter);
        carType.setId(id);
        LOGGER.log(Level.INFO, "New record of entity in database: " + carType);
        return id;
    }

    @Override
    public CarType update(CarType newCarType) throws DaoException {
        update(newCarType, UPDATE, propSetter);
        LOGGER.log(Level.INFO, "CarType has been changed: " + newCarType);
        return newCarType;
    }

    @Override
    public boolean delete(CarType carType) throws DaoException {
        boolean success = deleteById(carType.getId(), DELETE);
        LOGGER.log(Level.INFO, "CarType has been deleted: " + carType);
        return success;
    }
}
