/**
 * This class used for create preloading screen.
 * @author Huy To Quang
 */
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

    /**
     * This function starts a stage and set the preloading view as scene to the stage.
     * @param stage
     * @throws IOException
     */
    public void start(Stage stage) throws IOException {
        // Load preloader
        parent = FXMLLoader.load(getClass().getResource("/preloading-view.fxml"));
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    /**
     * This function closes the current stage whenever the log in screen starts.
     * @param event
     */
    @Override
    public void handleStateChangeNotification(StateChangeNotification event) {
        // Preloader disappears when application starts
        if(event.getType()==StateChangeNotification.Type.BEFORE_START) {
            Stage stage = (Stage) parent.getScene().getWindow();
            stage.close();
        }
    }
}
