package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "customers")
public class Customer {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private User user;

    public Customer() {
    }

    public Customer(User user) {
        this.user = user;
    }

    /**
     * Возвращает ID клиента
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает сущность пользователя клиента
     * @return сущность пользователя
     */
    public User getUser() {
        return user;
    }

}
