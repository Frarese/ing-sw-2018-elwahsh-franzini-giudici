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


    /**
     * Constructor
     * @param playerName player's name
     * @param playerID player's ID
     */
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


    /**
     * Getter for player's name
     * @return player's name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Setter for player's name
     * @param playerName new Player's name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;

    }

    /**
     * Getter for Player's ID
     * @return player's ID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Setter for Player's ID
     * @param playerID new Player's ID
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;

    }

    /**
     * Getter for Player's favour points
     * @return player's favour points
     */
    public int getPlayerFavours() {
        return playerFavours;
    }

    /**
     * Setter for Player's favour points
     * @param playerFavours player's favour points
     */
    public void setPlayerFavours(int playerFavours) {
        this.playerFavours = playerFavours;

    }

    /**
     * Getter for player's pattern
     * @return player's pattern
     */
    public IntColorPair[][]  getPlayerTemplate() {
        return playerTemplate;
    }

    /**
     * Setter for player's pattern
     * @param playerTemplate player's pattern
     */
    public void setPlayerTemplate(IntColorPair[][] playerTemplate) {
        this.playerTemplate = playerTemplate;

    }

    /**
     * Getter for player's grid
     * @return player's grid
     */
    public IntColorPair[][] getPlayerGrid() {
        return playerGrid;
    }

    /**
     * Setter for player's grid
     * @param playerGrid player's grid
     */
    public void setPlayerGrid(IntColorPair[][] playerGrid) {
        this.playerGrid = playerGrid;

    }

    /**
     * Getter for placement's rights
     * @return true if the player can place a die
     */
    public boolean isPlacementRights() {
        return placementRights;
    }

    /**
     * Setter for placement's rights
     * @param placementRights placement rights
     */
    public void setPlacementRights(boolean placementRights) {
        this.placementRights = placementRights;
    }

    /**
     * Getter for card's rights
     * @return true if a player can use a card
     */
    public boolean isCardRights() {
        return cardRights;
    }

    /**
     * Setter for card's rights
     * @param cardRights card's rights
     */
    public void setCardRights(boolean cardRights) {
        this.cardRights = cardRights;
    }

    /**
     * Notifies all observers
     */
    public synchronized void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
