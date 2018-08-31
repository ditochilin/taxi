package service;

import entities.CarType;
import service.exceptions.ServiceException;

import java.util.List;

/**
 *  Service for working with car types
 *
 * @author Dmitry Tochilin
 */
public interface ICarTypeService extends IService<CarType> {

    boolean suchCarTypeIsPresent(String typeName) throws ServiceException;

}
