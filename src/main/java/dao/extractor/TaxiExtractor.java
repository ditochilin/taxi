package dao.extractor;

import entities.Taxi;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxiExtractor implements IExtractor<Taxi> {
    @Override
    public Taxi extractEntityData(ResultSet resultSet) throws SQLException {
        Taxi taxi = new Taxi();
        taxi.setId(resultSet.getLong("id_taxi"));
        taxi.setCarName(resultSet.getString("car_name"));
        taxi.setCarNumber(resultSet.getString("car_number"));
        taxi.setBusy(resultSet.getBoolean("busy"));
        return taxi;
    }
}
