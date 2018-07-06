package it.polimi.se2018.view.tools.fx.alert;

import it.polimi.se2018.view.app.JavaFXApp;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class GeneralBox {

    static Stage window;

    /**
     * Class constructor
     */
    GeneralBox() {
        throw new IllegalStateException("Utility JavaFX class");
    }

    /**
     * Show a new window to manage change layer request
     * @param title title
     * @param isLoaded  isLoaded
     * @param loaderName the loader name
     */
    static void initStage(String title, boolean isLoaded, String loaderName) {
        window = new Stage();

        //Locks event in this window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setAlwaysOnTop(true);

        window.setTitle(title);

        if (isLoaded) {
            FXMLLoader loader;
            Pane root;
            //Trying to load FXML
            try {
                loader = new FXMLLoader(JavaFXStageProducer.class.getResource(loaderName));
                root = loader.load();

                window.centerOnScreen();
                window.setScene(new Scene(root));
                //Current window must be close to come back to the caller
                window.showAndWait();
            } catch (Exception e) {
                JavaFXApp.logFxmlLoadError(e.getMessage());
            }
        }
    }

    /**
     * Closes the window
     */
    public static void close() {
        if (window != null) {
            window.close();
        }
    }
}
