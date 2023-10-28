module dmitr.app.sportiksclub {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens dmitr.app.sportiksclub to javafx.fxml;
    opens dmitr.app.sportiksclub.controller to javafx.fxml;

    exports dmitr.app.sportiksclub;
}