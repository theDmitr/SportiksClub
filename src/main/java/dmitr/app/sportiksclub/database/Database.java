package dmitr.app.sportiksclub.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dmitr.app.sportiksclub.model.*;
import dmitr.app.sportiksclub.util.Utils;

import java.sql.SQLException;

public class Database {

    private static final Database instance;

    static {
        try {
            instance = new Database();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final ConnectionSource connectionSource;

    private Database() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String path = Utils.getDatabasePath();
        connectionSource = new JdbcConnectionSource(path);
        setupDatabase();
    }

    public static Database getInstance() {
        return instance;
    }

    private void setupDatabase() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, User.class);
        TableUtils.createTableIfNotExists(connectionSource, Post.class);
        TableUtils.createTableIfNotExists(connectionSource, Person.class);
        TableUtils.createTableIfNotExists(connectionSource, MembershipType.class);
        TableUtils.createTableIfNotExists(connectionSource, Membership.class);
        TableUtils.createTableIfNotExists(connectionSource, Employee.class);
        TableUtils.createTableIfNotExists(connectionSource, Customer.class);
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

}
