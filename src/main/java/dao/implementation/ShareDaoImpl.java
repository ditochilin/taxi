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
import dao.transactionManager.TransactionManagerImpl;
import entities.Share;
import entities.Order;
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

    private static final Logger LOGGER = LogManager.getLogger(ShareDaoImpl.class.getName());
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
        return TransactionManagerImpl.doInTransaction(() ->(
                findByInTransaction(FIND_ALL, null, null, extractor, IEnricher.NULL)));
    }

    @Override
    public Share findById(Long id) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->(
                findById(FIND_ALL, "id_share", id, extractor, IEnricher.NULL)));
    }

    @Override
    public List<Share> findSharesByOrder(Order Order) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() ->{
        List<Share> shares = new ArrayList<>();
        try (PreparedStatement statement = createStatement(getConnection(), FIND_BY_ORDER, null, Order.getId());
             ResultSet resultSet = statement.executeQuery()) {
             while (resultSet.next()) {
                Share share = extractor.extractEntityData(resultSet);
                shares.add(share);
             }

        } catch (SQLException e) {
            LOGGER.warn("Could not get shares for order: " + Order);
        }
            return shares;
        });
    }

    @Override
    public Long insert(Share share) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() -> insertInTransaction(share, INSERT, propSetter));
    }

    @Override
    public Share update(Share share) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() -> updateInTransaction(share, UPDATE, propSetter));
    }

    @Override
    public boolean delete(Share share) throws DaoException {
        return TransactionManagerImpl.doInTransaction(() -> deleteInTransaction(share, DELETE));
    }
}
