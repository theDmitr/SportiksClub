package dmitr.app.sportiksclub.util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtils {

    public static Date addDaysToDate(Date date, int days) {
        LocalDate localDate = date.toLocalDate();
        localDate = localDate.plusDays(days);
        return Date.valueOf(localDate);
    }

}
