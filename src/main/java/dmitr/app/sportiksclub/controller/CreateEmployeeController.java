package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.util.SHA256Hasher;
import dmitr.app.sportiksclub.util.SportiksAlertType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateEmployeeController implements Initializable {

    @FXML
    private ToggleGroup sexButtonsGroup;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField patronymicTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private void saveData() {
        String login = loginTextField.getText().strip();
        String password = passwordTextField.getText().strip();
        String name = nameTextField.getText().strip();
        String surname = surnameTextField.getText().strip();
        String patronymic = patronymicTextField.getText().strip();
        RadioButton sexRadioButton = (RadioButton) sexButtonsGroup.getSelectedToggle();

        if (login.isEmpty()) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Заполните поле логин!", null)
                    .showAndWait();
            return;
        }

        if (login.length() > 24) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Максимальная длина логина 24 символа!", null)
                    .showAndWait();
            return;
        }

        if (DatabaseHelper.isLoginUsed(login)) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Данный логин занят!", null)
                    .showAndWait();
            return;
        }

        if (password.isEmpty()) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Заполните поле пароль!", null)
                    .showAndWait();
            return;
        }

        if (password.length() > 64) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Максимальная длина пароля 64 символа!", null)
                    .showAndWait();
            return;
        }

        if (name.isEmpty() || surname.isEmpty()) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Заполните поля ФИО!", null)
                    .showAndWait();
            return;
        }

        if (name.length() > 16) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Максимальная длина имя 16 символов!", null)
                    .showAndWait();
            return;
        }

        if (surname.length() > 30) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Максимальная длина фамилии 30 символов!", null)
                    .showAndWait();
            return;
        }

        if (patronymic.length() > 16) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Максимальная длина отчества 16 символов!", null)
                    .showAndWait();
            return;
        }

        if (sexRadioButton == null) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Выберите пол!", null)
                    .showAndWait();
            return;
        }

        boolean sex = sexRadioButton.equals(maleRadioButton);

        DatabaseHelper.createEmployee(login, SHA256Hasher.getHash(password), name, surname, patronymic, sex);

        SportiksAlertType.INFORMATION.getAlert("Успех", "Аккаунт зарегистрирован!", null)
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
