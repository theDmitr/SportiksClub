package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

@DatabaseTable(tableName = "employees")
public class Employee {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private User user;

    @DatabaseField(foreign = true)
    private Post post;

    @DatabaseField(canBeNull = false)
    private int experience;

    public Employee() {
    }

    public Employee(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public int getExperience() {
        return experience;
    }

}
