package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Person;
import dmitr.app.sportiksclub.util.AlertButtonTypes;
import dmitr.app.sportiksclub.util.SHA256Hasher;
import dmitr.app.sportiksclub.util.SportiksAlertType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPersonController implements Initializable {

    private static Person editable;
    @FXML
    private ToggleGroup sexButtonsGroup;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField patronymicTextField;
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

    /**
     * Установка редактируемой сущности Персоны
     *
     * @param person сущность Персоны
     */
    public static void setEditablePerson(Person person) {
        editable = person;
    }

    private void saveData() {
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String patronymic = patronymicTextField.getText();
        RadioButton sexRadioButton = (RadioButton) sexButtonsGroup.getSelectedToggle();

        String login = null;
        String password = null;

        if (name.isEmpty() || surname.isEmpty() || patronymic.isEmpty()) {
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

        if (loginEditCheckBox.isSelected()) {
            login = loginTextField.getText();

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
        }

        if (passwordEditCheckBox.isSelected()) {
            password = passwordTextField.getText();

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
        }

        if (SportiksAlertType.CONFIRMATION.getAlert("Подтверждение",
                        "Вы уверены, что хотите изменить данные аккаунта?", null)
                .showAndWait().get() == AlertButtonTypes.noButtonType)
            return;

        editable.setName(name);
        editable.setSurname(surname);
        editable.setPatronymic(patronymic);
        editable.setSex(sex);

        if (loginEditCheckBox.isSelected()) {
            editable.getUser().setLogin(login);
        }

        if (passwordEditCheckBox.isSelected()) {
            editable.getUser().setPassword(SHA256Hasher.getHash(password));
        }

        DatabaseHelper.updatePerson(editable);
        DatabaseHelper.updateUser(editable.getUser());

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

        if (editable == null) {
            SportiksAlertType.ERROR.getAlert("Ошибка",
                    "Произошла непредвиденная ошибка!", null).showAndWait();
            close();
        }

        nameTextField.setText(editable.getName());
        surnameTextField.setText(editable.getSurname());
        patronymicTextField.setText(editable.getPatronymic());
        sexButtonsGroup.selectToggle(editable.getSex() ? maleRadioButton : femaleRadioButton);
    }

}
