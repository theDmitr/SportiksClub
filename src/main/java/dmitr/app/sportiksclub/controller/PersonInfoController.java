package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonInfoController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField patronymicTextField;

    @FXML
    private Button closeButton;

    private Person looking;

    private void updateTextFields() {
        nameTextField.setText(looking.getName());
        surnameTextField.setText(looking.getSurname());
        patronymicTextField.setText(looking.getPatronymic());
    }

    private void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void applyActions() {
        closeButton.setOnAction(actionEvent -> close());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();
        looking = DatabaseHelper.getUserPerson(DatabaseHelper.getAuthorizedUser());
        updateTextFields();
    }

}
