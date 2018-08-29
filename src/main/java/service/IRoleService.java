package service;

import entities.Role;
import service.exceptions.ServiceException;

/**
 * Service for working with user
 *
 * @author Dmitry Tochilin
 */
public interface IRoleService extends IService<Role> {

    Role create(String name, String description) throws ServiceException;

    Role getByName(String roleName);

}
