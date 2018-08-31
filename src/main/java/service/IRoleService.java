package service;

import entities.Role;
import service.exceptions.ServiceException;

/**
 * Service for working with user
 *
 * @author Dmitry Tochilin
 */
public interface IRoleService extends IService<Role> {

    Role getByName(String roleName) throws ServiceException;

}
