package dao.enricher;

import dao.IRoleDao;
import dao.exceptions.NoSuchEntityException;
import entities.Role;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEnricher implements IEnricher<User> {

    private static IRoleDao roleDao;

    public UserEnricher(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void enrich(User user, ResultSet resultSet) throws SQLException, NoSuchEntityException {
        if (user.getRole() != null) {
            return;
        }
        Role role = roleDao.findById(resultSet.getLong("id_role"));
        user.setRole(role);
    }
}
