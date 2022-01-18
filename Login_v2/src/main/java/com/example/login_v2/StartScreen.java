package com.example.login_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;


import java.io.IOException;

public class StartScreen extends Application implements SplashArt.SharedScene {
    private static Parent parentNode;

    public Parent getParentNode() {
        return parentNode;
    }

    @Override
    public void init() throws IOException, InterruptedException {
        Parent parent = FXMLLoader.load(getClass().getResource("WelcomeScreen_v2.fxml"));
        parentNode = new Group(parent);
        Thread.sleep(1000);
    }

    public void start(Stage stage) {

    }

    public static void main(String[] args) {
        launch(args);
    }

}