import dao.*;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.*;
import dao.transactionManager.TransactionManagerImpl;
import entities.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class fulfillDatabase {

    final ITaxiDao taxiDao = TaxiDaoImpl.getInstance();
    final IUserDao userDao = UserDaoImpl.getInstance();
    final ICarTypeDao carTypeDao = CarTypeDaoImpl.getInstance();
    final IRoleDao roleDao = RoleDaoImpl.getInstance();
    final IShareDao shareDao = ShareDaoImpl.getInstance();
    final ITaxiOrderDao taxiOrderDao = TaxiOrderDaoImpl.getInstance();

    @Test
    public void fulfillBase() throws DaoException, NoSuchEntityException {
        deleteUsers();
        deleteRoles();

        insertRoles(createRoles());
        insertUsers(createUsers());
    }

    public List<Role> createRoles(){
        return Arrays.asList(new Role(),new Role(),new Role());
    }

    public List<User> createUsers(){
        return Arrays.asList(new User(),new User(),new User());
    }

    public List<CarType> createCarTypes(){
        return Arrays.asList(new CarType(),new CarType(),new CarType());
    }

    public List<Share> createShares(){
        return Arrays.asList(new Share(),new Share(),new Share());
    }

    public List<Taxi> createTaxis(){
        return Arrays.asList(new Taxi(),new Taxi(),new Taxi());
    }

    public List<TaxiOrder> createOrders(){
        return Arrays.asList(new TaxiOrder(),new TaxiOrder(),new TaxiOrder());
    }

    public void insertRoles(List<Role> roles) throws DaoException {
        roles.get(0).setRoleName("Admin");
        roles.get(0).setDescription("Administrator");
        roleDao.insert(roles.get(0));

        roles.get(1).setRoleName("Client");
        roles.get(1).setDescription("Client (buyer)");
        roleDao.insert(roles.get(1));

        roles.get(2).setRoleName("Driver");
        roles.get(2).setDescription("Taxi driver");
        roleDao.insert(roles.get(2));
    }

    public void insertUsers(List<User> users) throws DaoException, NoSuchEntityException {
        User currentUser = users.get(0);
        currentUser.setRole(roleDao.findByName("Admin"));
        currentUser.setUserName("root");
        currentUser.setPassword("root");
        currentUser.setPhone("0678124444");
        userDao.insert(currentUser);

        User currentUser1 = users.get(1);
        currentUser1.setRole(roleDao.findByName("Client"));
        currentUser1.setUserName("Alex");
        currentUser1.setPassword("root");
        currentUser1.setPhone("0678124433");
        userDao.insert(currentUser1);

        User currentUser2 = users.get(2);
        currentUser2.setRole(roleDao.findByName("Driver"));
        currentUser2.setUserName("Ivan");
        currentUser2.setPassword("root");
        currentUser2.setPhone("0678124422");
        userDao.insert(currentUser2);
    }

    @Test
    public void deleteRoles() {
        try (Connection connection = TransactionManagerImpl.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM roles");
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteUsers() {
        try (Connection connection = TransactionManagerImpl.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users");
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
