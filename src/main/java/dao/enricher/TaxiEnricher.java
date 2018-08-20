package dao.enricher;

import dao.ICarTypeDao;
import dao.IUserDao;
import dao.exceptions.NoSuchEntityException;
import entities.CarType;
import entities.Taxi;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxiEnricher implements IEnricher<Taxi> {

    private static ICarTypeDao carTypeDao;
    private static IUserDao userDao;

    public TaxiEnricher(IUserDao userDao, ICarTypeDao carTypeDao) {
        this.userDao = userDao;
        this.carTypeDao = carTypeDao;
    }

    @Override
    public void enrich(Taxi taxi, ResultSet resultSet) throws SQLException, NoSuchEntityException {
        if (taxi.getDriver() == null) {
            User user = userDao.findById(resultSet.getLong("id_user"));
            taxi.setDriver(user);
        }

        if (taxi.getCarType() == null) {
            CarType carType = carTypeDao.findById(resultSet.getLong("id_car_type"));
            taxi.setCarType(carType);
        }
    }
}
