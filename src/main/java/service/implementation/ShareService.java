package service.implementation;

import dao.IShareDao;
import dao.exceptions.DaoException;
import dao.implementation.ShareDaoImpl;
import entities.Share;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IShareService;

import java.util.ArrayList;
import java.util.List;

public class ShareService implements IShareService {

    private static final Logger LOGGER = LogManager.getLogger(ShareService.class.getName());
    private static IShareDao shareDao;
    private static ShareService instance;

    private ShareService() {
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
    public boolean update(Share entityDTO, Long id) {
        return false;
    }
}
