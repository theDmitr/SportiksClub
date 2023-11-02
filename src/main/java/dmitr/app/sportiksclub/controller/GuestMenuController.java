package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
        logoutImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logout();
            }
        });

        membershipTypesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SceneController.setScene(Scene.MEMBERSHIP_TYPES);
            }
        });

        aboutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SceneController.setScene(Scene.ABOUT);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();
    }

}
