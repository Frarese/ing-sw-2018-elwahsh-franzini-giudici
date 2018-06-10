package it.polimi.se2018.view.tools.fx.alert;

import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameMenuBox {

    private GameMenuBox() {
        throw new IllegalStateException("Utility class");
    }

    private static Stage window;

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

    public static void close() {
        if (window != null) {
            window.close();
        }
    }
}

