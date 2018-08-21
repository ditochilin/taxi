package service.implementation;

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
    private final UserDaoImpl userDao;

    private UserService() {
        userDao = UserDaoImpl.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean userIsCorrect(User user) throws ServiceException {
        String userName = user.getUserName();
        try {
            List<User> users = userDao.findByName(userName);
            if (users.isEmpty()) {
                LOGGER.log(Level.INFO, String.format("User with nickname %s", userName));
                return false;
            }
            return checkPassword(user, users.get(0));
        } catch (DaoException e) {
            throw new ServiceException("User not found. "+e.getMessage(),e);
        }
    }

    private boolean checkPassword(User user, User userSecond) {
        return user.getPassword().equals(userSecond.getPassword());
    }


}
