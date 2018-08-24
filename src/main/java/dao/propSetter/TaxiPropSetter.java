package dao.propSetter;

import dao.exceptions.DaoException;
import entities.Taxi;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaxiPropSetter implements IPropSetter<entities.Taxi> {
    @Override
    public void setProperties(PreparedStatement statement, Taxi taxi) throws DaoException {
        try {
            statement.setString(1, taxi.getCarName());
            statement.setString(2, taxi.getCarNumber());
            statement.setBoolean(3, taxi.getBusy());
            setValueOrNull(statement, 4,taxi.getDriverId());
            setValueOrNull(statement, 5,taxi.getCarTypeId());
            setIdIfNotNull(statement, 6,taxi.getId());

        } catch (SQLException e) {
            throw new DaoException("Can't set statement properties for entity " + taxi, e);
        }
    }
}
