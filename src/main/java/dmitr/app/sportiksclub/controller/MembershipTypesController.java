package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.MembershipType;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import dmitr.app.sportiksclub.util.BarcodeUtils;
import dmitr.app.sportiksclub.util.ExcelWorkbookUtils;
import dmitr.app.sportiksclub.util.FileUtils;
import dmitr.app.sportiksclub.util.SportiksAlertType;
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

public class MembershipTypesController implements Initializable {

    @FXML
    private ImageView menuImage;

    @FXML
    private TableView<MembershipType> membershipTypesTableView;

    @FXML
    private TableColumn<MembershipType, String> nameTableColumn;

    @FXML
    private TableColumn<MembershipType, String> durationTableColumn;

    @FXML
    private TableColumn<MembershipType, String> hasTrainerTableColumn;

    @FXML
    private MenuItem contextMenuQrItem;

    @FXML
    private MenuItem updateTableItem;

    @FXML
    private MenuItem exportTableItem;

    private void goToMenu() {
        User user = DatabaseHelper.getAuthorizedUser();

        if (user == null) {
            SceneController.setScene(Scene.GUEST_MENU);
            return;
        }

        switch (user.getRole()) {
            case ADMIN -> SceneController.setScene(Scene.ADMIN_MENU);
            case EMPLOYEE -> SceneController.setScene(Scene.EMPLOYEE_MENU);
            case CUSTOMER -> SceneController.setScene(Scene.CUSTOMER_MENU);
        }
    }

    private void exportTable() {
        Stage stage = SceneController.getStage();
        File file = FileUtils.getFileByChooser(stage,
                "Сохранить таблицу как...", FileUtils.excelExtensionfilter);

        if (file == null)
            return;

        String fileName = file.getAbsolutePath();

        ObservableList<MembershipType> items = membershipTypesTableView.getItems();

        String[][] fields = new String[items.size() + 1][3];
        fields[0] = new String[]{"Абонемент", "Длительность (дни)", "Наличие тренера"};

        for (int i = 1; i <= items.size(); i++) {
            fields[i][0] = items.get(i - 1).getName();
            fields[i][1] = Integer.toString(items.get(i - 1).getDuration());
            fields[i][2] = items.get(i - 1).hasTrainer() ? "Да" : "Нет";
        }

        ExcelWorkbookUtils.writeTable(fileName, "Абонементы", fields);
    }

    private void generateQrCode() {
        MembershipType selected = membershipTypesTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            SportiksAlertType.ERROR.getAlert(
                    "Ошибка", "Для генерации QR-кода выберите элемент из таблицы!"
            ).showAndWait();
            return;
        }

        String data = String.format(
                "Абонемент: %s\nДлительность (дни): %s\nНаличие тренера: %s\n",
                selected.getName(), selected.getDuration(), selected.hasTrainer() ? "Да" : "Нет"
        );

        Alert alert = SportiksAlertType.QR.getAlert("QR-код", null);
        Image image = BarcodeUtils.generateQrCodeImage(data);
        ImageView imageView = new ImageView(image);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }

    private void applyActions() {
        menuImage.setOnMouseClicked(mouseEvent -> goToMenu());
        updateTableItem.setOnAction(actionEvent -> updateTableItems());
        exportTableItem.setOnAction(actionEvent -> exportTable());
        contextMenuQrItem.setOnAction(actionEvent -> generateQrCode());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();

        nameTableColumn.setCellValueFactory(
                m -> new ReadOnlyStringWrapper(m.getValue().getName())
        );

        durationTableColumn.setCellValueFactory(
                m -> new ReadOnlyStringWrapper(Integer.toString(m.getValue().getDuration()))
        );

        hasTrainerTableColumn.setCellValueFactory(
                m -> new ReadOnlyStringWrapper(m.getValue().hasTrainer() ? "Да" : "Нет")
        );

        updateTableItems();
    }

    private void updateTableItems() {
        List<MembershipType> membershipTypeItems = DatabaseHelper.getMembershipTypes();
        ObservableList<MembershipType> data = FXCollections.observableArrayList(membershipTypeItems);
        membershipTypesTableView.setItems(data);
    }

}
