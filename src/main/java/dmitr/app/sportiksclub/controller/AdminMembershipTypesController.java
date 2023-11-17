package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.SportiksClub;
import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Customer;
import dmitr.app.sportiksclub.model.MembershipType;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import dmitr.app.sportiksclub.util.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminMembershipTypesController implements Initializable {
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

    @FXML
    private MenuItem removeTableItem;

    @FXML
    private MenuItem createTableItem;

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
                    "Ошибка", "Для генерации QR-кода выберите элемент из таблицы!", null
            ).showAndWait();
            return;
        }

        String data = String.format(
                "Абонемент: %s\nДлительность (дни): %s\nНаличие тренера: %s\n",
                selected.getName(), selected.getDuration(), selected.hasTrainer() ? "Да" : "Нет"
        );

        Image image = BarcodeUtils.generateQrCodeImage(data);
        ImageView imageView = new ImageView(image);
        Alert alert = SportiksAlertType.QR.getAlert("QR-код", null, imageView);
        alert.showAndWait();
    }

    private void removeMembershipType() {
        MembershipType selected = membershipTypesTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            SportiksAlertType.ERROR.getAlert(
                    "Ошибка", "Для удаления абонемента выберите элемент из таблицы!", null
            ).showAndWait();
            return;
        }

        if (SportiksAlertType.CONFIRMATION.getAlert("Подтверждение",
                        "Вы уверены, что хотите удалить абонемент?", null)
                .showAndWait().get() == AlertButtonTypes.noButtonType)
            return;

        if (DatabaseHelper.isMembershipTypeUsed(selected)) {
            SportiksAlertType.ERROR.getAlert(
                    "Ошибка",
                    "Один или несколько абонементов данного типа активны на текущий момент!", null
            ).showAndWait();
            return;
        }

        DatabaseHelper.removeMembershipType(selected);
        membershipTypesTableView.getItems().remove(selected);
    }

    private void createMembershipType() {
        SceneController.getStageByScene(Scene.CREATE_MEMBERSHIP_TYPE,
                Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/membership.png"))
        ).showAndWait();

        updateTableItems();
    }

    private void applyActions() {
        menuImage.setOnMouseClicked(mouseEvent -> SceneController.setScene(Scene.ADMIN_MENU));
        updateTableItem.setOnAction(actionEvent -> updateTableItems());
        exportTableItem.setOnAction(actionEvent -> exportTable());
        contextMenuQrItem.setOnAction(actionEvent -> generateQrCode());
        removeTableItem.setOnAction(actionEvent -> removeMembershipType());
        createTableItem.setOnAction(actionEvent -> createMembershipType());
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
