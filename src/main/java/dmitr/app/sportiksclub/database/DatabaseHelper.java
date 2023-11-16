package dmitr.app.sportiksclub.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import dmitr.app.sportiksclub.model.*;
import dmitr.app.sportiksclub.util.Role;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {

    private static final Dao<User, Integer> userDao;
    private static final Dao<Person, Integer> personDao;
    private static final Dao<MembershipType, Integer> membershipTypeDao;
    private static final Dao<Membership, Integer> membershipDao;
    private static final Dao<Employee, Integer> employeeDao;
    private static final Dao<Customer, Integer> customerDao;

    private static User authorizedUser = null;

    static {
        ConnectionSource connectionSource = Database.getInstance().getConnectionSource();
        try {
            userDao = DaoManager.createDao(connectionSource, User.class);
            personDao = DaoManager.createDao(connectionSource, Person.class);
            membershipTypeDao = DaoManager.createDao(connectionSource, MembershipType.class);
            membershipDao = DaoManager.createDao(connectionSource, Membership.class);
            employeeDao = DaoManager.createDao(connectionSource, Employee.class);
            customerDao = DaoManager.createDao(connectionSource, Customer.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Dao<User, Integer> getUserDao() {
        return userDao;
    }

    public static Dao<Person, Integer> getPersonDao() {
        return personDao;
    }

    public static Dao<MembershipType, Integer> getMembershipTypeDao() {
        return membershipTypeDao;
    }

    public static Dao<Membership, Integer> getMembershipDao() {
        return membershipDao;
    }

    public static Dao<Employee, Integer> getEmployeeDao() {
        return employeeDao;
    }

    public static Dao<Customer, Integer> getCustomerDao() {
        return customerDao;
    }

    public static boolean authUser(String login, String password) {
        List<User> users = null;

        Map<String, Object> values = Map.of(
                "login", login,
                "password", password
        );

        try {
            users = userDao.queryForFieldValues(values);
        } catch (SQLException ignored) {

        }

        if (users == null || users.isEmpty())
            return false;

        authorizedUser = users.get(0);

        return true;
    }

    public static void logoutUser() {
        authorizedUser = null;
    }

    public static User getAuthorizedUser() {
        return authorizedUser;
    }

    public static Person getAuthorizedUserPerson() {
        User user = getAuthorizedUser();
        return getUserPerson(user);
    }

    public static Person getUserPerson(User user) {
        Person person;

        try {
            person = personDao.queryForEq("user_id", user.getId()).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return person;
    }

    public static List<MembershipType> getMembershipTypes() {
        List<MembershipType> membershipTypes = new ArrayList<>();

        try {
            membershipTypes = membershipTypeDao.queryForAll();
        } catch (SQLException ignored) {

        }

        return membershipTypes;
    }

    public static List<Membership> getMemberships() {
        List<Membership> memberships = new ArrayList<>();

        try {
            memberships = membershipDao.queryForAll();
        } catch (SQLException ignored) {

        }

        return memberships;
    }

    public static List<Membership> getMemberships(Customer customer) {
        List<Membership> memberships = new ArrayList<>();

        try {
            memberships = membershipDao.queryForEq("customer_id", customer.getId());
        } catch (SQLException ignored) {

        }

        return memberships;
    }

    public static List<Membership> getMemberships(User user) {
        if (user.getRole() != Role.CUSTOMER)
            throw new RuntimeException("User isn`t customer!");

        List<Membership> memberships = new ArrayList<>();

        try {
            List<Customer> customers = customerDao.queryForEq("user_id", user.getId());
            Customer customer = customers.get(0);
            memberships = getMemberships(customer);
        } catch (SQLException ignored) {

        }

        return memberships;
    }

    public static List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();

        try {
            customers = customerDao.queryForAll();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return customers;
    }

    public static void removeMembership(Membership membership) {
        try {
            membershipDao.delete(membership);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void removeCustomer(Customer customer) {
        try {
            List<Membership> memberships = membershipDao.queryForEq("customer_id", customer.getId());
            membershipDao.delete(memberships);

            User user = customer.getUser();

            customerDao.delete(customer);
            personDao.delete(getUserPerson(user));
            userDao.delete(user);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void updatePerson(Person person) {
        try {
            personDao.update(person);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static boolean isLoginUsed(String login) {
        try {
            List<User> users = userDao.queryForEq("login", login);

            if (users.size() != 0)
                return true;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return false;
    }

    public static void createCustomer(Role role, String login, String password, String name,
                                      String surname, String patronymic, boolean sex) {
        User user = new User(login, password, role);
        Person person = new Person(user, name, surname, patronymic, sex);
        Customer customer = new Customer(user);

        try {
            userDao.create(user);
            personDao.create(person);
            customerDao.create(customer);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void createMembership(Customer customer, MembershipType membershipType, Date beginDate) {
        Membership membership = new Membership(customer, membershipType, beginDate);

        try {
            membershipDao.create(membership);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

}
