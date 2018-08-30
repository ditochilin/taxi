package dao;

import dao.exceptions.DaoException;
import entities.CarType;
import entities.Role;
import entities.User;

import java.util.List;

/**
 * Serve to extend IDao interface for User entity with additional behavior
 * For example, user may be found by role, phone
 * @author Dmitry Tochilin
 */
public interface IUserDao extends IDao<User> {

    List<User> findByRole(Role role) throws DaoException;

    List<User> findByPhone(String phone) throws DaoException;

    List<User> findByName(String userName) throws DaoException;
}
