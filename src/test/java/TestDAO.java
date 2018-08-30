import dao.*;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import dao.implementation.*;
import dao.transactionManager.TransactionManagerImpl;
import entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestDAO {

    private static final Logger LOGGER = LogManager.getLogger(TestDAO.class.getName());

    final ITaxiDao taxiDao = TaxiDaoImpl.getInstance();
    final IUserDao userDao = UserDaoImpl.getInstance();
    final ICarTypeDao carTypeDao = CarTypeDaoImpl.getInstance();
    final IRoleDao roleDao = RoleDaoImpl.getInstance();
    final IShareDao shareDao = ShareDaoImpl.getInstance();
    final IOrderDao OrderDao = OrderDaoImpl.getInstance();

    @Test
    public void rolesTest() {
        IRoleDao roleDao = RoleDaoImpl.getInstance();
        Role role1 = new Role();
        Role role2 = new Role();
        Role role3 = new Role();

        try {
            fulfillRoles(role1, role2, role3);

            role1.setRoleName("ТестАдмин");
            role1.setDescription("ТестАдминистратор");
            roleDao.update(role1);

            LOGGER.debug("Try to find by id: " + role3.getId());
            LOGGER.debug(roleDao.findById(role3.getId()));

            try {
                LOGGER.debug("Try to find by id that is not exist: " + roleDao.findById(9999999L));
            } catch (Exception e) {
                LOGGER.debug("Not found");
            }
            LOGGER.debug("Try to delete by id: " + role3.getId());
            roleDao.delete(role3.getId());

            LOGGER.debug("Get all:");
            List<Role> roles = roleDao.findAll();
            roles.stream().forEach(LOGGER::debug);

            LOGGER.debug("Find by name 'CLIENT':" + roleDao.findByName("CLIENT"));

            roleDao.delete(role1.getId());
            roleDao.delete(role2.getId());

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void sharesTest() {
        IShareDao shareDao = ShareDaoImpl.getInstance();
        Share share1 = new Share();
        Share share2 = new Share();
        Share share3 = new Share();

        try {
            fulfillShares(share1, share2, share3);

            share1.setShareName("Loyalty_6.5");
            share1.setIsLoyalty(true);
            share1.setSum(BigDecimal.valueOf(200));
            share1.setPercent(6.5f);
            share1.setIsOn(false);
            shareDao.update(share1);

            Share shareTemp = shareDao.findById(share1.getId());
            Assert.assertEquals(share1.getId(), shareTemp.getId());

            try {
                LOGGER.debug("Try to find by id that is not exist: " + shareDao.findById(9999999L));
            } catch (Exception e) {
                LOGGER.debug("Not found");
            }
            LOGGER.debug("Try to delete by id: " + share3.getId());
            shareDao.delete(share3.getId());


            LOGGER.debug("Get all:");
            List<Share> shares = shareDao.findAll();
            Assert.assertEquals(shares.size(), 2);
            shares.stream().forEach(LOGGER::debug);

            List<Share> shareList = shareDao.findSharesByOrder(new Order());
            Assert.assertEquals(shareList.size(), 0);

            shareDao.delete(share1.getId());
            shareDao.delete(share2.getId());

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

    @Test
    public void testOrders() {

        Role role1 = new Role();
        Role role2 = new Role();
        Role role3 = new Role();

        CarType carType1 = new CarType();
        CarType carType2 = new CarType();
        CarType carType3 = new CarType();
        Order Order1 = new Order();
        Order Order2 = new Order();
        Order Order3 = new Order();
        Share share1 = new Share();
        Share share2 = new Share();
        Share share3 = new Share();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        Taxi taxi1 = new Taxi();
        Taxi taxi2 = new Taxi();
        Taxi taxi3 = new Taxi();

        try {
//            fulfillRoles(role1, role2, role3);
//            fulfillCarTypes(carType1, carType2, carType3);
//            fulfillUsers(user1, user2, user3);
//            fulfillShares(share1, share2, share3);
//
//            fulfillTaxis(taxi1, taxi2, taxi3);
//
//            SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
////
//            Order1.setShares(Arrays.asList(share1, share2));
//            Order1.setStatus(Status.INWORK);
//            Order1.setTaxi(taxi1);
//            Order1.setClient(user1);
//            Order1.setStartPoint("address 1");
//            Order1.setEndPoint("address 2");
//            Order1.setFeedTime(Time.valueOf("00:15:00"));
//            Order1.setDistance(1245);
//            Order1.setDateTime(dateformat.parse("02-04-2018 00:11:42"));
//            Order1.setCost(BigDecimal.valueOf(170));
//
//            Order2.setShares(Arrays.asList(share2));
//            Order2.setStatus(Status.CREATED);
//            Order2.setTaxi(taxi2);
//            Order2.setClient(user2);
//            Order2.setStartPoint("address 3");
//            Order2.setEndPoint("address 4");
//            Order2.setFeedTime(Time.valueOf("00:10:00"));
//            Order2.setDistance(1100);
//            Order2.setDateTime(dateformat.parse("02-05-2018 10:21:42"));
//            Order2.setCost(BigDecimal.valueOf(120));
//
//            Order3.setShares(Arrays.asList(share1, share3));
//            Order3.setStatus(Status.REJECTED);
//            Order3.setTaxi(taxi3);
//            Order3.setClient(user2);
//            Order3.setStartPoint("address 5");
//            Order3.setEndPoint("address 6");
//            Order3.setFeedTime(Time.valueOf("00:20:00"));
//            Order3.setDistance(1500);
//            Order3.setDateTime(dateformat.parse("02-08-2018 18:35:42"));
//            Order3.setCost(BigDecimal.valueOf(200));
//
//
//            OrderDao.insert(Order1);
//            OrderDao.insert(Order2);
//            OrderDao.insert(Order3);
//
//            User testDriver = userDao.findByPhone("80672229911").get(0);
//            List<Order> orders  = OrderDao.getByDriver(testDriver);
//            orders.stream().forEach(LOGGER::debug);
//
//            User testClient = userDao.findByPhone("80672224455").get(0);
//            List<Order> ordersClient  = OrderDao.getByClient(testClient);
//            ordersClient.stream().forEach(LOGGER::debug);
//
//            Order order = OrderDao.findById(Order1.getId());
//            Assert.assertEquals(order.getId(),Order1.getId());
//
//
//            order.setCost(BigDecimal.valueOf(999));
//            order.addShare(shareDao.findById(321L));
//
//            OrderDao.update(order);

            //OrderDao.delete(order);
//
//        } catch (NoSuchEntityException e) {
//            e.printStackTrace();
        } finally {
            clearAllTables();
        }
    }

    private void fulfillShares(Share... shares) throws DaoException {
        shares[0].setShareName("Loyalty_5.5");
        shares[0].setIsLoyalty(true);
        shares[0].setSum(BigDecimal.valueOf(300));
        shares[0].setPercent(5.5f);
        shares[0].setIsOn(false);
        shareDao.insert(shares[0]);

        shares[1].setShareName("Loyalty_3.5");
        shares[1].setIsLoyalty(true);
        shares[1].setSum(BigDecimal.valueOf(190));
        shares[1].setPercent(3.5f);
        shares[1].setIsOn(true);
        shareDao.insert(shares[1]);

        shares[2].setShareName("Share_1.5");
        shares[2].setIsLoyalty(false);
        shares[2].setSum(BigDecimal.valueOf(150));
        shares[2].setPercent(1.5f);
        shares[2].setIsOn(true);
        shareDao.insert(shares[2]);
    }

    @Test
    public void carTypeTest() {
        CarType carType1 = new CarType();
        CarType carType2 = new CarType();
        CarType carType3 = new CarType();

        try {
            fulfillCarTypes(carType1, carType2, carType3);

            carType1.setTypeName("Седан");
            carType1.setPrice(BigDecimal.valueOf(50));
            carTypeDao.update(carType1);

            LOGGER.debug("Try to find by id: " + carType3.getId());
            CarType carTypeForSearch = carTypeDao.findById(carType3.getId());
            Assert.assertEquals(carType3, carTypeForSearch);

            try {
                LOGGER.debug("Try to find by id that is not exist: " + carTypeDao.findById(9999999L));
            } catch (Exception e) {
                LOGGER.debug("Not found");
            }
            LOGGER.debug("Try to delete by id: " + carType3.getId());
            carTypeDao.delete(carType3.getId());


            LOGGER.debug("Get all:");
            List<CarType> carTypes = carTypeDao.findAll();
            carTypes.stream().forEach(LOGGER::debug);

            LOGGER.debug("Find by name 'Sedan':" + carTypeDao.findByName("Sedan"));

            carTypeDao.delete(carType1.getId());
            carTypeDao.delete(carType2.getId());

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void usersTest() {
        final UserDaoImpl userDao = UserDaoImpl.getInstance();
        RoleDaoImpl roleDao = RoleDaoImpl.getInstance();
        Role role1 = new Role();
        Role role2 = new Role();
        Role role3 = new Role();

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        try {
            // create
            fulfillRoles(role1, role2, role3);
            fulfillUsers(user1, user2, user3);
            // Read
            List<User> users = userDao.findAll();
            users.stream().forEach(System.out::println);

            // Update
            user2.setPhone("80503334455");
            userDao.update(user2);

            User userForSearch = userDao.findByPhone("80503334455").get(0);
            Assert.assertEquals(user2, userForSearch);

            User newUser2 = userDao.findById(user2.getId());
            Assert.assertEquals(user2.getPhone(), newUser2.getPhone());

            Role role1ForSearch = roleDao.findByName("testClient");
            List<User> list = userDao.findByRole(role1ForSearch);
            Assert.assertEquals(user1.getId(), list.get(0).getId());

        } catch (DaoException e) {
            //  e.printStackTrace();
        } catch (NoSuchEntityException e) {
            //  e.printStackTrace();
        } finally {
            try {
                deleteUsers(Arrays.asList(user1, user2, user3));
                deleteRoles(Arrays.asList(role1, role2, role3));
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void taxiTest() {

        Role role1 = new Role();
        Role role2 = new Role();
        Role role3 = new Role();

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        CarType carType1 = new CarType();
        CarType carType2 = new CarType();
        CarType carType3 = new CarType();

        Taxi taxi1 = new Taxi();
        Taxi taxi2 = new Taxi();
        Taxi taxi3 = new Taxi();

        try {
            // create
            fulfillRoles(role1, role2, role3);
            fulfillUsers(user1, user2, user3);
            fulfillCarTypes(carType1, carType2, carType3);
            fulfillTaxis(taxi1, taxi2, taxi3);

            // Read
            List<Taxi> taxis = taxiDao.findAll();
            taxis.stream().forEach(System.out::println);

            List<Taxi> taxisBusy = taxiDao.findAllBusyFree(true);
            taxisBusy.stream().forEach(System.out::println);
            Assert.assertArrayEquals(taxisBusy.toArray(), new Taxi[]{taxi3});

            List<Taxi> taxisByUser = taxiDao.findByUser(userDao.findByPhone("80672224455").get(0));
            taxisByUser.stream().forEach(System.out::println);
            Assert.assertEquals(taxi2, taxisByUser.get(0));

            Taxi taxisById = taxiDao.findById(taxi1.getId());
            Assert.assertEquals(taxisById, taxi1);

            // Update
            Long idTest = taxi1.getId();
            taxi1.setCarName("BMW 318i");
            taxi1.setDriver(userDao.findByPhone("80672224455").get(0));
            taxi1.setCarType(carTypeDao.findByName("Pikap").get(0));
            taxiDao.update(taxi1);
            Taxi edTaxi = taxiDao.findById(idTest);
            Assert.assertEquals(taxi1, edTaxi);
            Assert.assertEquals(taxi1.getCarName(), edTaxi.getCarName());
            Assert.assertEquals(taxi1.getCarNumber(), edTaxi.getCarNumber());
            Assert.assertEquals(taxi1.getCarType(), edTaxi.getCarType());
            Assert.assertEquals(taxi1.getDriver(), edTaxi.getDriver());

        } catch (DaoException e) {
            //  e.printStackTrace();
        } catch (NoSuchEntityException e) {
            //  e.printStackTrace();
        } finally {
            try {
                deleteTaxis(Arrays.asList(taxi1, taxi2, taxi3));
                deleteUsers(Arrays.asList(user1, user2, user3));
                deleteCarTypes(Arrays.asList(carType1, carType2, carType3));
                deleteRoles(Arrays.asList(role1, role2, role3));
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
    }

    private void fulfillTaxis(Taxi... taxis) throws DaoException, NoSuchEntityException {
        taxis[0].setCarType(carTypeDao.findByName("Sedan").get(0));
        taxis[0].setCarNumber("AA5987ED");
        taxis[0].setCarName("Scoda Oktavia");
        taxis[0].setBusy(false);
        taxis[0].setDriver(userDao.findByRole(roleDao.findByName("testDriver")).get(0));
        taxiDao.insert(taxis[0]);

        taxis[1].setCarType(carTypeDao.findByName("Cupe").get(0));
        taxis[1].setCarNumber("BB9875GF");
        taxis[1].setBusy(false);
        taxis[1].setCarName("Mazda 6");
        taxis[1].setDriver(userDao.findByPhone("80672224455").get(0));
        taxiDao.insert(taxis[1]);

        taxis[2].setCarType(carTypeDao.findByName("Sedan").get(0));
        taxis[2].setBusy(true);
        taxis[2].setCarNumber("AA5444ED");
        taxis[2].setCarName("Saab");
        taxis[2].setDriver(userDao.findByRole(roleDao.findByName("testDriver")).get(0));
        taxiDao.insert(taxis[2]);

    }

    private void fulfillCarTypes(CarType... carTypes) throws DaoException {
        carTypes[0].setTypeName("Sedan");
        carTypes[0].setPrice(BigDecimal.valueOf(34));
        carTypeDao.insert(carTypes[0]);

        carTypes[1].setTypeName("Cupe");
        carTypes[1].setPrice(BigDecimal.valueOf(42));
        carTypeDao.insert(carTypes[1]);

        carTypes[2].setTypeName("Pikap");
        carTypes[2].setPrice(BigDecimal.valueOf(47));
        carTypeDao.insert(carTypes[2]);
    }

    @Test
    public void clearAllTables() {
        try (Connection connection = TransactionManagerImpl.getConnection();
             Statement statement = connection.createStatement()) {
            int resultSet7 = statement.executeUpdate("DELETE FROM orders_shares");
            int resultSet = statement.executeUpdate("DELETE FROM orders");
            int resultSet6 = statement.executeUpdate("DELETE FROM shares");
            int resultSet5 = statement.executeUpdate("DELETE FROM taxis");
            int resultSet4 = statement.executeUpdate("DELETE FROM users");
            int resultSet2 = statement.executeUpdate("DELETE FROM roles");
            int resultSet3 = statement.executeUpdate("DELETE FROM car_types");

        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fulfillUsers(User... users) throws DaoException, NoSuchEntityException {
        users[0].setUserName("client1");
        users[0].setPassword("123");
        users[0].setPhone("80672224455");
        users[0].setRole(roleDao.findByName("testClient"));
        userDao.insert(users[0]);

        users[1].setRole(roleDao.findByName("testAdmin"));
        users[1].setUserName("client2");
        users[1].setPassword("123");
        users[1].setPhone("80672228877");
        userDao.insert(users[1]);

        users[2].setRole(roleDao.findByName("testDriver"));
        users[2].setUserName("driver3");
        users[2].setPassword("123");
        users[2].setPhone("80672229911");
        userDao.insert(users[2]);
    }

    private void fulfillRoles(Role... roles) throws DaoException {
        roles[0].setRoleName("testAdmin");
        roles[0].setDescription("testAdministrator");
        roleDao.insert(roles[0]);

        roles[1].setRoleName("testClient");
        roles[1].setDescription("Client (buyer)");
        roleDao.insert(roles[1]);

        roles[2].setRoleName("testDriver");
        roles[2].setDescription("Taxi driver");
        roleDao.insert(roles[2]);

    }

    private void deleteRoles(List<Role> roles) throws DaoException {
        for (Role role : roles) {
            roleDao.delete(role.getId());
        }
    }

    private void deleteCarTypes(List<CarType> carTypes) throws DaoException {
        for (CarType carType : carTypes) {
            carTypeDao.delete(carType.getId());
        }
    }

    private void deleteUsers(List<User> users) throws DaoException {
        for (User user : users) {
            userDao.delete(user.getId());
        }
    }

    private void deleteTaxis(List<Taxi> taxis) throws DaoException {
        for (Taxi taxi : taxis) {
            taxiDao.delete(taxi.getId());
        }
    }

    private void deleteShares(List<Share> shares) throws DaoException {
        for (Share share : shares) {
            shareDao.delete(share.getId());
        }
    }


    @Test
    public void speedTestRole() throws Exception {
        IRoleDao roleDao = RoleDaoImpl.getInstance();
        int COUNT = 100;

        fulfillRoles(new Role(), new Role(), new Role());
        List<Long> time = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            long tic = System.nanoTime();

            List<Role> roles = roleDao.findAll();

            LOGGER.debug(roles.get(0).toString());
            long tac = System.nanoTime();
            long diff = (tac - tic) / 1000;
            LOGGER.debug(diff);
            time.add(diff);
        }

        Collections.sort(time);
        long sum = 0;
        for (int i = COUNT / 10; i < COUNT - COUNT / 10; i++) {
            sum += time.get(i);
        }

        clearAllTables();
        LOGGER.debug("avg = " + 10 * sum / (COUNT * 8));
    }
}
