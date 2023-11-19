package dmitr.app.sportiksclub;

import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class SportiksClub extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(
                new Image(Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/dumbbell.png")))
        );
        SceneController.init(stage);
        SceneController.setScene(Scene.AUTH);
    }

}
