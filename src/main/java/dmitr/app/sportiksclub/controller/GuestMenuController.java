package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.scene.SceneController;
import dmitr.app.sportiksclub.util.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class GuestMenuController implements Initializable {

    @FXML
    private Button menuButton;

    private void applyActions() {
        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SceneController.setScene(Utils.getStageFromActionEvent(actionEvent), "view/Auth.fxml");
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();
    }

}
