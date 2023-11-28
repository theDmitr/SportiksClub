package dmitr.app.sportiksclub.util;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsTest {

    @Test
    void addDaysToDate() {
        Date first = Date.valueOf(LocalDate.of(2020, 5, 1));
        Date second = Date.valueOf(LocalDate.of(2020, 5, 3));
        first = DateUtils.addDaysToDate(first, 2);
        assertEquals(first, second);
    }
}