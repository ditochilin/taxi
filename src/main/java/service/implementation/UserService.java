package service.implementation;

import dao.IUserDao;
import dao.exceptions.DaoException;
import dao.implementation.UserDaoImpl;
import entities.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IUserService;
import service.exceptions.ServiceException;

import java.util.List;

public class UserService implements IUserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class.getName());
    private static UserService instance;
    private static IUserDao userDao;

    private UserService() {
        userDao = UserDaoImpl.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User checkUserByPassword(User user) throws ServiceException {
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
            }
            return null;
        } catch (DaoException e) {
            throw new ServiceException(String.format("User $s not found. %s",userName,e.getMessage()),e);
        }
    }

    private boolean checkPassword(User user, User userByName) {
        if(!user.getPassword().equals(userByName.getPassword())){
            LOGGER.log(Level.INFO, String.format("Password is not correct entered for %s", user.getUserName()));
            return false;
        }
        return true;
    }


}
