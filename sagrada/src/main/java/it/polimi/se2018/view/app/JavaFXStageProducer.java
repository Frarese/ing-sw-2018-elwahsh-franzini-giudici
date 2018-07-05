package it.polimi.se2018.view.app;

import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import it.polimi.se2018.view.tools.fx.controller.FXController;
import javafx.application.Application;
import javafx.application.Platform;
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

    private static JavaFXApp app;

    private static Stage stage;

    private static FXController controller;

    @Override
    public synchronized void start(Stage primaryStage) {

        //Setting stage properties
        primaryStage.setTitle("Sagrada Game");
        primaryStage.getIcons().add(new Image(JavaFXStageProducer.class.getResourceAsStream("/it/polimi/se2018/view/images/others/icon.png")));
        primaryStage.centerOnScreen();

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeHandler();
        });

        //Setting static public attributes
        setStage(primaryStage);

        Logger.getGlobal().log(Level.INFO, "Start JavaFX app");
    }

    /**
     * Getter method for controller
     *
     * @return current FXController
     */
    public static FXController getController() {
        return controller;
    }

    /**
     * Setter method for controller
     *
     * @param controller contains the controller to set
     */
    public static void setController(FXController controller) {
        JavaFXStageProducer.controller = controller;
    }

    /**
     * Getter method for stage
     *
     * @return current Application Stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Setter method for stage
     *
     * @param stage contains Application's Stage to set
     */
    private static void setStage(Stage stage) {
        JavaFXStageProducer.stage = stage;
    }

    /**
     * Getter method for app
     *
     * @return current JavaFXApp
     */
    public static JavaFXApp getApp() {
        return app;
    }

    /**
     * Setter method for app
     *
     * @param app contains JavaFXApp to set
     */
    public static void setApp(JavaFXApp app) {
        JavaFXStageProducer.app = app;
    }

    /**
     * Manages the window's close
     */
    private void closeHandler() {
        boolean answer = ConfirmBox.displaySafeExit();
        if (answer) {
            Logger.getGlobal().log(Level.INFO, "Close JavaFX app");
            stage.close();
            Platform.exit();
            System.exit(0);
        }
    }
}
