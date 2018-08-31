package service.implementation;

import dao.IRoleDao;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.RoleDaoImpl;
import entities.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AbstractService;
import service.IRoleService;
import service.exceptions.ServiceException;

import java.util.List;

/**
 * Describes main operations with roles
 *
 * @author Dmitry Tochilin
 */
public class RoleService extends AbstractService<Role> implements IRoleService {

    private static final Logger LOGGER = LogManager.getLogger(RoleService.class.getName());
    private static IRoleDao roleDao;
    private static RoleService instance;

    private RoleService() {
        dao = RoleDaoImpl.getInstance();
        roleDao = RoleDaoImpl.getInstance();
    }

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService();
        }
        return instance;
    }

    public List<Role> getAll() throws ServiceException {
        return getAllEntities();
    }

    @Override
    public boolean update(Role entityDTO, Long id, StringBuilder msg) throws ServiceException {
        updateEntity(entityDTO, id, msg);
        return true;

    }

    @Override
    public Role getById(Long id) throws ServiceException {
        return getEntityById(id);
    }

    @Override
    public void remove(Long id) throws ServiceException {
        removeEntity(id);
    }

    @Override
    public Role getByName(String roleName) throws ServiceException {
        try {
            return roleDao.findByName(roleName);
        } catch (DaoException | NoSuchEntityException e) {
            catchServiceException(e, "Could not find role by name: " + roleName);
        }
        return null;
    }

}
