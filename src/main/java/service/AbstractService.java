package service;

import dao.IDao;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractService<T> implements IService<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractService.class.getName());
    protected IDao<T> dao;

    protected List<T> getAllEntities() throws Exception {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Couldn't get all entities from database.");
            throw new Exception(e);
        }
    }

    protected T getEntityById(Long id) throws Exception {
        try {
            return dao.findById(id);
        } catch (DaoException | NoSuchEntityException e) {
            LOGGER.error("Couldn't get all entities from database.");
            throw new Exception(e);
        }
    }

    protected void updateEntity(T entityDTO, Long id, StringBuilder msg) throws Exception {
        try {
            if (isEntityDTONew(entityDTO, id)) {
                dao.insert(entityDTO);
                msg.append("Successful registration!");
            } else {
                dao.update(entityDTO);
                msg.append("Successful update!");
            }
        } catch (DaoException e) {
            LOGGER.error("Couldn't get update/insert entity database. " + e.getCause());
            throw new Exception(e.getCause());
        }
    }

    private boolean isEntityDTONew(T entityDTO, Long id) {
        Class clazz = entityDTO.getClass();
        Long idCurrent = null;
        try {
            idCurrent = (Long) clazz.getMethod("getId").invoke(entityDTO);
            if (idCurrent == null || idCurrent == 0) {
                clazz.getMethod("setId", Long.class).invoke(entityDTO, id);
                return true;
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }
}
