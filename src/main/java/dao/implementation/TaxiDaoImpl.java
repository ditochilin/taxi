package dao.implementation;

import dao.AbstractDao;
import dao.ICarTypeDao;
import dao.ITaxiDao;
import dao.IUserDao;
import dao.enricher.IEnricher;
import dao.enricher.TaxiEnricher;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.extractor.IExtractor;
import dao.extractor.TaxiExtractor;
import dao.propSetter.IPropSetter;
import dao.propSetter.TaxiPropSetter;
import entities.Taxi;
import entities.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TaxiDaoImpl extends AbstractDao<Taxi> implements ITaxiDao {

    private static final Logger LOGGER = LogManager.getLogger(TaxiDaoImpl.class.getName());
    private static TaxiDaoImpl instance;
    private static IExtractor<Taxi> extractor;
    private static IPropSetter<Taxi> propSetter;
    private static IEnricher<Taxi> enricher;
    private static IUserDao userDao;
    private static ICarTypeDao carTypeDao;

    //SQL
    private static final String FIND_ALL = "SELECT car_name, car_number, busy, id_taxi, id_user, id_car_type FROM taxis";
    private static final String INSERT = "INSERT INTO taxis (car_name, car_number, busy, id_user, id_car_type) VALUES(?,?,?,?,?)";
    private static final String UPDATE = "UPDATE taxis SET car_name = ?, car_number= ?, busy = ?, id_user = ?, id_car_type = ? WHERE id_taxi = ?";
    private static final String DELETE = "DELETE FROM taxis WHERE id_taxi = ?";

    private TaxiDaoImpl() {
        userDao = UserDaoImpl.getInstance();
        carTypeDao = CarTypeDaoImpl.getInstance();
        extractor = new TaxiExtractor();
        propSetter = new TaxiPropSetter();
        enricher = new TaxiEnricher(userDao, carTypeDao);
    }

    public static TaxiDaoImpl getInstance() {
        if (instance == null) {
            instance = new TaxiDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Taxi> findAll() throws DaoException {
        return findBy(FIND_ALL, null, null, extractor, enricher);
    }

    @Override
    public Taxi findById(Long id) throws DaoException, NoSuchEntityException {
        return findById(FIND_ALL, "id_taxi", id, extractor, enricher);
    }

    @Override
    public List<Taxi> findAllBusyFree(boolean busy) throws DaoException {
        return findBy(FIND_ALL, "busy", busy, extractor, enricher);
    }

    @Override
    public void turnBusyness(Taxi taxi, boolean busy) throws DaoException {
        taxi.setBusy(busy);
        update(taxi);
    }

    @Override
    public List<Taxi> findByUser(User user) throws DaoException {
        return findBy(FIND_ALL, "id_user", user.getId(), extractor, enricher);
    }

    @Override
    public Long insert(Taxi newTaxi) throws DaoException {
        Long id = insert(newTaxi, INSERT, propSetter);
        newTaxi.setId(id);
        LOGGER.log(Level.INFO, "New taxi in database: " + newTaxi);
        return id;
    }

    @Override
    public Taxi update(Taxi taxi) throws DaoException {
        update(taxi, UPDATE, propSetter);
        LOGGER.log(Level.INFO, "Taxi has been changed: " + taxi);
        return taxi;
    }

    @Override
    public boolean delete(Taxi taxi) throws DaoException {
        boolean success = deleteById(taxi.getId(), DELETE);
        LOGGER.log(Level.INFO, "Taxi has been deleted: " + taxi);
        return success;
    }
}
