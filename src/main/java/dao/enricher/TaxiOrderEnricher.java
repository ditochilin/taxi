package dao.enricher;

import dao.IShareDao;
import dao.ITaxiDao;
import dao.IUserDao;
import dao.exceptions.NoSuchEntityException;
import entities.Share;
import entities.Taxi;
import entities.TaxiOrder;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TaxiOrderEnricher implements IEnricher<TaxiOrder> {

    private static IUserDao userDao;
    private static ITaxiDao taxiDao;
    private static IShareDao shareDao;

    public TaxiOrderEnricher(IUserDao userDao, ITaxiDao taxiDao, IShareDao shareDao) {
        this.userDao = userDao;
        this.taxiDao = taxiDao;
        this.shareDao = shareDao;
    }

    @Override
    public void enrich(TaxiOrder taxiOrder, ResultSet resultSet) throws SQLException, NoSuchEntityException {
        if (taxiOrder.getUser() == null) {
            User user = userDao.findById(resultSet.getLong("id_user"));
            taxiOrder.setUser(user);
        }

        if (taxiOrder.getTaxi() == null) {
            Taxi taxi = taxiDao.findById(resultSet.getLong("id_taxi"));
            taxiOrder.setTaxi(taxi);
        }

        if (taxiOrder.getShares() == null) {
            List<Share> shares = shareDao.findSharesByOrder(taxiOrder);
            taxiOrder.setShares(shares);
        }
    }
}
