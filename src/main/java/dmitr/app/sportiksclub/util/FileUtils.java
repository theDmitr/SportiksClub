package dmitr.app.sportiksclub.util;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileUtils {

    public static final FileChooser.ExtensionFilter excelExtensionfilter =
            new FileChooser.ExtensionFilter("Microsoft Excel Spreadsheet", "*.xls");

    /**
     * Возвращает выбранный файл для сохранения, вызывая FileChooser
     *
     * @param stage           окно
     * @param title           заголовок окна
     * @param extensionFilter расширение файла
     * @return файл
     */
    public static File getFileByChooser(Stage stage, String title, FileChooser.ExtensionFilter extensionFilter) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(extensionFilter);

        return chooser.showSaveDialog(stage);
    }

}
