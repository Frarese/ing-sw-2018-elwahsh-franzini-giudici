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
    private final String playerName;
    private final int playerFavours;
    private final IntColorPair[][] playerTemplate;
    private final IntColorPair[][] playerGrid;
    private final boolean placementRights;
    private final boolean cardRights;


    public PlayerState(String playerName, int favours, IntColorPair[][] playerTemplate, IntColorPair[][] playerGrid, boolean placementRights, boolean cardRights) {
        this.playerName = playerName;
        this.playerFavours = favours;
        this.playerTemplate = playerTemplate;
        this.playerGrid = playerGrid;
        this.placementRights = placementRights;
        this.cardRights = cardRights;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerFavours() {
        return playerFavours;
    }

    public IntColorPair[][] getPlayerTemplate() {
        return playerTemplate;
    }

    public IntColorPair[][] getPlayerGrid() {
        return playerGrid;
    }

    public boolean isPlacementRights() {
        return placementRights;
    }

    public boolean isCardRights() {
        return cardRights;
    }

}

