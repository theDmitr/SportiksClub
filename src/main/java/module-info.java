module dmitr.app.sportiksclub {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.xerial.sqlitejdbc;
    requires ormlite.jdbc;

    opens dmitr.app.sportiksclub to javafx.fxml;
    opens dmitr.app.sportiksclub.controller to javafx.fxml;
    opens dmitr.app.sportiksclub.model to ormlite.jdbc;

    exports dmitr.app.sportiksclub;
    exports dmitr.app.sportiksclub.model to ormlite.jdbc;
}