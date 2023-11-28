package dmitr.app.sportiksclub.util;

import java.io.File;

public class DatabaseUtils {

    /**
     * Возвращает путь к БД
     *
     * @return путь
     */
    public static String getDatabasePath() {
        String userHome = System.getProperty("user.home");
        File appHome = new File(userHome, "/SportiksClub");

        if (!appHome.exists())
            appHome.mkdir();

        return "jdbc:sqlite:" + appHome.getAbsolutePath() + "/sportiksclub.db";
    }

}
