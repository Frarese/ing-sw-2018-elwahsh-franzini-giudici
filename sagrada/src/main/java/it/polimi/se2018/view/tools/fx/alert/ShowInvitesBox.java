package it.polimi.se2018.view.tools.fx.alert;

import it.polimi.se2018.view.app.JavaFXApp;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.fx.controller.ShowInvitesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Class to create invites box to show to user
 *
 * @author Mathyas Giudici
 */

public class ShowInvitesBox extends GeneralBox {

    /**
     * Class constructor
     */
    private ShowInvitesBox() {
        super();
    }

    /**
     * Shows invites in a table in a new window
     */
    public static void display() {
        initStage("Inviti", false, null);

        FXMLLoader loader;
        Pane root;
        //Trying to load FXML
        try {
            loader = new FXMLLoader(JavaFXStageProducer.class.getResource("fxmlFiles/showInvites.fxml"));
            root = loader.load();
            ShowInvitesController controller = loader.getController();
            controller.setTable();

            window.centerOnScreen();
            window.setScene(new Scene(root));
            //Current window must be close to come back to the caller
            window.showAndWait();
        } catch (Exception e) {
            JavaFXApp.logFxmlLoadError(e.getMessage());
        }
    }
}
