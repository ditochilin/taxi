package dao.propSetter;

import dao.exceptions.DaoException;
import entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserPropSetter implements IPropSetter<User> {
    @Override
    public void setProperties(PreparedStatement statement, User user) throws DaoException {
        try {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPhone());
            setValueOrNull(statement, 4,user.getRoleId());
            setIdIfNotNull(statement, 5,user.getId());

        } catch (SQLException e) {
            throw new DaoException("Can't set statement properties for entity " + user, e);
        }
    }
}
