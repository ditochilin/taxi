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
    public List<User> getAllDrivers() {
        try {
            Role roleDriver = roleDao.findByName("DRIVER");
            return userDao.findByRole(roleDriver);
        } catch (DaoException | NoSuchEntityException e) {
            LOGGER.error("Couldn't get drivers from database.");
        }
        return new ArrayList<>();
    }

    @Override
    public List<User> getUsersByName(String userName) throws ServiceException {
        try {
            return userDao.findByName(userName);
        } catch (DaoException e) {
            catchServiceException(e, "Could not find user by name :" + userName);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean suchNameIsPresent(String userName) throws ServiceException {
        try {
            return !getUsersByName(userName).isEmpty();
        } catch (Exception e) {
            catchServiceException(e, "Could not define presentation of user by name :" + userName);
        }
        return false;
    }

    @Override
    public boolean suchPhoneIsPresent(String phone) throws ServiceException {
        try {
            return !userDao.findByPhone(phone).isEmpty();
        } catch (DaoException e) {
            catchServiceException(e, "Could not check user by phone " + phone);
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
    public List<User> getAll() throws ServiceException {
        return getAllEntities();
    }

    @Override
    public boolean update(User entityDTO, Long id, StringBuilder msg) throws ServiceException {
        updateEntity(entityDTO, id, msg);
        return true;
    }

    @Override
    public User getById(Long id) throws ServiceException {
        return getEntityById(id);
    }

    @Override
    public void remove(Long id) throws ServiceException {
        removeEntity(id);
    }
}
