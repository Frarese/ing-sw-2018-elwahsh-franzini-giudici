package it.polimi.se2018.view.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class JavaFXStageProducer represents the helper class for JavaFXApp
 *
 * @author Mathyas Giudici
 */

public class JavaFXStageProducer extends Application {

    private JavaFXApp appController;

    private Stage stage;

    public JavaFXStageProducer() {
        super();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
    }

    public Stage getStage() {
        return stage;
    }
}
