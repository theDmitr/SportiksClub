package dmitr.app.sportiksclub.util;

import dmitr.app.sportiksclub.SportiksClub;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Objects;

public enum SportiksAlertType {

    INFORMATION(Alert.AlertType.INFORMATION, Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/information.png"))),
    ERROR(Alert.AlertType.ERROR, Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/error.png"))),
    QR(Alert.AlertType.INFORMATION, Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/qr-code.png"))),
    CONFIRMATION(Alert.AlertType.CONFIRMATION, Objects.requireNonNull(SportiksClub.class.getResourceAsStream("image/confirm.png")), AlertButtonTypes.yesButtonType, AlertButtonTypes.noButtonType);

    private static final String DIALOG_STYLE = Objects.requireNonNull(SportiksClub.class.getResource("css/style.css")).toString();
    private final Alert.AlertType alertType;
    private final Image icon;
    private final ButtonType[] buttonTypes;
    SportiksAlertType(Alert.AlertType alertType, InputStream iconStream, ButtonType... buttonTypes) {
        this.alertType = alertType;
        this.icon = new Image(iconStream);
        this.buttonTypes = buttonTypes;
    }

    public static Stage getStageFromAlert(Alert alert) {
        return (Stage) alert.getDialogPane().getScene().getWindow();
    }

    public Alert getAlert(String caption, String content, ImageView imageView) {
        Alert alert = new Alert(alertType);

        getStageFromAlert(alert).getIcons().add(icon);

        alert.setTitle(caption);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.setGraphic(imageView);
        alert.getDialogPane().getStylesheets().add(DIALOG_STYLE);
        alert.getDialogPane().getStyleClass().add("dialog-pane");

        if (buttonTypes.length != 0)
            alert.getButtonTypes().setAll(buttonTypes);

        return alert;
    }

}
