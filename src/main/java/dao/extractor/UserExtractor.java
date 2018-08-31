package dao.extractor;

import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExtractor implements IExtractor<User> {

    @Override
    public User extractEntityData(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getLong("id_user"),
                resultSet.getString("phone"),
                resultSet.getString("password"),
                resultSet.getString("user_name"));
        return user;
    }

}
