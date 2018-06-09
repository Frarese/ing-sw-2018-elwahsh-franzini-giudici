package it.polimi.se2018.view.observer;

import it.polimi.se2018.model.IntColorPair;

/**
 * This class represents current state of a player
 *
 * @author Mathyas Giudici
 */

public class PlayerState {

    /**
     * Class attributes
     */
    private String playerName;
    private int playerID;
    private int playerFavours;
    private IntColorPair[][] playerTemplate;
    private IntColorPair[][] playerGrid;
    private boolean placementRights;
    private boolean cardRights;


    public PlayerState(String playerName, int playerID, int favours, IntColorPair[][] playerTemplate, IntColorPair[][] playerGrid, boolean placementRights, boolean cardRights) {
        this.playerName = playerName;
        this.playerID = playerID;
        this.playerFavours = favours;
        this.playerTemplate = playerTemplate;
        this.playerGrid = playerGrid;
        this.placementRights = placementRights;
        this.cardRights = cardRights;
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

    public IntColorPair[][] getPlayerTemplate() {
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
}

