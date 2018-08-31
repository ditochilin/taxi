package service;

import entities.Share;
import service.exceptions.ServiceException;

/**
 * Service for working with shares
 *
 * @author Dmitry Tochilin
 */
public interface IShareService extends IService<Share> {

    boolean suchShareIsPresent(String shareName) throws ServiceException;

    boolean ifLoyaltyDoesAlreadyExist(String shareName) throws ServiceException;
}
