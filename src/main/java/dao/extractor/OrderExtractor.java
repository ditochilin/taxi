package dao.extractor;

import entities.Status;
import entities.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderExtractor implements IExtractor<Order> {

    @Override
    public Order extractEntityData(ResultSet resultSet) throws SQLException {
        Order Order = new Order();
        Order.setId(resultSet.getLong("id_order"));
        Order.setDateTime(resultSet.getTimestamp("order_date"));
        Order.setStartPoint(resultSet.getString("start_point"));
        Order.setEndPoint(resultSet.getString("end_point"));
        Order.setDistance(resultSet.getInt("distance"));
        Order.setCost(resultSet.getBigDecimal("cost"));
        Order.setFeedTime(resultSet.getTimestamp("feed_time"));
        Order.setStatus(Status.valueOf(resultSet.getString("status_order")));
        Order.setWaitingTime(resultSet.getInt("waiting_time"));
        return Order;
    }
}
