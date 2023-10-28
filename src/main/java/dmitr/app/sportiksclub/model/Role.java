package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "roles")
public class Role {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private int userId;

    @DatabaseField(canBeNull = false)
    private String name;

    public Role() { }

    public int getId() {
        return id;
    }

    public Role(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
