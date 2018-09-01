package dao.propSetter;

import dao.exceptions.DaoException;
import entities.Order;
import entities.Status;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class OrderPropSetter implements IPropSetter<Order> {

    private static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Kiev"));

    @Override
    public void setProperties(PreparedStatement statement, Order order) throws DaoException {
        try {
            setDateTimeForSQL(statement,1, order.getDateTime());
            statement.setString(2, order.getStartPoint());
            statement.setString(3, order.getEndPoint());
            setValueOrNull(statement,4, order.getDistance());
            setValueOrNull(statement,5, order.getCost());
            setDateTimeForSQL(statement,6, order.getFeedTime());
            String statusString = "";
            Status status = order.getStatus();
            if (status != null) {
                statusString = status.toString();
            } else {
                statusString = Status.CREATED.toString();
            }
            statement.setString(7, statusString);
            setValueOrNull(statement, 8, order.getWaitingTime());
            setValueOrNull(statement, 9, order.getDiscount());

            setValueOrNull(statement, 10, order.getClientId());
            setValueOrNull(statement, 11, order.getTaxiId());
            setValueOrNull(statement, 12, order.getCarTypeId());
            setIdIfNotNull(statement, 13, order.getId());

        } catch (SQLException e) {
            throw new DaoException("Can't set statement properties for entity " + order, e);
        }
    }

    private void setDateTimeForSQL(PreparedStatement statement, int parameterIndex, Date date) throws SQLException {
        Timestamp timestamp = null;
        if(date != null){
            timestamp = new Timestamp(date.getTime());
        }
        statement.setTimestamp(parameterIndex, timestamp, calendar);
    }

}
