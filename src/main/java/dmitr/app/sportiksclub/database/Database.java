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

    public final ConnectionSource connection;

    private Database() throws SQLException, ClassNotFoundException {
        String path = Utils.getDatabasePath();
        connection = new JdbcConnectionSource(path);
        setupDatabase();
    }

    public static Database getInstance() {
        return instance;
    }

    private void setupDatabase() throws SQLException {
        TableUtils.createTableIfNotExists(connection, User.class);
        TableUtils.createTableIfNotExists(connection, Role.class);
        TableUtils.createTableIfNotExists(connection, Post.class);
        TableUtils.createTableIfNotExists(connection, Person.class);
        TableUtils.createTableIfNotExists(connection, MembershipType.class);
        TableUtils.createTableIfNotExists(connection, Membership.class);
        TableUtils.createTableIfNotExists(connection, Employee.class);
        TableUtils.createTableIfNotExists(connection, Customer.class);
    }

}
