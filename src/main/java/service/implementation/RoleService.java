package service.implementation;

import dao.IRoleDao;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.RoleDaoImpl;
import entities.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IRoleService;
import service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes main operations with roles
 *
 * @author Dmitry Tochilin
 */
public class RoleService implements IRoleService {

    private static final Logger LOGGER = LogManager.getLogger(RoleService.class.getName());
    private static IRoleDao roleDao;
    private static RoleService instance;

    private RoleService() {
        roleDao = RoleDaoImpl.getInstance();
    }

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService();
        }
        return instance;
    }

    public List<Role> getAll() {
        try {
            return roleDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Could not get all roles",e.getCause());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean update(Role entityDTO, Long id) {
        return false;
    }

    @Override
    public Role getByName(String roleName) {
        try {
            return roleDao.findByName(roleName);
        } catch (DaoException | NoSuchEntityException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
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
