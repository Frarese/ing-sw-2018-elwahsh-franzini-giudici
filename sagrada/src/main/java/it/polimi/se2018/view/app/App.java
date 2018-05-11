package it.polimi.se2018.view.app;

import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.view.*;
import it.polimi.se2018.view.view_message.MessageBox;

import java.util.ArrayList;
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
    protected boolean animationEnable;

    /**
     * Player Information variables
     */
    protected int ownerPlayerID;

    protected String ownerPlayerName;

    protected boolean useRMI;

    /**
     * Game variables
     */
    protected List<PlayerView> players;
    protected ArrayList<MatchIdentifier> invites;

    /**
     * Comunication variables
     */
    protected ViewActions viewActions;

    protected ViewToolCardActions viewToolCardActions;

    protected MessageBox messageBox;

    protected ModelObserver modelObserver;

    public App() {
        this.animationEnable = true;
        this.ownerPlayerID = 0;
        this.ownerPlayerName = null;
        this.useRMI = false;
        this.players = null;
        this.viewActions = new ViewActions();
        this.viewToolCardActions = new ViewToolCardActions();
        this.messageBox = new MessageBox();
        this.modelObserver = new ModelObserver();
    }
}
