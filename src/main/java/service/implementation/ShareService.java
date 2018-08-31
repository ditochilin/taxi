package service.implementation;

import dao.IShareDao;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.ShareDaoImpl;
import entities.Share;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AbstractService;
import service.IShareService;

import java.util.ArrayList;
import java.util.List;

public class ShareService extends AbstractService<Share> implements IShareService {

    private static final Logger LOGGER = LogManager.getLogger(ShareService.class.getName());
    private static IShareDao shareDao;
    private static ShareService instance;

    private ShareService() {
        dao = ShareDaoImpl.getInstance();
        shareDao = ShareDaoImpl.getInstance();
    }

    public static ShareService getInstance() {
        if (instance == null) {
            instance = new ShareService();
        }
        return instance;
    }

    @Override
    public List<Share> getAll() {
        try {
            return shareDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Could not get all shares",e.getCause());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean update(Share entityDTO, Long id, StringBuilder msg) throws Exception {
        try {
            updateEntity(entityDTO, id, msg);
            return true;
        } catch (Exception e) {
            LOGGER.error("Could not update/insert share.", e.getCause());
            throw e;
        }
    }

    @Override
    public Share getById(Long id) {
        try {
            return shareDao.findById(id);
        } catch (DaoException | NoSuchEntityException e) {
            LOGGER.error("Could not get all share by id: "+id, e.getCause());
        }
        return null;
    }

    @Override
    public void remove(Long id) throws Exception {
        try {
            shareDao.delete(id);
        } catch (DaoException e) {
            LOGGER.error("Could not remove user.", e.getCause());
            throw new Exception(e);
        }
    }

    @Override
    public boolean suchShareIsPresent(String shareName) {
        try {
            return !shareDao.findByName(shareName).isEmpty();
        } catch (DaoException e) {
            LOGGER.error("No share found bt name :"+shareName, e.getCause());
        }
        return false;
    }

    @Override
    public boolean ifLoyaltyDoesAlreadyExist() {
        try {
            return shareDao.findLoyalty();
        } catch (DaoException e) {
            LOGGER.error(e.getCause());
        }
        return false;
    }
}
