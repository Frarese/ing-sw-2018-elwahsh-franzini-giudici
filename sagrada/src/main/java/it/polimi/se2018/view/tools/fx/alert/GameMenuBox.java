package it.polimi.se2018.view.tools.fx.alert;

import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to create game menu box to show to user
 *
 * @author Mathyas Giudici
 */

public class GameMenuBox {

    /**
     * Class constructor
     */
    private GameMenuBox() {
        throw new IllegalStateException("Utility JavaFX class");
    }


    private static Stage window;

    /**
     * Shows the game's menu
     */
    public static void display() {
        window = new Stage();

        //Locks event in this window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setAlwaysOnTop(true);

        window.setTitle("Menu");

        FXMLLoader loader;
        Pane root;
        try {
            loader = new FXMLLoader(JavaFXStageProducer.class.getResource("fxmlFiles/gameMenu.fxml"));
            root = loader.load();

            window.centerOnScreen();
            window.setScene(new Scene(root));

            //Current window must be close to come back to the caller
            window.showAndWait();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "Non sono riuscito a caricare FXML");
        }
    }

    /**
     * Close the menu's window
     */
    public static void close() {
        if (window != null) {
            window.close();
        }
    }
}

