package dmitr.app.sportiksclub.util;

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

}
