package dmitr.app.sportiksclub.controller;

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
import java.util.ResourceBundle;

public class CustomerMembershipsController implements Initializable {

    @FXML
    private ImageView menuImage;

    @FXML
    private TableView<Membership> membershipsTableView;

    @FXML
    private TableColumn<Membership, String> nameTableColumn;

    @FXML
    private TableColumn<Membership, String> endDateTableColumn;

    @FXML
    private TableColumn<Membership, String> hasTrainerTableColumn;

    @FXML
    private MenuItem contextMenuQrItem;

    @FXML
    private MenuItem updateTableItem;

    @FXML
    private MenuItem exportTableItem;

    private void exportTable() {
        Stage stage = SceneController.getStage();
        File file = FileUtils.getFileByChooser(stage, "Сохранить таблицу как...", FileUtils.excelExtensionfilter);

        if (file == null)
            return;

        ObservableList<Membership> items = membershipsTableView.getItems();

        String[][] fields = new String[items.size() + 1][3];
        fields[0] = new String[]{"Абонемент", "Действителен до", "Наличие тренера"};

        for (int i = 1; i <= items.size(); i++) {
            fields[i][0] = items.get(i - 1).getMembershipType().getName();
            fields[i][1] = items.get(i - 1).getEndDate().toString();
            fields[i][2] = items.get(i - 1).getMembershipType().hasTrainer() ? "Да" : "Нет";
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

    private void applyActions() {
        menuImage.setOnMouseClicked(mouseEvent -> SceneController.setScene(Scene.CUSTOMER_MENU));
        updateTableItem.setOnAction(actionEvent -> updateTableItems());
        exportTableItem.setOnAction(actionEvent -> exportTable());
        contextMenuQrItem.setOnAction(actionEvent -> generateQrCode());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();

        nameTableColumn.setCellValueFactory(
                d -> new ReadOnlyStringWrapper(d.getValue().getMembershipType().getName())
        );

        endDateTableColumn.setCellValueFactory(
                d -> new ReadOnlyStringWrapper(d.getValue().getEndDate().toString())
        );

        hasTrainerTableColumn.setCellValueFactory(
                d -> new ReadOnlyStringWrapper(d.getValue().getMembershipType().hasTrainer() ? "Да" : "Нет")
        );

        updateTableItems();
    }

    private void updateTableItems() {
        User user = DatabaseHelper.getAuthorizedUser();
        List<Membership> membershipItems = DatabaseHelper.getMemberships(user);

        ObservableList<Membership> data = FXCollections.observableArrayList(membershipItems);
        membershipsTableView.setItems(data);
    }

}
