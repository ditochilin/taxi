package service.implementation;

import dao.IRoleDao;
import dao.IUserDao;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.RoleDaoImpl;
import dao.implementation.UserDaoImpl;
import entities.Role;
import entities.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AbstractService;
import service.IUserService;
import service.exceptions.IncorrectPassword;
import service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class UserService extends AbstractService<User> implements IUserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class.getName());
    private static UserService instance;
    private static IRoleDao roleDao;
    private static IUserDao userDao;

    private UserService() {
        dao = UserDaoImpl.getInstance();
        userDao = UserDaoImpl.getInstance();
        roleDao = RoleDaoImpl.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User checkUserByPassword(User user) throws ServiceException, IncorrectPassword {
        String userName = user.getUserName();
        try {
            List<User> users = userDao.findByName(userName);
            if (users.isEmpty()) {
                LOGGER.log(Level.INFO, String.format("User with nickname %s", userName));
                return null;
            }
            User userByName = users.get(0);
            if (checkPassword(user, userByName)) {
                return userByName;
            } else {
                throw new IncorrectPassword("Password is wrong!");
            }
        } catch (DaoException e) {
            throw new ServiceException(String.format("User $s not found. %s", userName, e.getMessage()), e);
        }
    }

    @Override
    public List<User> getAllClients() {
        try {
            Role roleCLIENT = roleDao.findByName("CLIENT");
            return userDao.findByRole(roleCLIENT);
        } catch (DaoException | NoSuchEntityException e) {
            LOGGER.error("Couldn't get clients from database.");
        }
        return new ArrayList<>();
    }

    @Override
    public List<User> getUsersByName(String userName) {
        try {
            return userDao.findByName(userName);
        } catch (DaoException e) {
            LOGGER.error("Could not find user by name " + userName, e);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean suchNameIsPresent(String userName) {
        return !getUsersByName(userName).isEmpty();
    }

    @Override
    public boolean suchPhoneIsPresent(String phone) {
        try {
            return !userDao.findByPhone(phone).isEmpty();
        } catch (DaoException e) {
            LOGGER.error("Could not check user by phone " + phone, e);
        }
        return false;
    }

    private boolean checkPassword(User user, User userByName) {
        if (!user.getPassword().equals(userByName.getPassword())) {
            LOGGER.log(Level.INFO, String.format("Password is not correct entered for %s", user.getUserName()));
            return false;
        }
        return true;
    }

    @Override
    public List<User> getAll() {
        try {
            return getAllEntities();
        } catch (Exception e) {
            LOGGER.error("Couldn't get all users from database.");
        }
        return new ArrayList<>();
    }

    @Override
    public boolean update(User entityDTO, Long id, StringBuilder msg) {
        try {
            updateEntity(entityDTO, id, msg);
            return true;
        } catch (Exception e) {
            LOGGER.error("Could not update/insert user.", e.getCause());
        }
        return false;
    }

    @Override
    public User getById(Long id) {
        try {
            return getEntityById(id);
        } catch (Exception e) {
            LOGGER.error("Couldn't get all users from database.", e.getCause());
        }
        return null;
    }

    @Override
    public void remove(Long id) throws Exception {
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            LOGGER.error("Could not remove user.", e.getCause());
            throw new Exception(e);
        }
    }

}
