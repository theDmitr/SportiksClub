package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.util.AlertButtonTypes;
import dmitr.app.sportiksclub.util.SHA256Hasher;
import dmitr.app.sportiksclub.util.SportiksAlertType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAdminController implements Initializable {

    @FXML
    private CheckBox loginEditCheckBox;
    @FXML
    private CheckBox passwordEditCheckBox;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private void saveData() {
        String login = null;
        String password = null;

        if (loginEditCheckBox.isSelected()) {
            login = loginTextField.getText();

            if (login.isEmpty()) {
                SportiksAlertType.ERROR.getAlert("Ошибка", "Заполните поле логин!", null)
                        .showAndWait();
                return;
            }

            if (DatabaseHelper.isLoginUsed(login)) {
                SportiksAlertType.ERROR.getAlert("Ошибка", "Данный логин занят!", null)
                        .showAndWait();
                return;
            }
        }

        if (passwordEditCheckBox.isSelected()) {
            password = passwordTextField.getText();

            if (password.isEmpty()) {
                SportiksAlertType.ERROR.getAlert("Ошибка", "Заполните поле пароль!", null)
                        .showAndWait();
                return;
            }
        }

        if (SportiksAlertType.CONFIRMATION.getAlert("Подтверждение",
                        "Вы уверены, что хотите изменить данные аккаунта?", null)
                .showAndWait().get() == AlertButtonTypes.noButtonType)
            return;

        User admin = DatabaseHelper.getAuthorizedUser();

        if (loginEditCheckBox.isSelected())
            admin.setLogin(login);

        if (passwordEditCheckBox.isSelected())
            admin.setPassword(SHA256Hasher.getHash(password));

        DatabaseHelper.updateUser(admin);

        SportiksAlertType.INFORMATION.getAlert("Успех", "Данные аккаунта изменены!", null)
                .showAndWait();

        close();
    }

    private void close() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void applyActions() {
        saveButton.setOnAction(actionEvent -> saveData());
        cancelButton.setOnAction(actionEvent -> close());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();
    }

}
