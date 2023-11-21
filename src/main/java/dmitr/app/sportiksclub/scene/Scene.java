package dmitr.app.sportiksclub.scene;

import dmitr.app.sportiksclub.SportiksClub;

import java.net.URL;

public enum Scene {

    AUTH("Sportik`s Club | Авторизация", "view/Auth.fxml"),
    GUEST_MENU("Sportik`s Club | Гость", "view/GuestMenu.fxml"),
    ADMIN_MENU("Sportik`s Club | Администратор", "view/AdminMenu.fxml"),
    EMPLOYEE_MENU("Sportik`s Club | Сотрудник", "view/EmployeeMenu.fxml"),
    CUSTOMER_MENU("Sportik`s Club | Клиент", "view/CustomerMenu.fxml"),
    ABOUT("Sportik`s Club | О нас", "view/About.fxml"),
    MEMBERSHIP_TYPES("Sportik`s Club | Абонементы", "view/MembershipTypes.fxml"),
    CUSTOMER_MEMBERSHIPS("Sportik`s Club | Мои абонементы", "view/CustomerMemberships.fxml"),
    CUSTOMERS("Sportik`s Club | Клиенты", "view/Customers.fxml"),
    ALL_MEMBERSHIPS("Sportik`s Club | Абонементы клиентов", "view/AllMemberships.fxml"),
    EDIT_PERSON("Sportik`s Club | Редактор аккаунта", 450, 365, "view/EditPerson.fxml"),
    CREATE_CUSTOMER("Sportik`s Club | Регистрация клиента", "view/CreateCustomer.fxml"),
    CREATE_MEMBERSHIP("Sportik`s Club | Применение абонемента", "view/CreateMembership.fxml"),
    EMPLOYEES("Sportik`s Club | Сотрудники", "view/Employees.fxml"),
    ADMIN_MEMBERSHIP_TYPES("Sportik`s Club | Абонементы", "view/AdminMembershipTypes.fxml"),
    CREATE_EMPLOYEE("Sportik`s Club | Регистрация сотрудника", "view/CreateEmployee.fxml"),
    CREATE_MEMBERSHIP_TYPE("Sportik`s Club | Создание абонемента", "view/CreateMembershipType.fxml"),
    PERSON_INFO("Sportik`s Club | Информация об аккаунте", 337, 230, "view/PersonInfo.fxml"),
    EDIT_ADMIN("Sportik`s Club | Редактор аккаунта", 450, 250, "view/EditAdmin.fxml");


    private final String caption;
    private final URL sceneFile;
    private final double minWidth;
    private final double minHeight;

    Scene(String caption, String filePath) {
        this(caption, 550, 300, filePath);
    }

    Scene(String caption, double minWidth, double minHeight, String filePath) {
        this.caption = caption;
        this.sceneFile = SportiksClub.class.getResource(filePath);
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }

    public String getCaption() {
        return caption;
    }

    public URL getSceneFile() {
        return sceneFile;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public double getMinHeight() {
        return minHeight;
    }

}
