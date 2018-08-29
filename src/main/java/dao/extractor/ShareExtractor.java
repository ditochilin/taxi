package dao.extractor;

import entities.Share;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShareExtractor implements IExtractor<Share> {
    @Override
    public Share extractEntityData(ResultSet resultSet) throws SQLException {
        Share share = new Share();
        share.setId(resultSet.getLong("id_share"));
        share.setShareName(resultSet.getString("share_name"));
        share.setIsLoyalty(resultSet.getBoolean("is_loyalty"));
        share.setSum(resultSet.getBigDecimal("sum"));
        share.setPercent(resultSet.getFloat("percent"));
        share.setIsOn(resultSet.getBoolean("is_on"));
        return share;
    }
}
