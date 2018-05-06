package it.polimi.se2018.view.app;

import it.polimi.se2018.view.*;
import it.polimi.se2018.view.view_message.MessageBox;

import java.util.List;

/**
 * Abstract Class App represents the generic user interface (CLI or JavaFX)
 *
 * @author Mathyas Giudici
 */

public abstract class App implements ControllerActionsInterface, ControllerToolCardActionsInterface {

    /**
     * App variables
     */
    boolean animationEnable;

    /**
     * Player Information variables
     */
    int ownerPlayerID;

    String ownerPlayerName;

    boolean useRMI;

    /**
     * Game variables
     */
    List players;

    /**
     * Comunication variables
     */
    ViewActions viewActions;

    ViewToolCardActions viewToolCardActions;

    MessageBox messageBox;

    ModelObserver modelObserver;

}
