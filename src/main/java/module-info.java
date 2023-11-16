module dmitr.app.sportiksclub {
    requires javafx.controls;
    requires javafx.fxml;

    requires ormlite.jdbc;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    requires org.apache.poi.poi;

    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires org.controlsfx.controls;

    opens dmitr.app.sportiksclub to javafx.fxml;
    opens dmitr.app.sportiksclub.controller to javafx.fxml;
    opens dmitr.app.sportiksclub.model to ormlite.jdbc;

    exports dmitr.app.sportiksclub;
    exports dmitr.app.sportiksclub.model to ormlite.jdbc;
}