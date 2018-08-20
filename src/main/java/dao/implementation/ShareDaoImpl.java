package dao.implementation;

import dao.AbstractDao;
import dao.IShareDao;
import dao.enricher.IEnricher;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.extractor.IExtractor;
import dao.extractor.ShareExtractor;
import dao.propSetter.IPropSetter;
import dao.propSetter.SharePropSetter;
import entities.Share;
import entities.TaxiOrder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShareDaoImpl extends AbstractDao<Share> implements IShareDao {

    private static final Logger LOGGER = LogManager.getLogger(RoleDaoImpl.class.getName());
    private static IExtractor<Share> extractor;
    private static IPropSetter<Share> propSetter;
    private static ShareDaoImpl instance;

    //  SQL
    private static final String FIND_ALL = "SELECT id_share, share_name, is_loyalty, sum, percent, is_on FROM shares";
    private static final String FIND_BY_ORDER = "SELECT id_share, share_name, is_loyalty, sum, percent, is_on " +
            " FROM shares " +
            " WHERE id_share IN(" +
            " SELECT id_share as id " +
            " FROM orders_shares " +
            " WHERE id_order = ?)";
    private static final String INSERT = "INSERT INTO shares (share_name, is_loyalty, sum, percent, is_on) VALUES(?,?,?,?,?)";
    private static final String UPDATE = "UPDATE shares SET share_name = ?, is_loyalty = ?, sum = ?, percent = ?, is_on = ? WHERE id_share = ?";
    private static final String DELETE = "DELETE FROM shares WHERE id_share = ?";

    private ShareDaoImpl() {
        extractor = new ShareExtractor();
        propSetter = new SharePropSetter();
    }

    public static ShareDaoImpl getInstance() {
        if (instance == null) {
            instance = new ShareDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Share> findAll() throws DaoException {
        return findBy(FIND_ALL, null, null, extractor, IEnricher.NULL);
    }

    @Override
    public Share findById(Long id) throws DaoException, NoSuchEntityException {
        return findById(FIND_ALL, "id_share", id, extractor, IEnricher.NULL);
    }

    @Override
    public Long insert(Share share) throws DaoException {
        Long id = insert(share, INSERT, propSetter);
        share.setId(id);
        LOGGER.log(Level.INFO, "New record of entity in database: " + share);
        return id;
    }

    @Override
    public Share update(Share share) throws DaoException {
        update(share, UPDATE, propSetter);
        LOGGER.log(Level.INFO, "Share has been changed: " + share);
        return share;
    }

    @Override
    public boolean delete(Share share) throws DaoException {
        boolean success = deleteById(share.getId(), DELETE);
        LOGGER.log(Level.INFO, "Share has been deleted: " + share);
        return success;
    }

    @Override
    public List<Share> findSharesByOrder(TaxiOrder taxiOrder) {
        List<Share> shares = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = createStatement(connection, FIND_BY_ORDER, null, taxiOrder.getId());
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Share share = extractor.extractEntityData(resultSet);
                shares.add(share);
            }
        } catch (SQLException e) {
            LOGGER.warn("Could not get shares for order: " + taxiOrder);
        }
        return shares;
    }
}
