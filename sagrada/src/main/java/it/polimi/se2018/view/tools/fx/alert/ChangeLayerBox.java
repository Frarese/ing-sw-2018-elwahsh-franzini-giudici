package it.polimi.se2018.view.tools.fx.alert;

import it.polimi.se2018.view.app.JavaFXApp;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class to create change layer box to show to user
 *
 * @author Mathyas Giudici
 */

public class ChangeLayerBox {

    private static Stage window;

    public static void display() {
        window = new Stage();

        //Locks event in this window
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Cambio Connessione");

        FXMLLoader loader;
        Pane root;
        //Trying to load FXML
        try {
            loader = new FXMLLoader(JavaFXStageProducer.class.getResource("fxmlFiles/layerChange.fxml"));
            root = loader.load();

            window.centerOnScreen();
            window.setScene(new Scene(root));
            //Current window must be close to come back to the caller
            window.showAndWait();
        } catch (Exception e) {
            JavaFXApp.logFxmlLoadError();
        }
    }

    public static void close() {
        if (window != null) {
            window.close();
        }
    }
}
