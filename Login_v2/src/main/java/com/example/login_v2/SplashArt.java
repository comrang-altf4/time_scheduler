package com.example.login_v2;

import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class SplashArt extends Preloader {
    Group topGroup;
    Parent splashParent;

    public interface SharedScene {
        Parent getParentNode();
    }

    public Scene createSplashScene() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("SplashArt_v2.fxml"));
        splashParent = new Group(anchorPane);
        topGroup = new Group(splashParent);
        Scene scene = new Scene(topGroup);
        return scene;
    }

    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(createSplashScene());
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification event) {
        if(event.getType()==StateChangeNotification.Type.BEFORE_START) {
            SharedScene sharedScene = (SharedScene) event.getApplication();
            fadeInto(sharedScene.getParentNode());
        }
    }

    private void fadeInto(Parent parent) {
        topGroup.getChildren().add(0, parent);
        FadeTransition transition = new FadeTransition(Duration.millis(500), splashParent);
        transition.setFromValue(1.0);
        transition.setToValue(0);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                topGroup.getChildren().remove(splashParent);
            }
        });
        transition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
