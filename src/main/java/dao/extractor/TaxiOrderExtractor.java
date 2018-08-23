package dao.extractor;

import entities.Status;
import entities.TaxiOrder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxiOrderExtractor implements IExtractor<TaxiOrder> {

    @Override
    public TaxiOrder extractEntityData(ResultSet resultSet) throws SQLException {
        TaxiOrder taxiOrder = new TaxiOrder();
        taxiOrder.setId(resultSet.getLong("id_order"));
        // todo. check date transferring
        taxiOrder.setOrderDate(resultSet.getTimestamp("order_date"));
        taxiOrder.setStartPoint(resultSet.getString("start_point"));
        taxiOrder.setEndPoint(resultSet.getString("end_point"));
        taxiOrder.setDistance(resultSet.getInt("distance"));
        taxiOrder.setCost(resultSet.getBigDecimal("cost"));
        // todo. check date transferring
        taxiOrder.setFeedTime(resultSet.getTime("feed_time"));
        taxiOrder.setStatus(Status.valueOf(resultSet.getString("status_order")));
        return taxiOrder;
    }
}
