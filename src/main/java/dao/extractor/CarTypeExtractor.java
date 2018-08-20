package dao.extractor;

import entities.CarType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarTypeExtractor implements IExtractor<CarType> {


    @Override
    public CarType extractEntityData(ResultSet resultSet) throws SQLException {
        CarType carType = new CarType();
        carType.setId(resultSet.getLong("id_car_type"));
        carType.setTypeName(resultSet.getString("type_name"));
        carType.setPrice(resultSet.getBigDecimal("price"));
        return carType;
    }

}
