package com.example.login_v2;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class AnimationFX {
    public static void transition(String fxml, Node node, AnchorPane anchorPane) throws IOException {
        Parent parent = FXMLLoader.load(AnimationFX.class.getResource(fxml));
        Scene scene = node.getScene();
        Group container = (Group) scene.getRoot();
        container.getChildren().add(parent);

        parent.translateXProperty().set(scene.getWidth());

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(parent.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(900), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        FadeTransition transition = new FadeTransition(Duration.millis(750), anchorPane);
        transition.setFromValue(1.0);
        transition.setToValue(0);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().add(timeline);
        parallelTransition.getChildren().add(transition);

        parallelTransition.setOnFinished(event -> {
            container.getChildren().remove(anchorPane);

        });
        parallelTransition.play();
    }

    public static void transitionBack(String fxml, Node node, AnchorPane anchorPane) throws IOException {
        Parent parent = FXMLLoader.load(AnimationFX.class.getResource(fxml));
        Scene scene = node.getScene();
        Group container = (Group) scene.getRoot();
        container.getChildren().add(parent);

        anchorPane.translateXProperty().set(0);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(anchorPane.translateXProperty(), scene.getWidth(), Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1500), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        FadeTransition transition = new FadeTransition(Duration.millis(750), parent);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);

        FadeTransition subTransition = new FadeTransition(Duration.millis(1200), anchorPane);
        subTransition.setFromValue(1.0);
        subTransition.setToValue(0.0);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().add(timeline);
        parallelTransition.getChildren().add(transition);
        parallelTransition.getChildren().add(subTransition);

        parallelTransition.setOnFinished(event -> {
            container.getChildren().remove(anchorPane);

        });
        parallelTransition.play();
    }
}
