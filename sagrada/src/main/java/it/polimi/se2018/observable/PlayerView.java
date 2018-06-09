package it.polimi.se2018.observable;

import it.polimi.se2018.events.messages.PlayerStatus;
import it.polimi.se2018.model.Pattern;
import it.polimi.se2018.model.IntColorPair;

import java.util.Observable;


/**
 * This class represents an observable of a player
 *
 * @author Mathyas Giudici
 */

public class PlayerView extends Observable {

    /**
     * Class attributes
     */
    private String playerName;
    private int playerID;
    private int playerFavours;
    private IntColorPair[][]  playerTemplate;
    private IntColorPair[][]  playerGrid;
    private boolean firstPlacementRights;
    private boolean firstCardRights;
    private boolean secondPlacementRights;
    private boolean secondCardRights;


    public PlayerView(String playerName, int playerID) {
        this.playerName = playerName;
        this.playerID = playerID;
        this.playerFavours = 0;
        this.playerTemplate = new IntColorPair[Pattern.HEIGHT][Pattern.WIDTH];
        this.playerGrid = new IntColorPair[Pattern.HEIGHT][Pattern.WIDTH];
        this.firstPlacementRights = true;
        this.firstCardRights = true;
        this.secondPlacementRights = true;
        this.secondCardRights = true;
        this.uniqueNotify();
    }


    public PlayerView(PlayerStatus playerStatus)
    {
        this.playerName = playerStatus.getName();
        this.playerID = playerStatus.getId();
        this.playerFavours = playerStatus.getFavourPoints();
        this.playerGrid = playerStatus.getGrid();
        this.firstCardRights = playerStatus.isFirstTurnCard();
        this.firstPlacementRights = playerStatus.isFirstTurnPlacement();
        this.secondCardRights = playerStatus.isSecondTurnCard();
        this.secondPlacementRights = playerStatus.isSecondTurnPlacement();

    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;

    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;

    }

    public int getPlayerFavours() {
        return playerFavours;
    }

    public void setPlayerFavours(int playerFavours) {
        this.playerFavours = playerFavours;

    }

    public IntColorPair[][]  getPlayerTemplate() {
        return playerTemplate;
    }

    public void setPlayerTemplate(IntColorPair[][] playerTemplate) {
        this.playerTemplate = playerTemplate;

    }

    public IntColorPair[][] getPlayerGrid() {
        return playerGrid;
    }

    public void setPlayerGrid(IntColorPair[][] playerGrid) {
        this.playerGrid = playerGrid;

    }

    public boolean isFirstPlacementRights() {
        return firstPlacementRights;
    }

    public void setFirstPlacementRights(boolean firstPlacementRights) {
        this.firstPlacementRights = firstPlacementRights;
    }

    public boolean isFirstCardRights() {
        return firstCardRights;
    }

    public void setFirstCardRights(boolean firstCardRights) {
        this.firstCardRights = firstCardRights;
    }

    public boolean isSecondCardRights() {
        return secondCardRights;
    }

    public boolean isSecondPlacementRights() {
        return secondPlacementRights;
    }

    public void setSecondCardRights(boolean secondCardRights) {
        this.secondCardRights = secondCardRights;
    }

    public void setSecondPlacementRights(boolean secondPlacementRights) {
        this.secondPlacementRights = secondPlacementRights;
    }

    public synchronized void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
