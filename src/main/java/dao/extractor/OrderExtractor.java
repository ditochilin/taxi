package dao.extractor;

import entities.Order;
import entities.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderExtractor implements IExtractor<Order> {

    @Override
    public Order extractEntityData(ResultSet resultSet) throws SQLException {
        Order Order = new Order(
                resultSet.getLong("id_order"),
                resultSet.getTimestamp("order_date"),
                resultSet.getString("start_point"),
                resultSet.getString("end_point"),
                resultSet.getInt("distance"),
                resultSet.getBigDecimal("cost"),
                resultSet.getTimestamp("feed_time"),
                Status.valueOf(resultSet.getString("status_order")),
                resultSet.getInt("waiting_time"),
                resultSet.getInt("discount")
        );
        return Order;
    }
}
