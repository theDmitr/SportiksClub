package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Customer;
import dmitr.app.sportiksclub.model.Person;
import dmitr.app.sportiksclub.util.AlertButtonTypes;
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

public class EditCustomerController implements Initializable {

    private static Customer editable;
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
    private Button saveButton;
    @FXML
    private Button cancelButton;

    public static void setEditableCustomer(Customer customer) {
        editable = customer;
    }

    private void saveData() {
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String patronymic = patronymicTextField.getText();
        RadioButton sexRadioButton = (RadioButton) sexButtonsGroup.getSelectedToggle();

        if (name.isEmpty() || surname.isEmpty() || patronymic.isEmpty()) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Заполните поля ФИО!", null)
                    .showAndWait();
            return;
        }

        if (sexRadioButton == null) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Выберите пол!", null)
                    .showAndWait();
            return;
        }

        boolean sex = sexRadioButton.equals(maleRadioButton);

        if (SportiksAlertType.CONFIRMATION.getAlert("Подтверждение",
                        "Вы уверены, что хотите изменить данные клиента?", null)
                .showAndWait().get() == AlertButtonTypes.noButtonType)
            return;

        Person person = DatabaseHelper.getUserPerson(editable.getUser());
        person.setName(name);
        person.setSurname(surname);
        person.setPatronymic(patronymic);
        person.setSex(sex);

        DatabaseHelper.updatePerson(person);

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

        Person person = DatabaseHelper.getUserPerson(editable.getUser());

        nameTextField.setText(person.getName());
        surnameTextField.setText(person.getSurname());
        patronymicTextField.setText(person.getPatronymic());
        sexButtonsGroup.selectToggle(person.getSex() ? maleRadioButton : femaleRadioButton);
    }

}
