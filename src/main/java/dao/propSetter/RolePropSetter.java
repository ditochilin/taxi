package dao.propSetter;

import dao.exceptions.DaoException;
import entities.Role;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RolePropSetter implements IPropSetter<Role> {

    public void setProperties(PreparedStatement statement, Role role) throws DaoException {
        try {
            statement.setString(1, role.getRoleName());
            statement.setString(2, role.getDescription());
            setIdIfNotNull(statement,3, role.getId());
        } catch (SQLException e) {
            throw new DaoException("Can't set statement properties for entity " + role, e);
        }
    }

}
