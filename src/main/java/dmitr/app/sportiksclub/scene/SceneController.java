package dmitr.app.sportiksclub.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

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

    public static Stage getStageByScene(dmitr.app.sportiksclub.scene.Scene scene, InputStream iconStream) {
        Stage tempStage = new Stage();
        tempStage.getIcons().add(new Image(iconStream));

        FXMLLoader fxmlLoader = new FXMLLoader(scene.getFilePath());
        Scene stageScene;

        try {
            stageScene = new Scene(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        tempStage.setTitle(scene.getCaption());
        tempStage.setScene(stageScene);

        tempStage.initModality(Modality.WINDOW_MODAL);
        tempStage.initOwner(stage);

        return tempStage;
    }

    public static Stage getStage() {
        return stage;
    }

}
