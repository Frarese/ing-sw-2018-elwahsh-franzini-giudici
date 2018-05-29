package it.polimi.se2018.view.app;

import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.*;
import it.polimi.se2018.view.message.MessageBox;
import it.polimi.se2018.view.tools.*;

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
     * Game variables
     */
    protected List<PlayerView> players;

    protected List<MatchIdentifier> invites;

    protected List<ScoreEntry> leaderBoard;

    /**
     * Communication variables
     */
    protected ViewActions viewActions;

    protected ViewToolCardActions viewToolCardActions;

    protected MessageBox messageBox;

    protected ModelObserver modelObserver;

    /**
     * Creators components
     */
    protected CardViewCreator cardViewCreator;

    protected GridViewCreator gridViewCreator;

    protected RoundTrackerViewCreator roundTrackerViewCreator;

    protected ReserveViewCreator reserveViewCreator;

    protected ScoreViewCreator scoreViewCreator;

    /**
     * Class constructor that initializes variables to defaults values
     *
     * @param viewActions         contains ViewActions class for View->Controller communication
     * @param viewToolCardActions contains ViewToolCardActions class for View->Controller communication (tool cards)
     */
    public App(ViewActions viewActions, ViewToolCardActions viewToolCardActions, ViewMessage viewMessage) {
        this.animationEnable = true;
        this.players = null;
        this.viewActions = viewActions;
        this.viewToolCardActions = viewToolCardActions;
        this.messageBox = new MessageBox(viewMessage);
        this.modelObserver = new ModelObserver();
    }

    /**
     * Searches in the players' list a player identified by his name ad return his PlayerView object
     *
     * @param players contains the players' list
     * @param wanted  contains the String name of the wanted player
     * @return the PlayerView of the wanted player
     */
    public PlayerView searchPlayerViewByName(List<PlayerView> players, String wanted) {
        for (PlayerView player : players) {
            if (player.getPlayerName().equals(wanted)) {
                return player;
            }
        }
        Logger.getGlobal().log(Level.WARNING, "Ci sono problemi in PlayerView");
        return null;
    }

    /**
     * Searches in the players' list a player identified by his ID ad return his PlayerView object
     *
     * @param players contains the players' list
     * @param wanted  contains the ID of the wanted player
     * @return the PlayerView of the wanted player
     */
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
     * Getter method for ViewActions
     *
     * @return ViewActions class reference
     */
    public ViewActions getViewActions() {
        return viewActions;
    }

    /**
     * Getter method for CardViewCreator
     *
     * @return the current CardViewCreator
     */
    public CardViewCreator getCardViewCreator() {
        return cardViewCreator;
    }

    /**
     * Getter method for GridViewCreator
     *
     * @return the current GridViewCreator
     */
    public GridViewCreator getGridViewCreator() {
        return gridViewCreator;
    }

    /**
     * Getter method for RoundTrackerViewCreator
     *
     * @return the current RoundTrackerViewCreator
     */
    public RoundTrackerViewCreator getRoundTrackerViewCreator() {
        return roundTrackerViewCreator;
    }

    /**
     * Getter method for ReserveViewCreator
     *
     * @return the current ReserveViewCreator
     */
    public ReserveViewCreator getReserveViewCreator() {
        return reserveViewCreator;
    }

    /**
     * Getter method for players' list
     *
     * @return the current players
     */
    public List<PlayerView> getPlayers() {
        return players;
    }

    /**
     * Getter method for invites' list
     *
     * @return the user's matches invites
     */
    public List<MatchIdentifier> getInvites() {
        return invites;
    }

    /**
     * Getter method for leaderBoard's list
     *
     * @return the leader board
     */
    public List<ScoreEntry> getLeaderBoard() {
        return leaderBoard;
    }
}
