import dao.*;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.*;
import entities.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.Optional.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FulfillDatabase {

    private static final Logger LOGGER = LogManager.getLogger(FulfillDatabase.class.getName());

    final ITaxiDao taxiDao = TaxiDaoImpl.getInstance();
    final IUserDao userDao = UserDaoImpl.getInstance();
    final ICarTypeDao carTypeDao = CarTypeDaoImpl.getInstance();
    final IRoleDao roleDao = RoleDaoImpl.getInstance();
    final IShareDao shareDao = ShareDaoImpl.getInstance();
    final IOrderDao orderDao = OrderDaoImpl.getInstance();

    List<Role> roles = null;
    List<CarType> carTypes = null;
    List<Share> shares = null;
    List<User> users = null;
    List<Taxi> taxis = null;
    List<Order> orders = null;

    @Test
    public void testFullBase() throws DaoException, ParseException, NoSuchEntityException {
        deleteAll();
        fulfillRoles();
        insertRoles();
        fulfillCarTypes();
        insertCarTypes();
        fulfillShares();
        insertShares();
        fulfillUsers();
        insertUsers();
        fulfillTaxis();
        insertTaxis();
        fulfillOrders();
        insertOrders();
    }

    //@Test
    public void deleteAll() throws DaoException, NoSuchEntityException {
        testDeleteOrders();
        testDeleteTaxis();
        testDeleteUsers();
        testDeleteShares();
        testDeleteRoles();
        testDeleteCarTypes();
    }

    public void fulfillRoles() {
        roles = Arrays.asList(new Role(), new Role(), new Role());
        roles.get(0).setRoleName("ADMIN");
        roles.get(0).setDescription("Administrator");

        roles.get(1).setRoleName("CLIENT");
        roles.get(1).setDescription("Client (buyer)");

        roles.get(2).setRoleName("DRIVER");
        roles.get(2).setDescription("Taxi driver");
    }

    public void fulfillUsers() throws NoSuchEntityException, DaoException {
        users = Arrays.asList(new User(),
                new User(),
                new User(),
                new User());
        User currentUser = users.get(0);
        if (userDao.findByPhone("+380672184141").isEmpty()) {
            currentUser.setRole(roleDao.findByName("ADMIN"));
            currentUser.setUserName("root");
            currentUser.setPassword("root");
            currentUser.setPhone("+380672184141");
        }

        User currentUser1 = users.get(1);
        if (userDao.findByPhone("+380502225547").isEmpty()) {
            currentUser1.setRole(roleDao.findByName("CLIENT"));
            currentUser1.setUserName("Alex");
            currentUser1.setPassword("root");
            currentUser1.setPhone("+380502225547");
        }

        User currentUser2 = users.get(2);
        if (userDao.findByPhone("+380678124422").isEmpty()) {
            currentUser2.setRole(roleDao.findByName("DRIVER"));
            currentUser2.setUserName("Ivan");
            currentUser2.setPassword("root");
            currentUser2.setPhone("+380678124422");
        }
        User currentUser3 = users.get(3);
        if (userDao.findByPhone("+380679514720").isEmpty()) {
            currentUser3.setRole(roleDao.findByName("DRIVER"));
            currentUser3.setUserName("Николай");
            currentUser3.setPassword("root");
            currentUser3.setPhone("+380679514720");
        }
    }

    public void fulfillCarTypes() {
        carTypes = Arrays.asList(new CarType(), new CarType(), new CarType());
        CarType type1 = carTypes.get(0);
        CarType type2 = carTypes.get(1);
        CarType type3 = carTypes.get(2);

        type1.setTypeName("Pikap");
        type2.setTypeName("Sedan");
        type3.setTypeName("Universal");

        type1.setPrice(BigDecimal.valueOf(55));
        type2.setPrice(BigDecimal.valueOf(40));
        type3.setPrice(BigDecimal.valueOf(65));
    }

    public void fulfillShares() {
        shares = Arrays.asList(new Share(),
                               new Share(),
                               new Share());

        Share share1 = shares.get(0);
        share1.setShareName("loyalry_10");
        share1.setIsOn(true);
        share1.setSum(BigDecimal.valueOf(150));
        share1.setPercent(10f);
        share1.setIsLoyalty(true);

        Share share2 = shares.get(1);
        share2.setShareName("Share_5");
        share2.setIsOn(true);
        share2.setSum(BigDecimal.valueOf(100));
        share2.setPercent(5f);
        share2.setIsLoyalty(false);

        Share share3 = shares.get(2);
        share3.setShareName("Share_15");
        share3.setIsOn(true);
        share3.setSum(BigDecimal.valueOf(250));
        share3.setPercent(15f);
        share3.setIsLoyalty(true);

    }

    public void fulfillTaxis() throws DaoException {
        taxis = Arrays.asList(new Taxi(), new Taxi(), new Taxi());
        Taxi taxi1 = taxis.get(0);
        Taxi taxi2 = taxis.get(1);
        Taxi taxi3 = taxis.get(2);
        try {
            taxi1.setDriver(userDao.findByName("Ivan").get(0));
            taxi2.setDriver(userDao.findByName("Николай").get(0));
            taxi3.setDriver(userDao.findByName("Ivan").get(0));

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
    }

    public void fulfillOrders() throws ParseException, DaoException, NoSuchEntityException {
        orders = Arrays.asList(new Order(), new Order(), new Order());
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ROOT);
        Order order1 = orders.get(0);
        Order order2 = orders.get(1);
        Order order3 = orders.get(2);

        order1.setStartPoint("Zejrevskogo 5");
        order1.setEndPoint("Kioto 1");
        order1.setClient(userDao.findByName("Alex").get(0));
        order1.setDistance(2645);
        order1.setFeedTime(dateformat.parse("24-08-2018 14:05:00"));
        order1.setStatus(Status.CREATED);
        order1.setCarType(carTypeDao.findByName("Universal").get(0));

        order2.setStartPoint("Zodchih 5");
        order2.setEndPoint("Rudenko 7");
        order2.setClient(userDao.findByName("root").get(0));
   //     order2.setDistance(1950);
        order2.setCost(BigDecimal.valueOf(158));
        order2.setFeedTime(dateformat.parse("02-08-2018 19:17:00"));
        order2.setWaitingTime(25);
        order2.setDiscount(15);   // 15 %
        order2.setTaxi(taxiDao.findByUser(userDao.findByName("Ivan").get(0)).get(0));
        order2.setStatus(Status.INWORK);
        order2.setDateTime(dateformat.parse("02-04-2018 00:11:42"));
        order2.setCarType(carTypeDao.findByName("Universal").get(0));

        order3.setStartPoint("Khreshatik 25");
        order3.setEndPoint("Gonty 98");
        order3.setClient(userDao.findByName("root").get(0));
        order3.setDistance(4800);
        order3.setWaitingTime(15);
        order2.setDiscount(7);   // 7 %
        order3.setCost(BigDecimal.valueOf(158));
        order3.setFeedTime(dateformat.parse("01-08-2018 21:00:00"));
        order3.setTaxi(taxiDao.findByUser(userDao.findByName("Ivan").get(0)).get(1));
        order3.setStatus(Status.INWORK);
        order3.setDateTime(dateformat.parse("20-08-2018 15:51:04"));
        order3.setShares(Arrays.asList(shareDao.findByName("Share_15").get(0),
                                        shareDao.findByName("loyalry_10").get(0)));
        order3.setCarType(carTypeDao.findByName("Pikap").get(0));

    }

    public void insertRoles() throws DaoException {
        roleDao.insert(roles.get(0));
        roleDao.insert(roles.get(1));
        roleDao.insert(roles.get(2));
    }

    @Test
    public void testFindRoleById() throws NoSuchEntityException, DaoException {
        Role role = roleDao.findByName("CLIENT");
        Assert.assertEquals(roleDao.findById(role.getId()), role);
    }

    @Test()
    public void testFindAllRoles() {
        try {
            List<Role> list = roleDao.findAll();
            Assert.assertEquals(3, list.size());
        } catch (DaoException e) {
            LOGGER.error("testFindAllRoles not works");
        }
    }

    @Test
    public void findOrderByCarType() throws NoSuchEntityException, DaoException {
        List<Order> orders = orderDao.findByCarType(carTypeDao.findByName("PIKAP").get(0));
        Assert.assertEquals(1, orders.size());
    }

    public void insertShares() throws DaoException {
        shareDao.insert(shares.get(0));
        shareDao.insert(shares.get(1));
        shareDao.insert(shares.get(2));
    }

    public void insertCarTypes() throws DaoException {
        carTypeDao.insert(carTypes.get(0));
        carTypeDao.insert(carTypes.get(1));
        carTypeDao.insert(carTypes.get(2));
    }

    public void insertUsers() throws DaoException {
        userDao.insert(users.get(0));
        userDao.insert(users.get(1));
        userDao.insert(users.get(2));
        userDao.insert(users.get(3));
    }

    @Test
    public void findByRole() throws NoSuchEntityException, DaoException {
        List<User> users = userDao.findByRole(roleDao.findByName("ADMIN"));
        Assert.assertEquals(users.size(), 1);
    }

    @Test
    public void findByPhone() throws DaoException {
        List<User> users = userDao.findByPhone("+380672184141");
        Assert.assertEquals(users.get(0).getUserName(), "root");
    }

    @Test
    public void updateUsers() throws DaoException {
        User user = userDao.findByName("Alex").get(0);
        user.setPhone("+380502225547");
        userDao.update(user);
    }


    public void insertTaxis() throws DaoException {
        taxiDao.insert(taxis.get(0));
        taxiDao.insert(taxis.get(1));
        taxiDao.insert(taxis.get(2));
    }

    @Test
    public void checkBusy() {
        try {
            List<Taxi> taxis = taxiDao.findAllBusyFree(false);
            for (Taxi taxi : taxis) {
                taxiDao.turnBusyness(taxi, true);
                Assert.assertEquals(taxi.getBusy(), true);
                taxiDao.turnBusyness(taxi, false);
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }


    public void insertOrders() throws DaoException {
        orderDao.insert(orders.get(0));
        orderDao.insert(orders.get(1));
        orderDao.insert(orders.get(2));
    }

    @Test
    public void testUpdateOrder() throws NoSuchEntityException, DaoException {
        Order order = orderDao.findByClient(userDao.findByPhone("+380502225547").get(0)).get(0);
        Taxi taxi = taxiDao.findByUser(userDao.findByPhone("+380679514720").get(0)).get(0);
        order.setTaxi(taxi);
        orderDao.update(order);
        Order updatedOrder = orderDao.findById(order.getId());
        Assert.assertEquals(taxi, updatedOrder.getTaxi());
    }

    @Test
    public void testCheckOrderIfItHasDiscount() throws DaoException {
        Order order = orderDao.findByClient(userDao.findByPhone("+380502225547").get(0)).get(0);
        Assert.assertEquals(0, order.getDiscount().intValue());

        List<Order> ordersRoot = orderDao.findByClient(userDao.findByPhone("+380672184141").get(0));
        Assert.assertEquals(2,ordersRoot.size());

        Assert.assertEquals(7,ordersRoot.get(0).getDiscount().intValue());
    }

        @Test
    public void testSharesInOrder() throws NoSuchEntityException, DaoException {
        Order order = orderDao.findByClient(userDao.findByPhone("+380502225547").get(0)).get(0);

        List<Share> shares = shareDao.findAll();
        order.setShares(shares);
        order.setStatus(Status.INWORK);
        orderDao.update(order);

        Order updatedOrder = orderDao.findById(order.getId());
        Assert.assertEquals(updatedOrder.getShares().size(), 3);

        updatedOrder.getShares().remove(2);
        orderDao.update(updatedOrder);
        Order updatedOrder2 = orderDao.findById(order.getId());
        Assert.assertEquals(updatedOrder2.getShares().size(), 2);
    }

    @Test
    public void ztestFindCreated() throws DaoException {
        List<Order> orders = orderDao.findByStatus(Status.INWORK);
        Assert.assertTrue(orders.size() > 0);
    }

    @Test
    public void testLogin() throws DaoException {
        User logUser = userDao.findByName("root").get(0);
        List<User> users = userDao.findAll();
        List<Share> shares = shareDao.findAll();
    }

    @Test
    public void testDeleteOrders() throws DaoException {
        List<Order> orders = orderDao.findAll();
        for (Order order : orders
                ) {
            orderDao.delete(order.getId());
        }
    }

    //@Test
    public void testDeleteTaxis() throws DaoException {
        List<Taxi> taxis = taxiDao.findAll();
        for (Taxi taxi : taxis
                ) {
            taxiDao.delete(taxi.getId());
        }
    }

    //@Test
    public void testDeleteUsers() throws DaoException {
        List<User> users = userDao.findAll();
        for (User user : users
                ) {
            userDao.delete(user.getId());
        }
    }

    //@Test
    public void testDeleteRoles() throws NoSuchEntityException, DaoException {
        Role role = roleDao.findByName("CLIENT");
        if(role!=null) {
            roleDao.delete(role.getId());
        }
        Role role2 = roleDao.findByName("ADMIN");
        if(role2!=null) {
            roleDao.delete(role2.getId());
        }
        Role role3 = roleDao.findByName("DRIVER");
        if(role3!=null) {
            roleDao.delete(role3.getId());
        }
    }

    //@Test
    public void testDeleteShares() throws DaoException {
        List<Share> shares = shareDao.findAll();
        for (Share share : shares
                ) {
            shareDao.delete(share.getId());
        }
    }

    //@Test
    public void testDeleteCarTypes() throws NoSuchEntityException, DaoException {
        List<CarType> types = carTypeDao.findAll();
        for (CarType type : types
                ) {
            carTypeDao.delete(carTypeDao.findById(type.getId()).getId());
        }
    }
}
