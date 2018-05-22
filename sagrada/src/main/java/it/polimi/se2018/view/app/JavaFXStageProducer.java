package it.polimi.se2018.view.app;

import it.polimi.se2018.view.tools.fx.controller.FXAbsController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class JavaFXStageProducer represents the helper class for JavaFXApp
 *
 * @author Mathyas Giudici
 */

public class JavaFXStageProducer extends Application {

    protected static JavaFXApp app;

    static Stage stage;

    static FXAbsController controller;

    @Override
    public synchronized void start(Stage primaryStage) {

        //Setting stage properties
        primaryStage.setTitle("Sagrada Game");
        primaryStage.getIcons().add(new Image(JavaFXStageProducer.class.getResourceAsStream("/it/polimi/se2018/view/images/others/icon.png")));
        primaryStage.show();

        //Setting static public attributes
        stage = primaryStage;

        Logger.getGlobal().log(Level.WARNING, "Start JavaFX app");
    }
}
