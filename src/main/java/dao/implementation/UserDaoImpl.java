package dao.implementation;

import dao.AbstractDao;
import dao.IRoleDao;
import dao.IUserDao;
import dao.enricher.IEnricher;
import dao.enricher.UserEnricher;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.extractor.IExtractor;
import dao.extractor.UserExtractor;
import dao.propSetter.IPropSetter;
import dao.propSetter.UserPropSetter;
import entities.Role;
import entities.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserDaoImpl extends AbstractDao<User> implements IUserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class.getName());
    private static UserDaoImpl instance;
    private static IExtractor<User> extractor;
    private static IPropSetter<User> propSetter;
    private static IEnricher<User> enricher;
    private static IRoleDao roleDao;

    //SQL
    private static final String FIND_ALL = "SELECT user_name, password, phone, id_user, id_role FROM users";
    private static final String INSERT = "INSERT INTO users (user_name, password, phone, id_role) VALUES(?,?,?,?)";
    private static final String UPDATE = "UPDATE users SET user_name = ?, password = ?, phone = ?, id_role = ? WHERE id_user = ?";
    private static final String DELETE = "DELETE FROM users WHERE id_user = ?";

    private UserDaoImpl() {
        roleDao = RoleDaoImpl.getInstance();
        extractor = new UserExtractor();
        propSetter = new UserPropSetter();
        enricher = new UserEnricher(roleDao);
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return findBy(FIND_ALL, null, null, extractor, enricher);
    }

    @Override
    public User findById(Long id) throws DaoException, NoSuchEntityException {
        return findById(FIND_ALL, "id_user", id, extractor, enricher);
    }

    @Override
    public List<User> findByName(String userName) throws DaoException {
        return findBy(FIND_ALL, "user_name", userName, extractor, enricher);
    }

    @Override
    public List<User> findByRole(Role role) throws DaoException {
        return findBy(FIND_ALL, "id_role", role.getId(), extractor, enricher);
    }

    @Override
    public List<User> findByPhone(String phone) throws DaoException {
        return findBy(FIND_ALL, "phone", phone, extractor, enricher);
    }

    @Override
    public Long insert(User user) throws DaoException {
        Long id = insert(user, INSERT, propSetter);
        user.setId(id);
        LOGGER.log(Level.INFO, "New record of entity in database: " + user);
        return id;
    }

    @Override
    public User update(User newUser) throws DaoException {
        update(newUser, UPDATE, propSetter);
        LOGGER.log(Level.INFO, "User has been changed: " + newUser);
        return newUser;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        boolean success = deleteById(user.getId(), DELETE);
        LOGGER.log(Level.INFO, "User has been deleted: " + user);
        return success;
    }
}
