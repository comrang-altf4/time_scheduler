package com.example.login_v2;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args) {
        System.setProperty("javafx.preloader", SplashArt.class.getCanonicalName());
        launch(StartScreen.class, args);
    }
}
