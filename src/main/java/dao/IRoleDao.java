package dao;

import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import entities.Role;

/**
 * Serve to extend IDao interface for Role entity with additional behavior
 * For example, role may be found by name
 * @author Dmitry Tochilin
 */
public interface IRoleDao extends IDao<Role> {

    Role findByName(String roleName) throws DaoException, NoSuchEntityException;

}
