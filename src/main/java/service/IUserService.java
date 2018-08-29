package service;

import entities.User;
import service.exceptions.IncorrectPassword;
import service.exceptions.ServiceException;

import java.util.List;

/**
 * Service for working with user
 * @author Dmitry Tochilin
 */
public interface IUserService extends IService<User> {

    /**
     * Check if the user is authenticated
     * @param user user for check
     * @return  fulfill dummy user with data from database
     */
    User checkUserByPassword(User user) throws ServiceException, IncorrectPassword;

    List<User> getAllClients();

    boolean suchNameIsPresent(String userName);

   // boolean addNewUser(User user);

    boolean suchPhoneIsPresent(String phone);

}
