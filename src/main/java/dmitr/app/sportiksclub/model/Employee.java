package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;

@DatabaseTable(tableName = "employees")
public class Employee {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private int userId;

    @DatabaseField(foreign = true)
    private int postId;

    @DatabaseField(canBeNull = false)
    private LocalDate experience;

    public Employee() { }

    public Employee(int userId, int postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getPostId() {
        return postId;
    }

    public LocalDate getExperience() {
        return experience;
    }

}
