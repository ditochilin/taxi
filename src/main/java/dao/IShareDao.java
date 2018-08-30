package dao;

import dao.exceptions.DaoException;
import entities.Order;
import entities.Share;

import java.util.List;

public interface IShareDao extends IDao<Share> {

    List<Share> findSharesByOrder(Order Order) throws DaoException;

    List<Share> findByName(String name) throws DaoException;

    boolean findLoyalty() throws DaoException;
}
