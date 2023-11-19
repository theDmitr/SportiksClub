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
    EDIT_PERSON("Sportik`s Club | Редактор аккаунта", SportiksClub.class.getResource("view/EditPerson.fxml")),
    CREATE_CUSTOMER("Sportik`s Club | Регистрация клиента", SportiksClub.class.getResource("view/CreateCustomer.fxml")),
    CREATE_MEMBERSHIP("Sportik`s Club | Применение абонемента", SportiksClub.class.getResource("view/CreateMembership.fxml")),
    EMPLOYEES("Sportik`s Club | Сотрудники", SportiksClub.class.getResource("view/Employees.fxml")),
    ADMIN_MEMBERSHIP_TYPES("Sportik`s Club | Абонементы", SportiksClub.class.getResource("view/AdminMembershipTypes.fxml")),
    CREATE_EMPLOYEE("Sportik`s Club | Регистрация сотрудника", SportiksClub.class.getResource("view/CreateEmployee.fxml")),
    CREATE_MEMBERSHIP_TYPE("Sportik`s Club | Создание абонемента", SportiksClub.class.getResource("view/CreateMembershipType.fxml")),
    PERSON_INFO("Sportik`s Club | Информация об аккаунте", SportiksClub.class.getResource("view/PersonInfo.fxml"));

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
