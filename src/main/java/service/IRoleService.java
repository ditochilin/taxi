package service;

import entities.Role;
import service.exceptions.ServiceException;

import java.util.List;

/**
 * Service for working with user
 * @author Dmitry Tochilin
 */
public interface IRoleService {

    Role create(String name, String description) throws ServiceException;
    List<Role> findAll() throws ServiceException;
    Role findByName(String role);
}
