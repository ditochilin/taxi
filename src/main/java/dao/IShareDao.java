package dao;

import dao.exceptions.DaoException;
import entities.Share;
import entities.Order;

import java.util.List;

public interface IShareDao extends IDao<Share> {

    List<Share> findSharesByOrder(Order Order) throws DaoException;

}
