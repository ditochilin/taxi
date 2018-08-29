package service;

import com.sun.jdi.InvocationException;
import dao.AbstractDao;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.factoryDao.DaoFactory;
import dao.implementation.UserDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractService<T> implements IService<T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractService.class.getName());
    AbstractDao abstractDao;

    public void setFactory(AbstractDao<T> daoImpl){
        this.abstractDao = daoImpl;
    }

    @Override
    public List<T> getAll() {
        try {
            return abstractDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Couldn't get all entities from database.");
        }
        return new ArrayList<>();
    }

    @Override
    public boolean update(T entityDTO, Long id) {
//        try {
//            if(isEntityDTONew(entityDTO, id)){
//                abstractDao.insert(entityDTO);
//            }else{
//                abstractDao.update(entityDTO);
//            }
//        } catch (DaoException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//            LOGGER.error("Couldn't get update/insert entity database.");
//        }
        return false;
    }

    private boolean isEntityDTONew(T entityDTO, Long id) {
//        Class clazz = entityDTO.getClass();
//        Long idCurrent = (Long) clazz.getMethod("getId").invoke(entityDTO);
//        if(idCurrent==null){
//            clazz.getMethod("setId", Long.class).invoke(entityDTO,id);
//            return true;
//        }
        return false;
    }
}
