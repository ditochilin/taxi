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
     * @return
     */
    boolean userIsCorrect(User user) throws ServiceException;

}
