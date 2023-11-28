package dmitr.app.sportiksclub.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import dmitr.app.sportiksclub.util.Role;

@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, columnDefinition = "VARCHAR(24)")
    private String login;

    @DatabaseField(canBeNull = false, columnDefinition = "VARCHAR(64)")
    private String password;

    @DatabaseField(canBeNull = false)
    private Role role;

    public User() {
    }

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    /**
     * Возвращает ID пользователя
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает логин пользователя
     * @return логин
     */
    public String getLogin() {
        return login;
    }

    /**
     * Устанавливает логин пользователя
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Возвращает пароль (хеш) пользователя
     * @return пароль (хеш)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя
     * @param password пароль (хеш)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Возвращает роль пользователя
     * @return роль
     */
    public Role getRole() {
        return role;
    }

}
