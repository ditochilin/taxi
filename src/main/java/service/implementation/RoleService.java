package service.implementation;

import dao.IRoleDao;
import dao.exceptions.DaoException;
import entities.Role;
import service.IRoleService;
import service.exceptions.ServiceException;

import java.util.List;

/**
 * Describes main operations with roles
 *
 * @author Dmitry Tochilin
 */
public class RoleService implements IRoleService {

    private static IRoleDao roleDao;
    private static RoleService instance;

    private RoleService() {
    }

    public RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService();
        }
        return instance;
    }

    public List<Role> findAll() throws ServiceException {
        try {
            return roleDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Could not get all roles", e);
        }
    }

    public Role create(String name, String description) throws ServiceException {
        Role role = new Role();
        role.setRoleName(name);
        role.setDescription(description);
        try {
            roleDao.insert(role);
        } catch (DaoException e) {
            throw new ServiceException("Could not insert new role", e);
        }
        return role;
    }
}
