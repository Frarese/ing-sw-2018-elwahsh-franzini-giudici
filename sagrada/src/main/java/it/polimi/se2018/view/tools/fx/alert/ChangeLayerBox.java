package it.polimi.se2018.view.tools.fx.alert;

/**
 * Class to create change layer box to show to user
 *
 * @author Mathyas Giudici
 */

public class ChangeLayerBox extends GeneralBox {

    /**
     * Class constructor
     */
    private ChangeLayerBox() {
        super();
    }

    /**
     * Show a new window to manage change layer request
     */
    public static void display() {
        initStage("Cambio Connessione", true, "fxmlFiles/layerChange.fxml");
    }
}
