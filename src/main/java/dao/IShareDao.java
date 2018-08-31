package dao;

import dao.exceptions.DaoException;
import entities.Order;
import entities.Share;

import java.util.List;

/**
 * describes additional methods for shareDao
 */
public interface IShareDao extends IDao<Share> {

    boolean ifLoyaltyIsPresent(String shareName) throws DaoException;

    List<Share> findSharesByOrder(Order Order) throws DaoException;

    List<Share> findByName(String name) throws DaoException;

}
