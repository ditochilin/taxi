package dao.enricher;

import dao.IShareDao;
import dao.ITaxiDao;
import dao.IUserDao;
import dao.exceptions.NoSuchEntityException;
import entities.Share;
import entities.Taxi;
import entities.Order;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderEnricher implements IEnricher<Order> {

    private static IUserDao userDao;
    private static ITaxiDao taxiDao;
    private static IShareDao shareDao;

    public OrderEnricher(IUserDao userDao, ITaxiDao taxiDao, IShareDao shareDao) {
        OrderEnricher.userDao = userDao;
        OrderEnricher.taxiDao = taxiDao;
        OrderEnricher.shareDao = shareDao;
    }

    @Override
    public void enrich(Order Order, ResultSet resultSet) throws SQLException, NoSuchEntityException {
        if (Order.getClient() == null) {
            User user = userDao.findById(resultSet.getLong("id_user"));
            Order.setClient(user);
        }

        if (Order.getTaxi() == null) {
            Taxi taxi = taxiDao.findById(resultSet.getLong("id_taxi"));
            Order.setTaxi(taxi);
        }

        if (Order.getShares() == null) {
            List<Share> shares = shareDao.findSharesByOrder(Order);
            Order.setShares(shares);
        }
    }
}
