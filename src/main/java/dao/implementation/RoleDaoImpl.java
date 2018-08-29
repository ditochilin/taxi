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
import dao.transactionManager.TransactionManagerImpl;
import entities.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Defines CRUD operations for Role entity
 *
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
        return TransactionManagerImpl.doInTransaction(() ->
                findByInTransaction(FIND_ALL, null, null, extractor, IEnricher.NULL));
    }

    @Override
    public Role findById(Long id) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->
                findById(FIND_ALL, "id_role", id, extractor, IEnricher.NULL));
    }

    @Override
    public Role findByName(String roleName) throws DaoException {
        List<Role> roles = TransactionManagerImpl.doInTransaction(() ->
                (findByInTransaction(FIND_ALL, "role_name", roleName.toUpperCase(), extractor, IEnricher.NULL)));
        if (roles.isEmpty()) {
            return null;
        }
        return roles.get(0);
    }

    @Override
    public Long insert(Role role) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() -> insertInTransaction(role, INSERT, propSetter));
    }

    @Override
    public Role update(Role newRole) throws DaoException {
        return TransactionManagerImpl.doInTransaction(()->updateInTransaction(newRole, UPDATE, propSetter));
    }

    @Override
    public boolean delete(Role role) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() -> deleteInTransaction(role, DELETE));
    }


}
