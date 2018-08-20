package dao.extractor;

import entities.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleExtractor implements IExtractor<Role> {

    @Override
    public Role extractEntityData(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong("id_role"));
        role.setRoleName(resultSet.getString("role_name"));
        role.setDescription(resultSet.getString("description"));
        return role;
    }

}
