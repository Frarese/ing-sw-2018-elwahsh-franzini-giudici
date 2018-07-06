package it.polimi.se2018.view.tools.fx.controller;


import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.fx.alert.ChangeLayerBox;
import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import it.polimi.se2018.view.tools.fx.alert.GameMenuBox;
import javafx.fxml.FXML;

/**
 * Manages game menu
 *
 * @author Mathyas Giudici
 */

public class GameMenuController {

    /**
     * Closes the menu
     */
    @FXML
    private void close() {
        GameMenuBox.close();
    }

    /**
     * Opens change layer's box
     */
    public void changeLayer() {
        ChangeLayerBox.display();
    }

    /**
     * Returns to the lobby
     */
    public void lobby() {
        boolean answer = ConfirmBox.display("Lobby", "Sei sicuro di voler tornare alla lobby?");
        if (answer) {
            JavaFXStageProducer.getApp().getViewActions().leaveMatch();
            close();
        }
    }

    /**
     * Exits from server (logout)
     */
    public void logout() {
        boolean answer = ConfirmBox.displaySafeExit();
        if (answer) {
            JavaFXStageProducer.getApp().getViewActions().logout();
            JavaFXStageProducer.getApp().startLogin(false);
            close();
        }
    }
}
