package dao.propSetter;

import dao.exceptions.DaoException;
import entities.Order;
import entities.Status;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderPropSetter implements IPropSetter<Order> {
    @Override
    public void setProperties(PreparedStatement statement, Order order) throws DaoException {
        try {
            statement.setTimestamp(1, new Timestamp(order.getDateTime().getTime()));
            statement.setString(2, order.getStartPoint());
            statement.setString(3, order.getEndPoint());
            statement.setInt(4, order.getDistance());
            statement.setBigDecimal(5, order.getCost());
            statement.setTime(6, order.getFeedTime());
            String statusString = "";
            Status status = order.getStatus();
            if (status != null) {
                statusString = status.toString();
            } else {
                statusString = Status.CREATED.toString();
            }
            statement.setString(7, statusString);

            setValueOrNull(statement, 8, order.getClientId());
            setValueOrNull(statement, 9, order.getTaxiId());
            setIdIfNotNull(statement, 10, order.getId());

        } catch (SQLException e) {
            throw new DaoException("Can't set statement properties for entity " + order, e);
        }
    }
}
