package service;

import dao.IDao;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.exceptions.ServiceException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractService<T> implements IService<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractService.class.getName());
    protected IDao<T> dao;

    protected List<T> getAllEntities() throws ServiceException {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            catchServiceException(e, "Couldn't get all entities from database.");
        }
        return new ArrayList<>();
    }

    protected T getEntityById(Long id) throws ServiceException {
        try {
            return dao.findById(id);
        } catch (DaoException | NoSuchEntityException e) {
            catchServiceException(e, "Couldn't get all entities from database.");
        }
        return null;
    }

    protected void updateEntity(T entityDTO, Long id, StringBuilder msg) throws ServiceException {
        try {
            if (isEntityDTONew(entityDTO, id)) {
                dao.insert(entityDTO);
                msg.append("Successful addition!");
            } else {
                dao.update(entityDTO);
                msg.append("Successful update!");
            }
        } catch (DaoException e) {
            catchServiceException(e, "Couldn't get update/insert entity database. " + e.getCause());
        }
    }

    protected void removeEntity(Long id) throws ServiceException {
        try {
            dao.delete(id);
        } catch (DaoException e) {
            catchServiceException(e, "Couldn't delete entity database with id: " + id + e.getCause());
        }
    }

    private boolean isEntityDTONew(T entityDTO, Long id) throws ServiceException {
        Class clazz = entityDTO.getClass();
        Long idCurrent = null;
        try {
            idCurrent = (Long) clazz.getMethod("getId").invoke(entityDTO);
            if (idCurrent == null || idCurrent == 0) {
                clazz.getMethod("setId", Long.class).invoke(entityDTO, id);
                return true;
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            catchServiceException(e, "Could not deside if entity " + entityDTO + " is new. ");
        }
        return false;
    }

    protected void catchServiceException(Exception cause, String message) throws ServiceException {
        String msgCause = cause.getMessage();
        LOGGER.error(message, msgCause);
        throw new ServiceException(message, cause);
    }
}
