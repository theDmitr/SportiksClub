package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.MembershipType;
import dmitr.app.sportiksclub.model.Person;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import dmitr.app.sportiksclub.util.Utils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MembershipTypesController implements Initializable {

    @FXML
    private ImageView menuImage;

    @FXML
    private TableView<MembershipTypeItem> membershipTypesTableView;

    @FXML
    private TableColumn<MembershipTypeItem, String> nameTableColumn;

    @FXML
    private TableColumn<MembershipTypeItem, String> durationTableColumn;

    @FXML
    private TableColumn<MembershipTypeItem, String> hasTrainerTableColumn;

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
            case CUSTOMER -> SceneController.setScene(Scene.CUSTOMER_MENU);
        }
    }

    private void exportTable() {
        Stage stage = SceneController.getStage();
        File file = Utils.getFileByChooser(stage, "Сохранить таблицу как...", Utils.excelExtensionfilter);

        if (file == null)
            return;

        String fileName = file.getAbsolutePath();

        ObservableList<MembershipTypeItem> items = membershipTypesTableView.getItems();

        String[][] fields = new String[items.size() + 1][3];
        fields[0] = new String[] { "Абонемент", "Длительность (дни)", "Наличие тренера" };

        for (int i = 1; i <= items.size(); i++) {
            fields[i][0] = items.get(i - 1).getName();
            fields[i][1] = items.get(i - 1).getDuration();
            fields[i][2] = items.get(i - 1).getHasTrainer();
        }

        Utils.writeTable(fileName, "Абонементы", fields);
    }

    private void applyActions() {
        menuImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goToMenu();
            }
        });

        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                updateTableItems();
            }
        });

        exportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                exportTable();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();

        nameTableColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getName()));
        durationTableColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getDuration()));
        hasTrainerTableColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getHasTrainer()));

        updateTableItems();
    }

    private void updateTableItems() {
        List<MembershipTypeItem> membershipTypeItems =
                DatabaseHelper.getMembershipTypes().stream().map(MembershipTypeItem::new).toList();
        ObservableList<MembershipTypeItem> data = FXCollections.observableArrayList(membershipTypeItems);
        membershipTypesTableView.setItems(data);
    }

    public static class MembershipTypeItem {

        private String name;
        private String duration;
        private String hasTrainer;
        private MembershipType membershipType;

        public MembershipTypeItem(MembershipType membershipType) {
            name = membershipType.getName();
            duration = Integer.toString(membershipType.getDuration());
            hasTrainer = membershipType.hasTrainer() ? "Да" : "Нет";
            this.membershipType = membershipType;
        }

        public String getName() {
            return name;
        }

        public String getDuration() {
            return duration;
        }

        public String getHasTrainer() {
            return hasTrainer;
        }

        public MembershipType getMembershipType() {
            return membershipType;
        }

    }

}
