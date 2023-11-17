package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.SportiksClub;
import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Employee;
import dmitr.app.sportiksclub.model.Person;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import dmitr.app.sportiksclub.util.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class EmployeesController implements Initializable {

    @FXML
    private ImageView menuImage;

    @FXML
    private TableView<Employee> employeesTableView;

    @FXML
    private TableColumn<Employee, String> nameTableColumn;

    @FXML
    private TableColumn<Employee, String> surnameTableColumn;

    @FXML
    private TableColumn<Employee, String> patronymicTableColumn;

    @FXML
    private TableColumn<Employee, String> sexTableColumn;

    @FXML
    private MenuItem contextMenuQrItem;

    @FXML
    private MenuItem updateTableItem;

    @FXML
    private MenuItem exportTableItem;

    @FXML
    private MenuItem editEmployeeItem;

    @FXML
    private MenuItem removeEmployeeItem;

    @FXML
    private MenuItem createEmployeeItem;

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

        ObservableList<Employee> items = employeesTableView.getItems();

        String[][] fields = new String[items.size() + 1][4];
        fields[0] = new String[]{"Имя", "Фамилия", "Отчество", "Пол"};

        for (int i = 1; i <= items.size(); i++) {
            Person person = DatabaseHelper.getUserPerson(items.get(i - 1).getUser());
            fields[i][0] = person.getName();
            fields[i][1] = person.getSurname();
            fields[i][2] = person.getPatronymic();
            fields[i][3] = person.getSex() ? "Мужской" : "Женский";
        }

        ExcelWorkbookUtils.writeTable(fileName, "Сотрудники", fields);
    }

    private void generateQrCode() {
        Employee selected = employeesTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            SportiksAlertType.ERROR.getAlert(
                    "Ошибка", "Для генерации QR-кода выберите элемент из таблицы!", null
            ).showAndWait();
            return;
        }

        Person person = DatabaseHelper.getUserPerson(selected.getUser());

        String data = String.format(
                "Имя: %s\nФамилия: %s\nОтчество: %s\nПол: %s\n",
                person.getName(), person.getSurname(), person.getPatronymic(),
                person.getSex() ? "Мужской" : "Женский"
        );

        Image image = BarcodeUtils.generateQrCodeImage(data);
        ImageView imageView = new ImageView(image);
        Alert alert = SportiksAlertType.QR.getAlert("QR-код", null, imageView);
        alert.showAndWait();
    }

    private void removeCustomer() {
        Employee selected = employeesTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            SportiksAlertType.ERROR.getAlert(
                    "Ошибка", "Для удаления аккаунта Сотрудника выберите элемент из таблицы!", null
            ).showAndWait();
            return;
        }

        if (SportiksAlertType.CONFIRMATION.getAlert("Подтверждение",
                        "Вы уверены, что хотите удалить аккаунт сотрудника?", null)
                .showAndWait().get() == AlertButtonTypes.noButtonType)
            return;

        DatabaseHelper.removeEmployee(selected);
        employeesTableView.getItems().remove(selected);
    }

    private void editCustomer() {
        Employee selected = employeesTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            SportiksAlertType.ERROR.getAlert(
                    "Ошибка", "Для редактирования аккаунта сотрудника выберите элемент из таблицы!", null
            ).showAndWait();
            return;
        }

        EditPersonController.setEditablePerson(DatabaseHelper.getUserPerson(selected.getUser()));

        SceneController.getStageByScene(Scene.EDIT_PERSON,
                Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/person.png"))
        ).showAndWait();

        updateTableItems();
    }

    private void createEmployee() {
        SceneController.getStageByScene(Scene.CREATE_EMPLOYEE,
                Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/person.png"))
        ).showAndWait();

        updateTableItems();
    }

    private void applyActions() {
        menuImage.setOnMouseClicked(mouseEvent -> goToMenu());
        updateTableItem.setOnAction(actionEvent -> updateTableItems());
        exportTableItem.setOnAction(actionEvent -> exportTable());
        contextMenuQrItem.setOnAction(actionEvent -> generateQrCode());
        removeEmployeeItem.setOnAction(actionEvent -> removeCustomer());
        editEmployeeItem.setOnAction(actionEvent -> editCustomer());
        createEmployeeItem.setOnAction(actionEvent -> createEmployee());
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
        List<Employee> employeesItems = DatabaseHelper.getEmployees();
        ObservableList<Employee> data = FXCollections.observableArrayList(employeesItems);
        employeesTableView.setItems(data);
    }

}
