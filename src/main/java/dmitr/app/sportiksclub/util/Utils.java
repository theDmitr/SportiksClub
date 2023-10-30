package dmitr.app.sportiksclub.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;

public class Utils {

    public static String getDatabasePath() {
        String userHome = System.getProperty("user.home");
        File appHome = new File(userHome, "/SportiksClub");

        if (!appHome.exists())
            appHome.mkdir();

        return "jdbc:sqlite:" + appHome.getAbsolutePath() + "/sportiksclub.db";
    }

    public static Stage getStageFromActionEvent(ActionEvent actionEvent) {
        return (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }

    public static Alert getAlert(Alert.AlertType alertType, String caption, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(caption);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

}
