package dmitr.app.sportiksclub.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class SceneController {

    private static Stage stage;

    /**
     * Привязка контроллера сцен к окну
     *
     * @param stage окно
     */
    public static void init(Stage stage) {
        SceneController.stage = stage;
    }

    /**
     * Установка сцены в привязанном окне
     * @param scene сцена
     */
    public static void setScene(dmitr.app.sportiksclub.scene.Scene scene) {
        FXMLLoader fxmlLoader = new FXMLLoader(scene.getSceneFile());
        Scene stageScene;

        try {
            stageScene = new Scene(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        stage.setMinWidth(scene.getMinWidth());
        stage.setMinHeight(scene.getMinHeight());

        SceneController.stage.setTitle(scene.getCaption());
        SceneController.stage.setScene(stageScene);
        SceneController.stage.show();
    }

    /**
     * Возвращает новое окно на основе сцены
     * @param scene сцена
     * @param iconStream поток иконки для заголовка окна
     * @return окно
     */
    public static Stage getStageByScene(dmitr.app.sportiksclub.scene.Scene scene, InputStream iconStream) {
        Stage tempStage = new Stage();
        tempStage.getIcons().add(new Image(iconStream));

        FXMLLoader fxmlLoader = new FXMLLoader(scene.getSceneFile());
        Scene stageScene;

        try {
            stageScene = new Scene(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        tempStage.setMinWidth(scene.getMinWidth());
        tempStage.setMinHeight(scene.getMinHeight());

        tempStage.setTitle(scene.getCaption());
        tempStage.setScene(stageScene);

        tempStage.initModality(Modality.WINDOW_MODAL);
        tempStage.initOwner(stage);

        return tempStage;
    }

    /**
     * Возвращает привязанное окно
     * @return окно
     */
    public static Stage getStage() {
        return stage;
    }

}
