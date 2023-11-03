package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Customer;
import dmitr.app.sportiksclub.model.Person;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import dmitr.app.sportiksclub.util.ExcelWorkbookUtils;
import dmitr.app.sportiksclub.util.FileUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    @FXML
    private ImageView menuImage;

    @FXML
    private TableView<Customer> customersTableView;

    @FXML
    private TableColumn<Customer, String> nameTableColumn;

    @FXML
    private TableColumn<Customer, String> surnameTableColumn;

    @FXML
    private TableColumn<Customer, String> patronymicTableColumn;

    @FXML
    private TableColumn<Customer, String> sexTableColumn;

    @FXML
    private Button updateButton;

    @FXML
    private Button exportButton;

    private void goToMenu() {
        User user = DatabaseHelper.getAuthorizedUser();

        if (user == null) {
            SceneController.setScene(Scene.GUEST_MENU);
            return;
        }

        switch (user.getRole()) {
            case ADMIN -> SceneController.setScene(Scene.ADMIN_MENU);
            case EMPLOYEE -> SceneController.setScene(Scene.EMPLOYEE_MENU);
        }
    }

    private void exportTable() {
        Stage stage = SceneController.getStage();
        File file = FileUtils.getFileByChooser(stage,
                "Сохранить таблицу как...", FileUtils.excelExtensionfilter);

        if (file == null)
            return;

        String fileName = file.getAbsolutePath();

        ObservableList<Customer> items = customersTableView.getItems();

        String[][] fields = new String[items.size() + 1][4];
        fields[0] = new String[]{"Имя", "Фамилия", "Отчество", "Пол"};

        for (int i = 1; i <= items.size(); i++) {
            Person person = DatabaseHelper.getUserPerson(items.get(i - 1).getUser());
            fields[i][0] = person.getName();
            fields[i][1] = person.getSurname();
            fields[i][2] = person.getPatronymic();
            fields[i][3] = person.getSex() ? "Мужской" : "Женский";
        }

        ExcelWorkbookUtils.writeTable(fileName, "Абонементы", fields);
    }

    private void applyActions() {
        menuImage.setOnMouseClicked(mouseEvent -> goToMenu());
        updateButton.setOnAction(actionEvent -> updateTableItems());
        exportButton.setOnAction(actionEvent -> exportTable());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();

        nameTableColumn.setCellValueFactory(
                p -> new ReadOnlyStringWrapper(DatabaseHelper.getUserPerson(p.getValue().getUser()).getName())
        );

        surnameTableColumn.setCellValueFactory(
                p -> new ReadOnlyStringWrapper(DatabaseHelper.getUserPerson(p.getValue().getUser()).getSurname())
        );

        patronymicTableColumn.setCellValueFactory(
                p -> new ReadOnlyStringWrapper(DatabaseHelper.getUserPerson(p.getValue().getUser()).getPatronymic())
        );

        sexTableColumn.setCellValueFactory(
                p -> new ReadOnlyStringWrapper(
                        DatabaseHelper.getUserPerson(p.getValue().getUser()).getSex() ? "Мужской" : "Женский"
                )
        );

        updateTableItems();
    }

    private void updateTableItems() {
        List<Customer> membershipTypeItems = DatabaseHelper.getCustomers();
        ObservableList<Customer> data = FXCollections.observableArrayList(membershipTypeItems);
        customersTableView.setItems(data);
    }

}
