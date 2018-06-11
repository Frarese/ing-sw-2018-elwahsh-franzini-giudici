package it.polimi.se2018.observable;

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
    private boolean placementRights;
    private boolean cardRights;



    public PlayerView(String playerName, int playerID) {
        this.playerName = playerName;
        this.playerID = playerID;
        this.playerFavours = 0;
        this.playerTemplate = new IntColorPair[Pattern.HEIGHT][Pattern.WIDTH];
        this.playerGrid = new IntColorPair[Pattern.HEIGHT][Pattern.WIDTH];
        this.placementRights = true;
        this.cardRights = true;
        this.uniqueNotify();
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

    public boolean isPlacementRights() {
        return placementRights;
    }

    public void setPlacementRights(boolean placementRights) {
        this.placementRights = placementRights;
    }

    public boolean isCardRights() {
        return cardRights;
    }

    public void setCardRights(boolean cardRights) {
        this.cardRights = cardRights;
    }


    public synchronized void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
