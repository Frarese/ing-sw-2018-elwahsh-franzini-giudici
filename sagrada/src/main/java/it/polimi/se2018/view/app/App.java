package it.polimi.se2018.view.app;

import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.view.*;
import it.polimi.se2018.view.view_message.MessageBox;
import it.polimi.se2018.view.view_util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Communication variables
     */
    protected ViewActions viewActions;

    protected ViewToolCardActions viewToolCardActions;

    protected MessageBox messageBox;

    protected ModelObserver modelObserver;

    /**
     * Creators componets
     */
    protected CardViewCreator cardViewCreator;

    protected GridViewCreator gridViewCreator;

    protected RoundTrackerViewCreator roundTrackerViewCreator;

    protected ReserveViewCreator reserveViewCreator;

    protected ScoreViewCreator scoreViewCreator;


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

    public PlayerView searchPlayerViewByName(List<PlayerView> players, String wanted) {
        for (PlayerView player : players) {
            if (player.getPlayerName().equals(wanted)) {
                return player;
            }
        }
        Logger.getGlobal().log(Level.WARNING, "Ci sono problemi in PlayerView");
        return null;
    }

    public PlayerView searchPlayerViewById(List<PlayerView> players, int wanted) {
        for (PlayerView player : players) {
            if (player.getPlayerID() == wanted) {
                return player;
            }
        }
        Logger.getGlobal().log(Level.WARNING, "Ci sono problemi in PlayerView");
        return null;
    }

    /**
     * Getter method
     *
     * @return ViewActions class reference
     */
    public ViewActions getViewActions() {
        return viewActions;
    }

    /**
     * Getter method
     *
     * @return boolean value that represents if user is using RMI connection
     */
    public boolean isUseRMI() {
        return useRMI;
    }

    /**
     * Getter method
     *
     * @return the current CardViewCreator
     */
    public CardViewCreator getCardViewCreator() {
        return cardViewCreator;
    }

    /**
     * Getter method
     *
     * @return the current GridViewCreator
     */
    public GridViewCreator getGridViewCreator() {
        return gridViewCreator;
    }

    /**
     * Getter method
     *
     * @return the current RoundTrackerViewCreator
     */
    public RoundTrackerViewCreator getRoundTrackerViewCreator() {
        return roundTrackerViewCreator;
    }

    /**
     * Getter method
     *
     * @return the current ReserveViewCreator
     */
    public ReserveViewCreator getReserveViewCreator() {
        return reserveViewCreator;
    }

    /**
     * Getter method
     *
     * @return the current ScoreViewCreator
     */
    public ScoreViewCreator getScoreViewCreator() {
        return scoreViewCreator;
    }

    /**
     * Getter method
     *
     * @return the current players
     */
    public List<PlayerView> getPlayers() {
        return players;
    }

    /**
     * Getter method
     *
     * @return the playerID
     */
    public int getOwnerPlayerID() {
        return ownerPlayerID;
    }
}
