package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Customer;
import dmitr.app.sportiksclub.model.MembershipType;
import dmitr.app.sportiksclub.util.PersonUtils;
import dmitr.app.sportiksclub.util.SportiksAlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CreateMembershipController implements Initializable {

    @FXML
    private SearchableComboBox<Customer> customersSearchableComboBox;

    @FXML
    private SearchableComboBox<MembershipType> membershipTypesSearchableComboBox;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    private void create() {
        Customer customer = customersSearchableComboBox.getSelectionModel().getSelectedItem();
        MembershipType membershipType = membershipTypesSearchableComboBox.getSelectionModel().getSelectedItem();

        if (customer == null) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Выберите клиента!", null)
                    .showAndWait();
            return;
        }

        if (membershipType == null) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Выберите абонемент!", null)
                    .showAndWait();
            return;
        }

        if (dataPicker.getValue() == null) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Выберите дату!", null)
                    .showAndWait();
            return;
        }

        Date dateStart = Date.valueOf(dataPicker.getValue());

        if (dateStart.before(Date.valueOf(LocalDate.now()))) {
            SportiksAlertType.ERROR.getAlert("Ошибка", "Некорректно выбрана дата начала!", null)
                    .showAndWait();
            return;
        }

        DatabaseHelper.createMembership(customer, membershipType, dateStart);

        SportiksAlertType.INFORMATION.getAlert("Успех", "Абонемент успешно создан!", null)
                .showAndWait();

        cancel();
    }

    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void applyActions() {
        cancelButton.setOnAction(actionEvent -> cancel());
        createButton.setOnAction(actionEvent -> create());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();

        List<Customer> customers = DatabaseHelper.getCustomers();
        ObservableList<Customer> observableCustomers = FXCollections.observableArrayList(customers);
        customersSearchableComboBox.setItems(observableCustomers);
        customersSearchableComboBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer != null ? PersonUtils.getInitials(DatabaseHelper.getUserPerson(customer.getUser()))
                        : "";
            }

            @Override
            public Customer fromString(String s) {
                return null;
            }
        });

        List<MembershipType> membershipTypes = DatabaseHelper.getMembershipTypes();
        ObservableList<MembershipType> observableMembershipTypes = FXCollections.observableArrayList(membershipTypes);
        membershipTypesSearchableComboBox.setItems(observableMembershipTypes);
        membershipTypesSearchableComboBox.setConverter(new StringConverter<MembershipType>() {
            @Override
            public String toString(MembershipType membershipType) {
                return membershipType != null ? membershipType.getName() : "";
            }

            @Override
            public MembershipType fromString(String s) {
                return null;
            }
        });
    }

}
