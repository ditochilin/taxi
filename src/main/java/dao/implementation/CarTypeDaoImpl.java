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
import dao.transactionManager.TransactionManagerImpl;
import entities.CarType;
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
    public CarType findById(Long id) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->
                findById(FIND_ALL, "id_car_type", id, extractor, IEnricher.NULL));
    }

    @Override
    public List<CarType> findAll() throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->
                findByInTransaction(FIND_ALL, null, null, extractor, IEnricher.NULL));
    }

    @Override
    public List<CarType> findByName(String typeName) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->
                findByInTransaction(FIND_ALL, "type_name", typeName, extractor, IEnricher.NULL));
    }

    @Override
    public Long insert(CarType carType) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() -> insertInTransaction(carType, INSERT, propSetter));
    }

    @Override
    public CarType update(CarType newCarType) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() -> updateInTransaction(newCarType, UPDATE, propSetter));
    }

    @Override
    public boolean delete(CarType carType) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() -> deleteInTransaction(carType, DELETE));
    }
}
