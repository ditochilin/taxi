import dao.*;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.*;
import dao.transactionManager.TransactionManagerImpl;
import entities.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FulfillDatabase {

    private static final Logger LOGGER = LogManager.getLogger(TestDAO.class.getName());

    final ITaxiDao taxiDao = TaxiDaoImpl.getInstance();
    final IUserDao userDao = UserDaoImpl.getInstance();
    final ICarTypeDao carTypeDao = CarTypeDaoImpl.getInstance();
    final IRoleDao roleDao = RoleDaoImpl.getInstance();
    final IShareDao shareDao = ShareDaoImpl.getInstance();
    final IOrderDao orderDao = OrderDaoImpl.getInstance();

    public List<Role> createRoles() {
        return Arrays.asList(new Role(), new Role(), new Role());
    }

    public List<User> createUsers() {
        return Arrays.asList(new User(), new User(), new User(), new User());
    }

    public List<CarType> createCarTypes() {
        return Arrays.asList(new CarType(), new CarType(), new CarType());
    }

    public List<Share> createShares() {
        return Arrays.asList(new Share(), new Share(), new Share());
    }

    public List<Taxi> createTaxis() {
        return Arrays.asList(new Taxi(), new Taxi(), new Taxi());
    }

    public List<Order> createOrders() {
        return Arrays.asList(new Order(), new Order(), new Order());
    }

    @Test
    public void insertRoles() throws DaoException {
        List<Role> roles = createRoles();
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

    @Test
    public void testFindRoleById() throws NoSuchEntityException, DaoException {
        Role role = roleDao.findByName("Admin");
        Assert.assertEquals(roleDao.findById(role.getId()),role);
    }

    @Test()
    public void testFindAllRoles(){
        try {
            List<Role> list = roleDao.findAll();
            Assert.assertEquals(3,list.size());
        } catch (DaoException e) {
            LOGGER.error("testFindAllRoles not works");
        }
    }

    @Test
    public void insertShares() throws DaoException {
        List<Share> shares = createShares();
        Share share1 = shares.get(0);
        share1.setShareName("loyalry_10");
        share1.setOn(true);
        share1.setSum(BigDecimal.valueOf(150));
        share1.setPercent(10);
        share1.setIsLoyalty(true);

        Share share2 = shares.get(1);
        share2.setShareName("Share_5");
        share2.setOn(true);
        share2.setSum(BigDecimal.valueOf(100));
        share2.setPercent(5);
        share2.setIsLoyalty(false);

        Share share3 = shares.get(2);
        share3.setShareName("Share_15");
        share3.setOn(true);
        share3.setSum(BigDecimal.valueOf(250));
        share3.setPercent(15);
        share3.setIsLoyalty(true);

        shareDao.insert(share1);
        shareDao.insert(share2);
        shareDao.insert(share3);
    }

    @Test
    public void insertCarTypes() throws DaoException {
        List<CarType> typeList = createCarTypes();
        CarType type1 = typeList.get(0);
        CarType type2 = typeList.get(1);
        CarType type3 = typeList.get(2);

        type1.setTypeName("Pikap");
        type2.setTypeName("Sedan");
        type3.setTypeName("Universal");

        type1.setPrice(BigDecimal.valueOf(55));
        type2.setPrice(BigDecimal.valueOf(40));
        type3.setPrice(BigDecimal.valueOf(65));

        carTypeDao.insert(type1);
        carTypeDao.insert(type2);
        carTypeDao.insert(type3);
    }

    @Test
    public void insertUsers() throws DaoException, NoSuchEntityException {
        List<User> users = createUsers();
        User currentUser = users.get(0);
        if(userDao.findByPhone("0672184141").isEmpty()) {
            currentUser.setRole(roleDao.findByName("Admin"));
            currentUser.setUserName("root");
            currentUser.setPassword("root");
            currentUser.setPhone("0672184141");
            userDao.insert(currentUser);
        }

        User currentUser1 = users.get(1);
        if(userDao.findByPhone("0502225547").isEmpty()) {
            currentUser1.setRole(roleDao.findByName("Client"));
            currentUser1.setUserName("Alex");
            currentUser1.setPassword("root");
            currentUser1.setPhone("0502225547");
            userDao.insert(currentUser1);
        }

        User currentUser2 = users.get(2);
        if(userDao.findByPhone("0678124422").isEmpty()) {
            currentUser2.setRole(roleDao.findByName("Driver"));
            currentUser2.setUserName("Ivan");
            currentUser2.setPassword("root");
            currentUser2.setPhone("0678124422");
            userDao.insert(currentUser2);
        }
        User currentUser3 = users.get(3);
        if(userDao.findByPhone("0679514720").isEmpty()) {
            currentUser3.setRole(roleDao.findByName("Driver"));
            currentUser3.setUserName("Николай");
            currentUser3.setPassword("root");
            currentUser3.setPhone("0679514720");
            userDao.insert(currentUser3);
        }
    }

    @Test
    public void findByRole() throws NoSuchEntityException, DaoException {
        List<User> users = userDao.findByRole(roleDao.findByName("Admin"));
        Assert.assertEquals(users.size(),1);
    }

    @Test
    public void findByPhone() throws NoSuchEntityException, DaoException {
        List<User> users = userDao.findByPhone("0672184141");
        Assert.assertEquals(users.get(0).getUserName(),"root");
    }

    @Test
    public void updateUsers() throws DaoException {
        User user = userDao.findByName("Alex").get(0);
        user.setPhone("0502225547");
        userDao.update(user);
    }

    @Test
    public void insertTaxi() throws DaoException {
        deleteTable("taxis");
        List<Taxi> taxis = createTaxis();
        Taxi taxi1 = taxis.get(0);
        Taxi taxi2 = taxis.get(1);
        Taxi taxi3 = taxis.get(2);

        taxi1.setDriver(userDao.findByName("Ivan").get(0));
        taxi2.setDriver(userDao.findByName("Николай").get(0));
        taxi3.setDriver(userDao.findByName("Ivan").get(0));

        try {
            taxi1.setCarType(carTypeDao.findByName("Pikap").get(0));
            taxi2.setCarType(carTypeDao.findByName("Sedan").get(0));
            taxi3.setCarType(carTypeDao.findByName("Universal").get(0));
        } catch (NoSuchEntityException e) {
            LOGGER.error(e.getMessage());
        }

        taxi1.setCarName("Honda");
        taxi2.setCarName("Ford");
        taxi3.setCarName("Kia");

        taxi1.setCarNumber("AA2345IO");
        taxi2.setCarNumber("AA2345IS");
        taxi3.setCarNumber("AA2345IF");

        taxi1.setBusy(true);

        taxiDao.insert(taxi1);
        taxiDao.insert(taxi2);
        taxiDao.insert(taxi3);
    }

    @Test
    public void checkBusy(){
        try {
            List<Taxi> taxis = taxiDao.findAllBusyFree(false);
            for (Taxi taxi: taxis) {
                taxiDao.turnBusyness(taxi,true);
                Assert.assertEquals(taxi.getBusy(),true);
                taxiDao.turnBusyness(taxi,false);
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR,e.getMessage());
        }
    }

    @Test
    public void insertOrders() throws DaoException, ParseException {
        List<Order> orders = createOrders();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss",Locale.ROOT);
        Order order1 = orders.get(0);
        Order order2 = orders.get(1);
        Order order3 = orders.get(2);

        order1.setStartPoint("Zejrevskogo 5");
        order1.setEndPoint("Kioto 1");
        order1.setClient(userDao.findByName("Alex").get(0));
        order1.setDistance(2645);
        order1.setFeedTime(dateformat.parse("24-08-2018 14:05:00"));

        order2.setStartPoint("Zodchih 5");
        order2.setEndPoint("Rudenko 7");
        order2.setClient(userDao.findByName("root").get(0));
        order2.setDistance(1950);
        order2.setCost(BigDecimal.valueOf(158));
        order2.setFeedTime(dateformat.parse("02-08-2018 19:17:00"));
        order2.setWaitingTime(25);
        order2.setTaxi(taxiDao.findByUser(userDao.findByName("Ivan").get(0)).get(0));
        order2.setStatus(Status.INWORK);
        order2.setDateTime(dateformat.parse("02-04-2018 00:11:42"));

        order3.setStartPoint("Khreshatik 25");
        order3.setEndPoint("Gonty 98");
        order3.setClient(userDao.findByName("root").get(0));
        order3.setDistance(4800);
        order3.setWaitingTime(15);
        order3.setCost(BigDecimal.valueOf(158));
        order3.setFeedTime(dateformat.parse("01-08-2018 21:00:00"));
        order3.setTaxi(taxiDao.findByUser(userDao.findByName("Ivan").get(0)).get(1));
        order3.setStatus(Status.INWORK);
        order3.setDateTime(dateformat.parse("20-08-2018 15:51:04"));

        orderDao.insert(order1);
        orderDao.insert(order2);
        orderDao.insert(order3);

    }

    @Test
    public void testUpdateOrder() throws NoSuchEntityException, DaoException {
        Order order = orderDao.findByClient(userDao.findByPhone("0502225547").get(0)).get(0);
        Taxi taxi = taxiDao.findByUser(userDao.findByPhone("0679514720").get(0)).get(0);
        order.setTaxi(taxi);
        orderDao.update(order);
        Order updatedOrder = orderDao.findById(order.getId());
        Assert.assertEquals(taxi, updatedOrder.getTaxi());
    }

    @Test
    public void testSharesInOrder() throws NoSuchEntityException, DaoException {
        Order order = orderDao.findByClient(userDao.findByPhone("0502225547").get(0)).get(0);

        List<Share> shares = shareDao.findAll();
        order.setShares(shares);
        orderDao.update(order);

        Order updatedOrder = orderDao.findById(order.getId());
        Assert.assertEquals(updatedOrder.getShares().size(),3);

        updatedOrder.getShares().remove(2);
        orderDao.update(updatedOrder);
        Order updatedOrder2 = orderDao.findById(order.getId());
        Assert.assertEquals(updatedOrder2.getShares().size(),2);


    }

//    @Test
//    public void deleteUsers() throws NoSuchEntityException, DaoException {
//        List<User> users = userDao.findAll();
//        for (User user:users
//                ) {
//            userDao.delete(userDao.findById(user.getId()));
//        }
//    }
//
//    @Test
//    public void deleteRolesByName() throws NoSuchEntityException, DaoException {
//        roleDao.delete(roleDao.findByName("Client"));
//        roleDao.delete(roleDao.findByName("Admin"));
//        roleDao.delete(roleDao.findByName("Driver"));
//    }
//
//    @Test
//    public void deleteSharesByName() throws NoSuchEntityException, DaoException {
//        List<Share> shares = shareDao.findAll();
//        for (Share share:shares
//             ) {
//            shareDao.delete(shareDao.findById(share.getId()));
//        }
//    }
//
//    @Test
//    public void deleteCarTypes() throws NoSuchEntityException, DaoException {
//        List<CarType> types = carTypeDao.findAll();
//        for (CarType type:types
//                ) {
//            carTypeDao.delete(carTypeDao.findById(type.getId()));
//        }
//    }
    @Test
    public void deleteTaxis() throws NoSuchEntityException, DaoException {
        List<Taxi> taxis = taxiDao.findAll();
        for (Taxi taxi:taxis
                ) {
            taxiDao.delete(taxiDao.findById(taxi.getId()));
        }
    }
//
//    @Test
//    public void fulfillBase() throws DaoException, NoSuchEntityException {
//
//        deleteTable("orders_shares");
//        deleteTable("orders");
//        deleteTable("taxis");
//        deleteTable("users");
//        deleteTable("roles");
//        deleteTable("car_types");
//        deleteTable("shares");
//
//    }

    private void deleteTable(String tableName) throws DaoException {
                TransactionManagerImpl.doInTransaction(()-> {
                    Connection connection = TransactionManagerImpl.getConnection();
            try (Statement statement = connection.createStatement()) {
                return statement.executeUpdate("DELETE FROM " + tableName);
            } catch (SQLException e) {
                throw new Exception(e.getMessage());
            }
        });
    }
}
