package dmitr.app.sportiksclub;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.*;
import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import dmitr.app.sportiksclub.util.Role;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;

public class SportiksClub extends Application {

    public static void main(String[] args) {
        launch();
    }

    private static void fill() throws SQLException {
        User user = new User("mana", "", Role.CUSTOMER);
        Person person = new Person(user, "Dmitr", "Levakov", "Stanislavovich", true);
        Customer customer = new Customer(user);
        MembershipType membershipType = new MembershipType("Топ абоник", 28, true);
        Membership membership = new Membership(customer, membershipType, new Date(2023 - 1900, 11 - 1, 2), 0.15);

        User user2 = new User("asd", "", Role.EMPLOYEE);
        Person person2 = new Person(user2, "Andrey", "Lox", "SHISHKA", true);
        Employee employee = new Employee(user2);

        DatabaseHelper.getUserDao().create(user);
        DatabaseHelper.getUserDao().create(user2);
        DatabaseHelper.getPersonDao().create(person);
        DatabaseHelper.getPersonDao().create(person2);
        DatabaseHelper.getCustomerDao().create(customer);
        DatabaseHelper.getEmployeeDao().create(employee);
        DatabaseHelper.getMembershipTypeDao().create(membershipType);
        DatabaseHelper.getMembershipDao().create(membership);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(
                new Image(Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/dumbbell.png")))
        );
        SceneController.init(stage);
        SceneController.setScene(Scene.AUTH);

        fill();
    }

}
