package dmitr.app.sportiksclub.util;

public enum Role {

    ADMIN("Администратор"), EMPLOYEE("Сотрудник организации"), CUSTOMER("Клиент");
    private final String caption;

    Role(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return caption;
    }

}
