package dao.propSetter;

import dao.exceptions.DaoException;
import entities.Share;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SharePropSetter implements IPropSetter<Share> {
    @Override
    public void setProperties(PreparedStatement statement, Share share) throws DaoException {
        try {
            statement.setString(1, share.getShareName());
            statement.setBoolean(2, share.getIsLoyalty());
            statement.setBigDecimal(3, share.getSum());
            statement.setFloat(4, share.getPercent());
            statement.setBoolean(5, share.getIsOn());
            setIdIfNotNull(statement,6, share.getId());
        } catch (SQLException e) {
            throw new DaoException("Can't set statement properties for entity " + share, e);
        }
    }
}
