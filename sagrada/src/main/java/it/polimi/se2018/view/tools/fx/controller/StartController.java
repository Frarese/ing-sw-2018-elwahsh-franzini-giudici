package it.polimi.se2018.view.tools.fx.controller;

import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.fxml.FXML;

/**
 * Manages actions in start page
 *
 * @author Mathyas Giudici
 */

public class StartController extends FXController {

    /**
     * Recalls JavaFXApp and creates login page
     */
    @FXML
    public void loadLogin() {
        JavaFXStageProducer.getApp().startLogin(false);
    }
}
