package dmitr.app.sportiksclub.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private static Stage stage;

    public static void init(Stage stage) {
        SceneController.stage = stage;
    }

    public static void setScene(dmitr.app.sportiksclub.scene.Scene scene) {
        FXMLLoader fxmlLoader = new FXMLLoader(scene.getFilePath());
        Scene stageScene;

        try {
            stageScene = new Scene(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        SceneController.stage.setTitle(scene.getCaption());
        SceneController.stage.setScene(stageScene);
        SceneController.stage.show();
    }

}
