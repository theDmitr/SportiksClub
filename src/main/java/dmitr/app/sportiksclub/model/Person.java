package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "persons")
public class Person {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private int userId;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String lastname;

    @DatabaseField(canBeNull = false)
    private String patronymic;

    @DatabaseField(canBeNull = false)
    private boolean sex;

    public Person() { }

    public Person(int userId, String name, String lastname, String patronymic, boolean sex) {
        this.userId = userId;
        this.name = name;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
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
