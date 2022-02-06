package transition;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;


public class AnimationFX {
    public static void transitionForward(String fxml, Parent currentParent) throws IOException {
        // Creating queue for scenes to appear
        Parent newParent = FXMLLoader.load(AnimationFX.class.getResource(fxml));
        Scene scene = currentParent.getScene();
        Group root = (Group) scene.getRoot();
        root.getChildren().add(newParent);

        currentParent.translateXProperty().set(0);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(currentParent.translateXProperty(), -scene.getWidth(), Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1500), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        FadeTransition transition = new FadeTransition(Duration.millis(1200), newParent);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);

        FadeTransition subTransition = new FadeTransition(Duration.millis(500), currentParent);
        subTransition.setFromValue(1.0);
        subTransition.setToValue(0.0);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().add(transition);
        parallelTransition.getChildren().add(subTransition);
        parallelTransition.getChildren().add(timeline);

        parallelTransition.setOnFinished(event -> root.getChildren().remove(currentParent));
        parallelTransition.play();
    }

    public static void transitionBackward(String fxml, Parent currentParent) throws IOException {
        // Creating queue for scenes to appear
        Parent newParent = FXMLLoader.load(AnimationFX.class.getResource(fxml));
        Scene scene = currentParent.getScene();
        Group root = (Group) scene.getRoot();
        root.getChildren().add(newParent);

        currentParent.translateXProperty().set(0);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(currentParent.translateXProperty(), scene.getWidth(), Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1500), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        FadeTransition transition = new FadeTransition(Duration.millis(1200), newParent);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);

        FadeTransition subTransition = new FadeTransition(Duration.millis(500), currentParent);
        subTransition.setFromValue(1.0);
        subTransition.setToValue(0.0);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().add(timeline);
        parallelTransition.getChildren().add(transition);
        parallelTransition.getChildren().add(subTransition);

        parallelTransition.setOnFinished(event -> root.getChildren().remove(currentParent));
        parallelTransition.play();
    }
}
