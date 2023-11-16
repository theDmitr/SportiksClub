package dmitr.app.sportiksclub.scene;

import dmitr.app.sportiksclub.SportiksClub;

import java.net.URL;

public enum Scene {

    AUTH("Sportik`s Club | Авторизация", SportiksClub.class.getResource("view/Auth.fxml")),
    GUEST_MENU("Sportik`s Club | Гость", SportiksClub.class.getResource("view/GuestMenu.fxml")),
    ADMIN_MENU("Sportik`s Club | Администратор", SportiksClub.class.getResource("view/AdminMenu.fxml")),
    EMPLOYEE_MENU("Sportik`s Club | Сотрудник", SportiksClub.class.getResource("view/EmployeeMenu.fxml")),
    CUSTOMER_MENU("Sportik`s Club | Клиент", SportiksClub.class.getResource("view/CustomerMenu.fxml")),
    ABOUT("Sportik`s Club | О нас", SportiksClub.class.getResource("view/About.fxml")),
    MEMBERSHIP_TYPES("Sportik`s Club | Абонементы", SportiksClub.class.getResource("view/MembershipTypes.fxml")),
    CUSTOMER_MEMBERSHIPS("Sportik`s Club | Мои абонементы", SportiksClub.class.getResource("view/CustomerMemberships.fxml")),
    CUSTOMERS("Sportik`s Club | Клиенты", SportiksClub.class.getResource("view/Customers.fxml")),
    ALL_MEMBERSHIPS("Sportik`s Club | Абонементы клиентов", SportiksClub.class.getResource("view/AllMemberships.fxml")),
    EDIT_CUSTOMER("Sportik`s Club | Редактор клиента", SportiksClub.class.getResource("view/EditCustomer.fxml")),
    CREATE_CUSTOMER("Sportik`s Club | Регистрация клиента", SportiksClub.class.getResource("view/CreateCustomer.fxml")),
    CREATE_MEMBERSHIP("Sportik`s Club | Применение абонемента", SportiksClub.class.getResource("view/CreateMembership.fxml"));

    private final String caption;
    private final URL filePath;

    Scene(String caption, URL filePath) {
        this.caption = caption;
        this.filePath = filePath;
    }

    public String getCaption() {
        return caption;
    }

    public URL getFilePath() {
        return filePath;
    }
}
