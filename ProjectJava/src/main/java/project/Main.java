/**
 * This class is the start of the application.
 * @author Huy To Quang
 */

package project;

import backend.Background;
import backend.Sess1on;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    protected static Sess1on session = new Sess1on();

    /**
     * This function delays the start of first screen for preloading effect.
     * @throws InterruptedException whenever there is a problem with thread being interrupted
     */
    @Override
    public void init() throws InterruptedException {
        // Delay app starting
        Thread.sleep(1000);
    }

    /**
     * This function starts a stage and set the log in screen as scene to the stage.
     * @param stage stage that will be started
     * @throws IOException whenever there is a problem with file loading
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load login screen
        Parent parent = FXMLLoader.load(getClass().getResource("/login-view.fxml"));
        Group group = new Group(parent);
        stage.setScene(new Scene(group));
        stage.initStyle(StageStyle.DECORATED);
        stage.show();

        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Kalendar");
        stage.setResizable(false);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Set system property to start preloader first
        System.setProperty("javafx.preloader", Preloading.class.getCanonicalName());

        // Set background running to send email
        Background background = new Background();
        background.start();

        // Start the application
        launch(Main.class, args);
    }

    /**
     * This class return current session that the user will be working on
     * @return current session
     */
    public static Sess1on getSession() {
        // Get session
        return session;
    }
}