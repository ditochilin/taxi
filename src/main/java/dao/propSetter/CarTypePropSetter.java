package dao.propSetter;

import dao.exceptions.DaoException;
import entities.CarType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarTypePropSetter implements IPropSetter<CarType> {
    @Override
    public void setProperties(PreparedStatement statement, CarType carType) throws DaoException {
        try {
            statement.setString(1, carType.getTypeName());
            statement.setBigDecimal(2, carType.getPrice());
            setIdIfNotNull(statement,3, carType.getId());
        } catch (SQLException e) {
            throw new DaoException("Can't set statement properties for entity " + carType, e);
        }
    }
}
