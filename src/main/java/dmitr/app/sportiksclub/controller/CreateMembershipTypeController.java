package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.util.SportiksAlertType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateMembershipTypeController implements Initializable {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField durationTextField;

    @FXML
    private CheckBox hasTrainerCheckBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private void saveData() {
        String name = nameTextField.getText().strip();
        String durationStr = durationTextField.getText().strip();
        boolean hasTrainer = hasTrainerCheckBox.isSelected();

        if (name.isEmpty()) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Заполните поле название!", null)
                    .showAndWait();
            return;
        }

        int duration;

        try {
            duration = Integer.parseInt(durationStr);
        } catch (Exception exception) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Некорректно заполнено поле длительности!", null)
                    .showAndWait();
            return;
        }

        if (duration <= 0) {
            SportiksAlertType.ERROR.getAlert("Ошибка",
                            "Поле длительности может быть только положительным целым числом!", null)
                    .showAndWait();
            return;
        }

        DatabaseHelper.createMembershipType(name, duration, hasTrainer);

        SportiksAlertType.INFORMATION.getAlert("Успех", "Абонемент создан!", null)
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
