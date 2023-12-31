package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.SportiksClub;
import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Membership;
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

public class AllMembershipsController implements Initializable {

    @FXML
    private ImageView menuImage;

    @FXML
    private TableView<Membership> membershipsTableView;

    @FXML
    private TableColumn<Membership, String> initialsTableColumn;

    @FXML
    private TableColumn<Membership, String> membershipTypeNameTableColumn;

    @FXML
    private TableColumn<Membership, String> beginDateTableColumn;

    @FXML
    private TableColumn<Membership, String> endDateTableColumn;

    @FXML
    private TableColumn<Membership, String> hasTrainerTableColumn;

    @FXML
    private MenuItem contextMenuQrItem;

    @FXML
    private MenuItem removeMembershipItem;

    @FXML
    private MenuItem updateTableItem;

    @FXML
    private MenuItem exportTableItem;

    @FXML
    private MenuItem createMembershipItem;

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
        File file = FileUtils.getFileByChooser(stage, "Сохранить таблицу как...", FileUtils.excelExtensionfilter);

        if (file == null)
            return;

        ObservableList<Membership> items = membershipsTableView.getItems();

        String[][] fields = new String[items.size() + 1][5];
        fields[0] = new String[]{"Клиент", "Абонемент", "Дата (начало)", "Дата (конец)", "Наличие тренера"};

        for (int i = 1; i <= items.size(); i++) {
            fields[i][0] = PersonUtils
                    .getInitials(DatabaseHelper.getUserPerson(items.get(i - 1).getCustomer().getUser()));
            fields[i][1] = items.get(i - 1).getMembershipType().getName();
            fields[i][2] = items.get(i - 1).getBeginDate().toString();
            fields[i][3] = items.get(i - 1).getEndDate().toString();
            fields[i][4] = items.get(i - 1).getMembershipType().hasTrainer() ? "Да" : "Нет";
        }

        ExcelWorkbookUtils.writeTable(file.getAbsolutePath(), "Абонементы", fields);
    }

    private void generateQrCode() {
        Membership selected = membershipsTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            SportiksAlertType.ERROR.getAlert(
                    "Ошибка", "Для генерации QR-кода выберите элемент из таблицы!", null
            ).showAndWait();
            return;
        }

        Person person = DatabaseHelper.getUserPerson(selected.getCustomer().getUser());
        String initials = PersonUtils.getInitials(person);

        String data = String.format(
                "Инициалы: %s\nДата (начало): %s\nДата(конец): %s\nНаличие тренера: %s\n",
                initials, selected.getBeginDate().toString(), selected.getEndDate().toString(),
                selected.getMembershipType().hasTrainer() ? "Да" : "Нет"
        );

        Image image = BarcodeUtils.generateQrCodeImage(data);
        ImageView imageView = new ImageView(image);
        Alert alert = SportiksAlertType.QR.getAlert("QR-код", null, imageView);
        alert.showAndWait();
    }

    private void removeMembership() {
        Membership selected = membershipsTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            SportiksAlertType.ERROR.getAlert(
                    "Ошибка", "Для удаления абонемента выберите элемент из таблицы!", null
            ).showAndWait();
            return;
        }

        if (SportiksAlertType.CONFIRMATION.getAlert("Подтверждение",
                        "Вы уверены, что хотите удалить абонемент клиента?", null)
                .showAndWait().get() == AlertButtonTypes.noButtonType) {
            return;
        }

        DatabaseHelper.removeMembership(selected);
        membershipsTableView.getItems().remove(selected);
    }

    private void createMembership() {
        SceneController.getStageByScene(Scene.CREATE_MEMBERSHIP,
                Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/person.png"))
        ).showAndWait();

        updateTableItems();
    }

    private void applyActions() {
        menuImage.setOnMouseClicked(mouseEvent -> goToMenu());
        updateTableItem.setOnAction(actionEvent -> updateTableItems());
        exportTableItem.setOnAction(actionEvent -> exportTable());
        contextMenuQrItem.setOnAction(actionEvent -> generateQrCode());
        removeMembershipItem.setOnAction(actionEvent -> removeMembership());
        createMembershipItem.setOnAction(actionEvent -> createMembership());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();

        initialsTableColumn.setCellValueFactory(
                c -> new ReadOnlyStringWrapper(
                        PersonUtils.getInitials(DatabaseHelper.getUserPerson(c.getValue().getCustomer().getUser()))
                )
        );

        membershipTypeNameTableColumn.setCellValueFactory(
                c -> new ReadOnlyStringWrapper(c.getValue().getMembershipType().getName())
        );

        beginDateTableColumn.setCellValueFactory(
                c -> new ReadOnlyStringWrapper(c.getValue().getBeginDate().toString())
        );

        endDateTableColumn.setCellValueFactory(
                c -> new ReadOnlyStringWrapper(c.getValue().getEndDate().toString())
        );

        hasTrainerTableColumn.setCellValueFactory(
                c -> new ReadOnlyStringWrapper(c.getValue().getMembershipType().hasTrainer() ? "Да" : "Нет")
        );

        updateTableItems();
    }

    private void updateTableItems() {
        List<Membership> membershipsItems = DatabaseHelper.getMemberships();
        ObservableList<Membership> data = FXCollections.observableArrayList(membershipsItems);
        membershipsTableView.setItems(data);
    }

}
