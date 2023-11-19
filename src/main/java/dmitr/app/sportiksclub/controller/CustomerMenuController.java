package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.SportiksClub;
import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerMenuController implements Initializable {

    @FXML
    private Label userLoginLabel;

    @FXML
    private ImageView logoutImage;

    @FXML
    private Button membershipTypesButton;

    @FXML
    private Button membershipsButton;

    private void logout() {
        DatabaseHelper.logoutUser();
        SceneController.setScene(Scene.AUTH);
    }

    private void openPersonInfo() {
        SceneController.getStageByScene(Scene.PERSON_INFO,
                Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/person.png"))
        ).showAndWait();
    }

    private void applyActions() {
        logoutImage.setOnMouseClicked(mouseEvent -> logout());
        membershipTypesButton.setOnAction(actionEvent -> SceneController.setScene(Scene.MEMBERSHIP_TYPES));
        membershipsButton.setOnAction(actionEvent -> SceneController.setScene(Scene.CUSTOMER_MEMBERSHIPS));
        userLoginLabel.setOnMouseClicked(mouseEvent -> openPersonInfo());
    }

    private void updateUserLoginLabel() {
        User user = DatabaseHelper.getAuthorizedUser();
        userLoginLabel.setText(user.getLogin());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();
        updateUserLoginLabel();
    }

}
