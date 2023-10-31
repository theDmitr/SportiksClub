package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();
    }

}
