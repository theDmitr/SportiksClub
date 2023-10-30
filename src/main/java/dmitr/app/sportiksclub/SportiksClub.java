package dmitr.app.sportiksclub;

import dmitr.app.sportiksclub.database.DatabaseHelper;
import dmitr.app.sportiksclub.model.User;
import dmitr.app.sportiksclub.scene.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class SportiksClub extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        /*FXMLLoader fxmlLoader = new FXMLLoader(SportiksClub.class.getResource("view/Auth.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sportik`s club | Auth");
        stage.setScene(scene);
        stage.show();*/
        SceneController.setScene(stage, "view/Auth.fxml");

        DatabaseHelper.getUserDao().create(new User("123.", "123123"));
    }

}
