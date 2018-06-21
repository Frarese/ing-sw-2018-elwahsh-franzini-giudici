package it.polimi.se2018.view.app;

import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.*;
import it.polimi.se2018.view.message.MessageBox;
import it.polimi.se2018.view.observer.*;
import it.polimi.se2018.view.tools.*;

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
    boolean animationEnable;
    String ownerPlayerName;

    /**
     * Game variables
     */
    final List<PlayerState> players;

    private final List<MatchIdentifier> invites;

    private final List<ScoreEntry> connectedUsers;

    private final List<ScoreEntry> leaderBoard;

    /**
     * Communication variables
     */
    final ViewActions viewActions;

    final ViewToolCardActions viewToolCardActions;

    final MessageBox messageBox;

    final List<PlayerViewObserver> playerViewObserverList;

    CardViewObserver cardViewObserver;

    ReserveViewObserver reserveViewObserver;

    RoundTrackerViewObserver roundTrackerViewObserver;


    /**
     * Creators components
     */
    CardViewCreator cardViewCreator;

    GridViewCreator gridViewCreator;

    RoundTrackerViewCreator roundTrackerViewCreator;

    ReserveViewCreator reserveViewCreator;

    ScoreViewCreator scoreViewCreator;

    /**
     * Class constructor that initializes variables to defaults values
     *
     * @param viewActions         contains ViewActions class for View->Controller communication
     * @param viewToolCardActions contains ViewToolCardActions class for View->Controller communication (tool cards)
     */
    protected App(ViewActions viewActions, ViewToolCardActions viewToolCardActions, ViewMessage viewMessage) {
        this.animationEnable = true;
        this.players = new ArrayList<>();

        this.invites = new ArrayList<>();
        this.connectedUsers = new ArrayList<>();
        this.leaderBoard = new ArrayList<>();

        this.viewActions = viewActions;
        this.viewToolCardActions = viewToolCardActions;
        this.messageBox = new MessageBox(viewMessage);
        this.playerViewObserverList = new ArrayList<>();
    }

    /**
     * Searches in the players' list a player identified by his name ad return his PlayerView object
     *
     * @param players contains the players' list
     * @param wanted  contains the String name of the wanted player
     * @return the PlayerView of the wanted player
     */
    public PlayerState searchPlayerViewByName(List<PlayerState> players, String wanted) {
        if (players != null) {
            for (PlayerState player : players)
                if (player.getPlayerName().equals(wanted)) return player;
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
     * Getter method for ViewToolCardActions
     *
     * @return ViewToolCardActions class reference
     */
    public ViewToolCardActions getViewToolCardActions() {
        return viewToolCardActions;
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
    public List<PlayerState> getPlayers() {
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
     * Getter method for connectedPlayer's list
     *
     * @return the connected players' list
     */
    public List<ScoreEntry> getConnectedUsers() {
        return connectedUsers;
    }

    /**
     * Getter method for leaderBoard's list
     *
     * @return the leader board
     */
    public List<ScoreEntry> getLeaderBoard() {
        return leaderBoard;
    }

    @Override
    public void pullConnectedPlayers(List<ScoreEntry> players) {
        //Refresh connected players list
        this.connectedUsers.clear();
        this.connectedUsers.addAll(players);
    }

    @Override
    public void pullLeaderBoard(List<ScoreEntry> leaderBoard) {
        //Refresh leaderBoard list
        this.leaderBoard.clear();
        this.leaderBoard.addAll(leaderBoard);
    }

    @Override
    public void pullInvitate(MatchIdentifier invite) {
        //Add invite add list
        this.invites.add(invite);
    }

    /**
     * Creates a new PlayerState from a PlayerView
     *
     * @param playerView contains the PlayerView
     * @return return the correlative PlayerState
     */
    PlayerState createState(PlayerView playerView) {
        return new PlayerState(playerView.getPlayerName(), playerView.getPlayerFavours(), playerView.getPlayerTemplate(),
                playerView.getPlayerGrid(), playerView.isPlacementRights(), playerView.isCardRights());
    }

    /**
     * Getter method for current player's name
     *
     * @return the player's name
     */
    public String getOwnerPlayerName() {
        return ownerPlayerName;
    }

    void clean(){
        this.animationEnable = true;
        this.players.clear();
        this.invites.clear();
        this.connectedUsers.clear();
        this.leaderBoard.clear();

        this.playerViewObserverList.clear();
    }
}
