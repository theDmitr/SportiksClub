package dmitr.app.sportiksclub.controller;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Customer;
import dmitr.app.sportiksclub.model.Membership;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.scene.Scene;
import dmitr.app.sportiksclub.scene.SceneController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MembershipsController implements Initializable {

    @FXML
    private ImageView menuImage;

    @FXML
    private TableView<MembershipItem> membershipsTableView;

    @FXML
    private TableColumn<MembershipItem, String> nameTableColumn;

    @FXML
    private TableColumn<MembershipItem, String> endDateTableColumn;

    @FXML
    private TableColumn<MembershipItem, String> hasTrainerTableColumn;

    private void applyActions() {
        menuImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SceneController.setScene(Scene.CUSTOMER_MENU);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyActions();

        nameTableColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getName()));
        endDateTableColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getEndDate()));
        hasTrainerTableColumn.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getHasTrainer()));

        updateTableItems();
    }

    private void updateTableItems() {
        User user = DatabaseHelper.getAuthorizedUser();
        List<MembershipItem> membershipItems =
                DatabaseHelper.getMemberships(user).stream().map(MembershipsController.MembershipItem::new).toList();

        ObservableList<MembershipsController.MembershipItem> data = FXCollections.observableArrayList(membershipItems);
        membershipsTableView.setItems(data);
    }

    public static class MembershipItem {

        private String name;
        private String endDate;
        private String hasTrainer;
        private Membership membership;

        public MembershipItem(Membership membership) {
            name = membership.getMembershipType().getName();
            endDate = membership.getEndDate().toString();
            hasTrainer = membership.getMembershipType().hasTrainer() ? "Да" : "Нет";
            this.membership = membership;
        }

        public String getName() {
            return name;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getHasTrainer() {
            return hasTrainer;
        }

        public Membership getMembership() {
            return membership;
        }

    }

}
