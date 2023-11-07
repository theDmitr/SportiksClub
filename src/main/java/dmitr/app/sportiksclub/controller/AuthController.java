package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import dmitr.app.sportiksclub.util.SportiksAlertType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    @FXML
    private Button authButton;

    @FXML
    private Button guestAuthButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    private void auth(String login, String password, ActionEvent actionEvent) {
        boolean success = DatabaseHelper.authUser(login, password);

        if (!success) {
            SportiksAlertType.ERROR.getAlert("Произошла ошибка!", "Неверный логин или пароль!")
                    .showAndWait();
            return;
        }

        SportiksAlertType.INFORMATION.getAlert("Успешная авторизация!", "Вы авторизованы!")
                .showAndWait();

        User user = DatabaseHelper.getAuthorizedUser();

        switch (user.getRole()) {
            case ADMIN -> SceneController.setScene(Scene.ADMIN_MENU);
            case EMPLOYEE -> SceneController.setScene(Scene.EMPLOYEE_MENU);
            case CUSTOMER -> SceneController.setScene(Scene.CUSTOMER_MENU);
        }
    }

    private void applyActions() {
        authButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String login = loginTextField.getText();
                String password = passwordTextField.getText();
                auth(login, password, actionEvent);
            }
        });

        guestAuthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SceneController.setScene(Scene.GUEST_MENU);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();
    }

}
