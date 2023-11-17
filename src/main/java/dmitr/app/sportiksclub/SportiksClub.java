package dmitr.app.sportiksclub;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Employee;
import dmitr.app.sportiksclub.model.MembershipType;
import dmitr.app.sportiksclub.model.Person;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import dmitr.app.sportiksclub.util.Role;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class SportiksClub extends Application {

    public static void main(String[] args) {
        launch();
    }

    private static void fill() throws SQLException {
        User user = new User("admin", "", Role.ADMIN);
        DatabaseHelper.getUserDao().create(user);
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
