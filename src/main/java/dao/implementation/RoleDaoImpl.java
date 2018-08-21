package dao.implementation;

import dao.AbstractDao;
import dao.IRoleDao;
import dao.enricher.IEnricher;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.extractor.IExtractor;
import dao.extractor.RoleExtractor;
import dao.propSetter.IPropSetter;
import dao.propSetter.RolePropSetter;
import entities.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Defines CRUD operations for Role entity
 * @author Dmitry Tochilin
 */
public class RoleDaoImpl extends AbstractDao<Role> implements IRoleDao {

    private static final Logger LOGGER = LogManager.getLogger(RoleDaoImpl.class.getName());
    private static RoleDaoImpl instance = new RoleDaoImpl();
    private static IExtractor<Role> extractor;
    private static IPropSetter<Role> propSetter;

    //  SQL
    private static final String FIND_ALL = "SELECT id_role, role_name, description FROM roles";
    private static final String INSERT = "INSERT INTO roles (role_name, description) VALUES(?,?)";
    private static final String UPDATE = "UPDATE roles SET role_name = ?, description = ? WHERE id_role = ?";
    private static final String DELETE = "DELETE FROM roles WHERE id_role = ?";

    private RoleDaoImpl() {
        extractor = new RoleExtractor();
        propSetter = new RolePropSetter();
    }

    public static RoleDaoImpl getInstance() {
        if (instance == null) {
            instance = new RoleDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Role> findAll() throws DaoException {
        return findBy(FIND_ALL, null, null, extractor, IEnricher.NULL);
    }

    @Override
    public Role findById(Long id) throws DaoException, NoSuchEntityException {
        return findById(FIND_ALL, "id_role", id, extractor, IEnricher.NULL);
    }

    @Override
    public List<Role> findByName(String roleName) throws DaoException, NoSuchEntityException {
        return findBy(FIND_ALL, "role_name", roleName, extractor, IEnricher.NULL);
    }

    @Override
    public Long insert(Role role) throws DaoException {
        Long id = insert(role, INSERT, propSetter);
        role.setId(id);
        LOGGER.log( Level.INFO,"New record of entity in database: "+role);
        return id;
    }

    @Override
    public Role update(Role newRole) throws DaoException {
        update(newRole, UPDATE, propSetter);
        LOGGER.log(Level.INFO, "Role has been changed: "+newRole);
        return newRole;
    }

    @Override
    public boolean delete(Role role) throws DaoException {
        boolean success = deleteById(role.getId(), DELETE);
        LOGGER.log(Level.INFO, "Role has been deleted: "+role);
        return success;
    }


}