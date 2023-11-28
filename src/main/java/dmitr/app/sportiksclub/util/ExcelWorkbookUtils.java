package dmitr.app.sportiksclub.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWorkbookUtils {

    /**
     * Записывает данные в xls таблицу и сохраняет её
     *
     * @param fileName     путь к файлу
     * @param sheetCaption заголовок листа таблицы
     * @param fields       поля
     */
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

}
