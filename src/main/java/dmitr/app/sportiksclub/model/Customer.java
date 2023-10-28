package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "customers")
public class Customer {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private int userId;

    public Customer() { }

    public Customer(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

}
