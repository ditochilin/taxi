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
import service.IUserService;
import service.exceptions.IncorrectPassword;
import service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class.getName());
    private static UserService instance;
    private static IUserDao userDao;
    private static IRoleDao roleDao;

    private UserService() {
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
            if(checkPassword(user, userByName)){
                return userByName;
            }else{
                throw new IncorrectPassword("Password is wrong!");
            }
        } catch (DaoException e) {
            throw new ServiceException(String.format("User $s not found. %s",userName,e.getMessage()),e);
        }
    }

    @Override
    public List<User> getAllClients() {
        try {
            Role roleClient = roleDao.findByName("Client");
            return userDao.findByRole(roleClient);
        } catch (DaoException | NoSuchEntityException e) {
            LOGGER.error("Couldn't get clients from database.");
        }
        return new ArrayList<>();
    }

    @Override
    public boolean suchNameIsPresent(String userName) {
        try {
            return !userDao.findByName(userName).isEmpty();
        } catch (DaoException e) {
            LOGGER.error("Could not find user by name "+userName,e);
        }
        return false;
    }

    @Override
    public boolean addNewUser(User user) {
        try {
            return userDao.insert(user) > 0;
        } catch (DaoException e) {
            LOGGER.error("Could not create new user:"+user,e.getCause());
        }
        return false;
    }

    @Override
    public boolean suchPhoneIsPresent(String phone) {
        try {
            return !userDao.findByPhone(phone).isEmpty();
        } catch (DaoException e) {
            LOGGER.error("Could not check user by phone "+phone,e);
        }
        return false;
    }

    private boolean checkPassword(User user, User userByName) {
        if(!user.getPassword().equals(userByName.getPassword())){
            LOGGER.log(Level.INFO, String.format("Password is not correct entered for %s", user.getUserName()));
            return false;
        }
        return true;
    }


}
