package dmitr.app.sportiksclub.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import dmitr.app.sportiksclub.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {

    private static final Dao<User, Integer> userDao;
    private static final Dao<Role, Integer> roleDao;
    private static final Dao<Post, Integer> postDao;
    private static final Dao<Person, Integer> personDao;
    private static final Dao<MembershipType, Integer> membershipTypeDao;
    private static final Dao<Membership, Integer> membershipDao;
    private static final Dao<Employee, Integer> employeeDao;
    private static final Dao<Customer, Integer> customerDao;

    private static User authorizedUser;

    static {
        ConnectionSource connectionSource = Database.getInstance().getConnectionSource();
        try {
            userDao = DaoManager.createDao(connectionSource, User.class);
            roleDao = DaoManager.createDao(connectionSource, Role.class);
            postDao = DaoManager.createDao(connectionSource, Post.class);
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

    public static Dao<Role, Integer> getRoleDao() {
        return roleDao;
    }

    public static Dao<Post, Integer> getPostDao() {
        return postDao;
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

    public static User getAuthorizedUser() {
        return authorizedUser;
    }

}
