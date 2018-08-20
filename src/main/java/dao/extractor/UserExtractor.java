package dao.extractor;

import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExtractor implements IExtractor<User> {

    @Override
    public User extractEntityData(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id_user"));
        user.setPhone(resultSet.getString("phone"));
        user.setPassword(resultSet.getString("password"));
        user.setUserName(resultSet.getString("user_name"));
        return user;
    }

}
