package dao.enricher;

import dao.ICarTypeDao;
import dao.IShareDao;
import dao.ITaxiDao;
import dao.IUserDao;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.OrderDaoImpl;
import entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderEnricher implements IEnricher<Order> {

    private static IUserDao userDao;
    private static ITaxiDao taxiDao;
    private static IShareDao shareDao;
    private static ICarTypeDao carTypesDao;

    public OrderEnricher(IUserDao userDao, ITaxiDao taxiDao, IShareDao shareDao, ICarTypeDao carTypesDao) {
        OrderEnricher.userDao = userDao;
        OrderEnricher.taxiDao = taxiDao;
        OrderEnricher.shareDao = shareDao;
        OrderEnricher.carTypesDao = carTypesDao;
    }

    @Override
    public void enrich(Order order, ResultSet resultSet) throws SQLException, NoSuchEntityException {
        Long idUser = resultSet.getLong("id_user");
        if (order.getClient() == null && idUser != 0) {
            User user = userDao.findById(idUser);
            order.setClient(user);
        }

        Long idTaxi = resultSet.getLong("id_taxi");
        if (order.getTaxi() == null && idTaxi != 0) {
            Taxi taxi = taxiDao.findById(idTaxi);
            order.setTaxi(taxi);
        }

        if (order.getShares().isEmpty()) {
            List<Share> shares = shareDao.findSharesByOrder(order);
            order.setShares(shares);
        }

        Long idCarType = resultSet.getLong("id_car_type");
        if (order.getCarType()==null && idCarType!=0) {
            CarType carType = carTypesDao.findById(idCarType);
            order.setCarType(carType);
        }
    }
}
