package dmitr.app.sportiksclub.util;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;

public class Utils {

    public static String getDatabasePath() {
        String userHome = System.getProperty("user.home");
        File appHome = new File(userHome, "/SportiksClub");

        if (!appHome.exists())
            appHome.mkdir();

        return "jdbc:sqlite:" + appHome.getAbsolutePath() + "/sportiksclub.db";
    }

    public static Date addDaysToDate(Date date, int days) {
        LocalDate localDate = date.toLocalDate();
        localDate = localDate.plusDays(days);
        return Date.valueOf(localDate);
    }

}
