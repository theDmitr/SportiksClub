package dmitr.app.sportiksclub.util;

import dmitr.app.sportiksclub.SportiksClub;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Objects;

public enum SportiksAlertType {

    INFORMATION(Alert.AlertType.INFORMATION, Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/information.png"))),
    ERROR(Alert.AlertType.ERROR, Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/error.png"))),
    QR(Alert.AlertType.INFORMATION, Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/qr-code.png")));

    private final Alert.AlertType alertType;
    private final Image icon;

    SportiksAlertType(Alert.AlertType alertType, InputStream iconStream) {
        this.alertType = alertType;
        this.icon = new Image(iconStream);
    }

    public static Stage getStageFromAlert(Alert alert) {
        return (Stage) alert.getDialogPane().getScene().getWindow();
    }

    public Alert getAlert(String caption, String content) {
        Alert alert = new Alert(alertType);

        getStageFromAlert(alert).getIcons().add(icon);

        alert.setTitle(caption);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.setGraphic(null);

        return alert;
    }

}
