package dmitr.app.sportiksclub.util;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.Person;
import dmitr.app.sportiksclub.scene.SceneController;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class Utils {

    public static final FileChooser.ExtensionFilter excelExtensionfilter =
            new FileChooser.ExtensionFilter("Microsoft Excel Spreadsheet", "*.xls");

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

    public static void writeTable(String fileName, String sheetCaption, String[][] fields) {
        try (HSSFWorkbook workbook = new HSSFWorkbook();
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            HSSFSheet sheet = workbook.createSheet(sheetCaption);

            for (int i = 0; i < fields.length; i++) {
                HSSFRow tableRow = sheet.createRow(i);
                String[] line = fields[i];

                for (int j = 0; j < line.length; j++)
                    tableRow.createCell(j).setCellValue(line[j]);
            }

            workbook.write(fileOutputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static File getFileByChooser(Stage stage, String title, FileChooser.ExtensionFilter extensionFilter) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(extensionFilter);

        return chooser.showSaveDialog(stage);
    }

}
