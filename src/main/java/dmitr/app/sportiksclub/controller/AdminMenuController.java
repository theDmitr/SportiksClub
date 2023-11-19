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

public class AdminMenuController implements Initializable {

    @FXML
    private Label userLoginLabel;

    @FXML
    private ImageView logoutImage;

    @FXML
    private Button employeesButton;

    @FXML
    private Button membershipTypesButton;

    private void logout() {
        DatabaseHelper.logoutUser();
        SceneController.setScene(Scene.AUTH);
    }

    private void openEditAdmin() {
        SceneController.getStageByScene(Scene.EDIT_ADMIN,
                Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/person.png"))
        ).showAndWait();
        updateUserLoginLabel();
    }

    private void applyActions() {
        logoutImage.setOnMouseClicked(mouseEvent -> logout());
        employeesButton.setOnAction(actionEvent -> SceneController.setScene(Scene.EMPLOYEES));
        membershipTypesButton.setOnAction(actionEvent -> SceneController.setScene(Scene.ADMIN_MEMBERSHIP_TYPES));
        userLoginLabel.setOnMouseClicked(mouseEvent -> openEditAdmin());
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
