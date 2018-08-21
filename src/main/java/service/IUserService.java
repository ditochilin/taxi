package service;

import entities.User;
import service.exceptions.ServiceException;

/**
 * Service for working with user
 * @author Dmitry Tochilin
 */
public interface IUserService {

    /**
     * Check if the user is authenticated
     * @param user user for check
     * @return  fulfill dummy user with data from database
     */
    User checkUserByPassword(User user) throws ServiceException;

}
