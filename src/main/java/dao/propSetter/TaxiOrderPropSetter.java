package dao.propSetter;

import dao.exceptions.DaoException;
import entities.Status;
import entities.TaxiOrder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TaxiOrderPropSetter implements IPropSetter<TaxiOrder> {
    @Override
    public void setProperties(PreparedStatement statement, TaxiOrder order) throws DaoException {
        try {
            statement.setTimestamp(1, new Timestamp(order.getOrderDate().getTime()));
            statement.setString(2, order.getStartPoint());
            statement.setString(3, order.getEndPoint());
            statement.setInt(4, order.getDistance());
            statement.setBigDecimal(5, order.getCost());
            statement.setTime(6, order.getFeedTime());
            String statusString  = "";
            Status status = order.getStatus();
            if(status!=null){
                statusString = status.toString();
            }
            statement.setString(7, statusString);

            setIdIfNotNull(statement, 8,order.getUserId());
            setIdIfNotNull(statement, 9,order.getTaxiId());
            setIdIfNotNull(statement, 10,order.getId());

        } catch (SQLException e) {
            throw new DaoException("Can't set statement properties for entity " + order, e);
        }
    }
}
