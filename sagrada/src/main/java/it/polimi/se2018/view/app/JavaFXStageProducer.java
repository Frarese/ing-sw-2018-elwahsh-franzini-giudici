package it.polimi.se2018.view.app;

import javafx.application.Application;
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
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public Stage getStage() {
        return stage;
    }
}
