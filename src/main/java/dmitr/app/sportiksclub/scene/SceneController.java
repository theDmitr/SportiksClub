package dmitr.app.sportiksclub.scene;

import dmitr.app.sportiksclub.SportiksClub;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    public static void setScene(Stage stage, String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(SportiksClub.class.getResource(path));
        Scene scene;

        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        stage.setTitle("Sportik`s club | Auth");
        stage.setScene(scene);
        stage.show();
    }

}
