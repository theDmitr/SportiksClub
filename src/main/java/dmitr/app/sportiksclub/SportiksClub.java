package dmitr.app.sportiksclub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SportiksClub extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(SportiksClub.class.getResource("view/Auth.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sportik`s club | Auth");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
