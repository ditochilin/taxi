package dao.extractor;

import entities.Share;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShareExtractor implements IExtractor<Share> {
    @Override
    public Share extractEntityData(ResultSet resultSet) throws SQLException {
        Share share = new Share(
                resultSet.getLong("id_share"),
                resultSet.getString("share_name"),
                resultSet.getBoolean("is_loyalty"),
                resultSet.getBoolean("is_on"),
                resultSet.getBigDecimal("sum"),
                resultSet.getFloat("percent")
        );
        return share;
    }
}
