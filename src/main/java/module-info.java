module dmitr.app.sportiksclub {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens dmitr.app.sportiksclub to javafx.fxml;

    exports dmitr.app.sportiksclub;
}