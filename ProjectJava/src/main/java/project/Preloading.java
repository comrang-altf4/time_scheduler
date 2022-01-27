package project;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class Preloading extends Preloader {
    private Parent parent;

    public void start(Stage stage) throws IOException {
        // Load preloader
        parent = FXMLLoader.load(getClass().getResource("/preloading-view.fxml"));
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification event) {
        // Preloader disappears when application starts
        if(event.getType()==StateChangeNotification.Type.BEFORE_START) {
            Stage stage = (Stage) parent.getScene().getWindow();
            stage.close();
        }
    }
}
