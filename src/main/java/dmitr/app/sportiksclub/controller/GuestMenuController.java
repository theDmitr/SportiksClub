package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class GuestMenuController implements Initializable {

    @FXML
    private ImageView logoutImage;

    @FXML
    private Button membershipTypesButton;

    @FXML
    private Button aboutButton;

    private void logout() {
        SceneController.setScene(Scene.AUTH);
    }

    private void applyActions() {
        logoutImage.setOnMouseClicked(mouseEvent -> logout());

        membershipTypesButton.setOnAction(actionEvent -> SceneController.setScene(Scene.MEMBERSHIP_TYPES));

        aboutButton.setOnAction(actionEvent -> SceneController.setScene(Scene.ABOUT));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();
    }

}
