package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "persons")
public class Person {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private User user;

    @DatabaseField(canBeNull = false, columnDefinition = "VARCHAR(16)")
    private String name;

    @DatabaseField(canBeNull = false, columnDefinition = "VARCHAR(30)")
    private String surname;

    @DatabaseField(columnDefinition = "VARCHAR(16)")
    private String patronymic;

    @DatabaseField(canBeNull = false)
    private boolean sex;

    public Person() {
    }

    public Person(User user, String name, String surname, String patronymic, boolean sex) {
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.sex = sex;
    }

    /**
     * Возвращает ID Персоны
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает сузность Пользователя Персоны
     * @return сущность Пользователя
     */
    public User getUser() {
        return user;
    }

    /**
     * Возвращает имя Персоны
     * @return имя
     */
    public String getName() {
        return name;
    }

    /**
     * Установливает имя Персоны
     * @param name имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает фамилию Персоны
     * @return фамилия
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Устанавливает фамилию Персоны
     * @param surname фамилия
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Возвращает отчество Персоны
     * @return отчество
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Устанавливает отчество Персоны
     * @param patronymic отчество
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Устанавливает пол Персоны
     * @return пол
     */
    public boolean getSex() {
        return sex;
    }

    /**
     * Устанавливает пол Персоны
     * @param sex пол
     */
    public void setSex(boolean sex) {
        this.sex = sex;
    }

}
