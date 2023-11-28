package dmitr.app.sportiksclub.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import dmitr.app.sportiksclub.model.*;
import dmitr.app.sportiksclub.util.Role;
import dmitr.app.sportiksclub.util.SHA256Hasher;

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
            ensureAdminAccount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Авторизация пользователя
     *
     * @param login    логин
     * @param password пароль
     * @return результат авторизации
     */
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

    /**
     * Завершает сеанс пользователя
     */
    public static void logoutUser() {
        authorizedUser = null;
    }

    /**
     * Возвращает авторизованного пользователя
     * @return сущность пользователя
     */
    public static User getAuthorizedUser() {
        return authorizedUser;
    }

    /**
     * Возвращает Персону Пользователя
     * @param user сущность пользователя
     * @return сущность персоны
     */
    public static Person getUserPerson(User user) {
        Person person;

        try {
            person = personDao.queryForEq("user_id", user.getId()).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return person;
    }

    /**
     * Возвращает все абонементы
     * @return коллекция абнементов
     */
    public static List<MembershipType> getMembershipTypes() {
        List<MembershipType> membershipTypes = new ArrayList<>();

        try {
            membershipTypes = membershipTypeDao.queryForAll();
        } catch (SQLException ignored) {

        }

        return membershipTypes;
    }

    /**
     * Возвращает все зарегистрированные абонементы
     * @return коллекция абонементов
     */
    public static List<Membership> getMemberships() {
        List<Membership> memberships = new ArrayList<>();

        try {
            memberships = membershipDao.queryForAll();
        } catch (SQLException ignored) {

        }

        return memberships;
    }

    /**
     * Возвращает зарегистрированные на клиента абонементы
     * @param customer сущность клиента
     * @return коллекция абонементов
     */
    public static List<Membership> getMemberships(Customer customer) {
        List<Membership> memberships = new ArrayList<>();

        try {
            memberships = membershipDao.queryForEq("customer_id", customer.getId());
        } catch (SQLException ignored) {

        }

        return memberships;
    }

    /**
     * Возвращает зарегистрированные на пользователя абонементы
     * @param user пользователь
     * @return коллекция абонементов
     */
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

    /**
     * Возвращает коллекцию клиентов
     * @return коллекция клиентов
     */
    public static List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();

        try {
            customers = customerDao.queryForAll();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return customers;
    }

    /**
     * Возвращает коллекцию сотрудников
     * @return коллекция сотрудников
     */
    public static List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        try {
            employees = employeeDao.queryForAll();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return employees;
    }

    /**
     * Удаляет абонемент клиента (Дерегистрация)
     * @param membership
     */
    public static void removeMembership(Membership membership) {
        try {
            membershipDao.delete(membership);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Удаляет аккаунт клиента
     * @param customer клиент
     */
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

    /**
     * Удаляет аккаунт сотрудника
     * @param employee сотрудник
     */
    public static void removeEmployee(Employee employee) {
        try {
            User user = employee.getUser();

            employeeDao.delete(employee);
            personDao.delete(getUserPerson(user));
            userDao.delete(user);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Обновляет сущность Персоны
     * @param person персона
     */
    public static void updatePerson(Person person) {
        try {
            personDao.update(person);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Обновляет сущность пользователя
     * @param user пользователь
     */
    public static void updateUser(User user) {
        try {
            userDao.update(user);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Проверяет, занят ли логин
     * @param login логин
     * @return результат проверки
     */
    public static boolean isLoginUsed(String login) {
        try {
            List<User> users = userDao.queryForEq("login", login);

            if (!users.isEmpty())
                return true;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return false;
    }

    /**
     * Проверяет, используется ли абонемент
     * @param membershipType абонемент
     * @return результат проверки
     */
    public static boolean isMembershipTypeUsed(MembershipType membershipType) {
        try {
            List<Membership> memberships = membershipDao.queryForEq("membershipType_id", membershipType.getId());

            if (!memberships.isEmpty())
                return true;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return false;
    }

    /**
     * Создает аккаунт клиента
     * @param login логин
     * @param password пароль
     * @param name имя
     * @param surname фамилия
     * @param patronymic отчество
     * @param sex пол
     */
    public static void createCustomer(String login, String password, String name,
                                      String surname, String patronymic, boolean sex) {
        User user = new User(login, password, Role.CUSTOMER);
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

    /**
     * Создает аккаунт сотрудника
     * @param login логин
     * @param password пароль
     * @param name имя
     * @param surname фамилия
     * @param patronymic отчество
     * @param sex пол
     */
    public static void createEmployee(String login, String password, String name,
                                      String surname, String patronymic, boolean sex) {
        User user = new User(login, password, Role.EMPLOYEE);
        Person person = new Person(user, name, surname, patronymic, sex);
        Employee employee = new Employee(user);

        try {
            userDao.create(user);
            personDao.create(person);
            employeeDao.create(employee);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Создает аккаунт админа
     * @param login логин
     * @param password пароль
     */
    public static void createAdmin(String login, String password) {
        User user = new User(login, password, Role.ADMIN);

        try {
            userDao.create(user);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Регистрирует абонемент клиента
     * @param customer сущность клиента
     * @param membershipType абонемент
     * @param beginDate дата начала
     */
    public static void createMembership(Customer customer, MembershipType membershipType, Date beginDate) {
        Membership membership = new Membership(customer, membershipType, beginDate);

        try {
            membershipDao.create(membership);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Создает абонемент
     * @param name наименование
     * @param duration длительность
     * @param hasTrainer наличие тренера
     */
    public static void createMembershipType(String name, int duration, boolean hasTrainer) {
        MembershipType membershipType = new MembershipType(name, duration, hasTrainer);

        try {
            membershipTypeDao.create(membershipType);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Удаляет абонемент
     * @param membershipType абонемент
     */
    public static void removeMembershipType(MembershipType membershipType) {
        try {
            membershipTypeDao.delete(membershipType);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Если в БД нет ни одного админ-аккаунта, то создается дефолтный.
     */
    public static void ensureAdminAccount() {
        try {
            List<User> admins = userDao.queryForEq("role", Role.ADMIN);
            if (admins.isEmpty())
                createAdmin("admin", SHA256Hasher.getHash("admin"));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

}
