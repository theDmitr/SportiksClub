package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    @FXML
    private ImageView menuImage;

    private void applyActions() {
        menuImage.setOnMouseClicked(mouseEvent -> SceneController.setScene(Scene.GUEST_MENU));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();
    }

}
