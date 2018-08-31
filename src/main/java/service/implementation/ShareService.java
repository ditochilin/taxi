package service.implementation;

import dao.IShareDao;
import dao.exceptions.DaoException;
import dao.implementation.ShareDaoImpl;
import entities.Share;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AbstractService;
import service.IShareService;
import service.exceptions.ServiceException;

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
    public List<Share> getAll() throws ServiceException {
        return getAllEntities();

    }

    @Override
    public boolean update(Share entityDTO, Long id, StringBuilder msg) throws ServiceException {
        updateEntity(entityDTO, id, msg);
        return true;
    }

    @Override
    public Share getById(Long id) throws ServiceException {
        return getEntityById(id);
    }

    @Override
    public void remove(Long id) throws ServiceException {
        removeEntity(id);
    }

    @Override
    public boolean suchShareIsPresent(String shareName) throws ServiceException {
        try {
            return !shareDao.findByName(shareName).isEmpty();
        } catch (DaoException e) {
            catchServiceException(e, "Could not find share by name: " + shareName);
        }
        return false;
    }

    @Override
    public boolean ifLoyaltyDoesAlreadyExist(String shareName) throws ServiceException {
        try {
            return shareDao.ifLoyaltyIsPresent(shareName);
        } catch (DaoException e) {
            catchServiceException(e, "Could not check loyalty");
        }
        return false;
    }
}
