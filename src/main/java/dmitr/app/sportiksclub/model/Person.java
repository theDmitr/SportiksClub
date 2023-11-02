package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "persons")
public class Person {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private User user;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String lastname;

    @DatabaseField
    private String patronymic;

    @DatabaseField(canBeNull = false)
    private boolean sex;

    public Person() {
    }

    public Person(User user, String name, String lastname, String patronymic, boolean sex) {
        this.user = user;
        this.name = name;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public boolean getSex() {
        return sex;
    }

}
