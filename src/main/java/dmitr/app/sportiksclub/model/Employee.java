package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "employees")
public class Employee {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private User user;

    @DatabaseField(canBeNull = false)
    private int experience;

    public Employee() {
    }

    public Employee(User user) {
        this.user = user;
    }

    /**
     * Возвращает ID сотрулника
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает сущность пользователя сотрулника
     * @return сущность пользователя
     */
    public User getUser() {
        return user;
    }

    /**
     * Возвращает опыт работы сотрудника
     * @return опыт работы (int)
     */
    public int getExperience() {
        return experience;
    }

}
