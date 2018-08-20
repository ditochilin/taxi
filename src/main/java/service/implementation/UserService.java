package service.implementation;

import dao.exceptions.DaoException;
import dao.implementation.UserDaoImpl;
import entities.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IUserService;

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

    public boolean userIsCorrect(User user) {
        String phone = user.getPhone();
        try {
            List<User> users = userDao.findByPhone(phone);
            if (users.isEmpty()) {
                LOGGER.log(Level.INFO, String.format("User with phone %s", phone));
                return false;
            }
            return checkPassword(user, users.get(0));
        } catch (DaoException e) {
            return false;
        }
    }

    private boolean checkPassword(User user, User userSecond) {
        return user.getPassword() == userSecond.getPassword();
    }


}
