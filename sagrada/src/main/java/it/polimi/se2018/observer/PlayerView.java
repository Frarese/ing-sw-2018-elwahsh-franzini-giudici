package it.polimi.se2018.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * This class represents an observer of a player
 *
 * @author Mathyas Giudici
 */

public class PlayerView {

    /**
     * Class attributes
     */
    private boolean hasChanged;

    private String playerName;
    private int playerID;
    private int playerFavours;
    private Pair<Integer, ColorModel>[][] playerTemplate;
    private Pair<Integer, ColorModel>[][] playerGrid;
    private boolean placementRights;
    private boolean cardRights;

    public PlayerView(boolean hasChanged, String playerName, int playerID, int favours, Pair<Integer, ColorModel>[][] playerTemplate, Pair<Integer, ColorModel>[][] playerGrid, boolean placementRights, boolean cardRights) {
        this.hasChanged = hasChanged;
        this.playerName = playerName;
        this.playerID = playerID;
        this.playerFavours = favours;
        this.playerTemplate = playerTemplate;
        this.playerGrid = playerGrid;
        this.placementRights = placementRights;
        this.cardRights = cardRights;
    }


    public boolean isHasChanged() {
        return hasChanged;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerFavours() {
        return playerFavours;
    }

    public Pair<Integer, ColorModel>[][] getPlayerTemplate() {
        return playerTemplate;
    }

    public Pair<Integer, ColorModel>[][] getPlayerGrid() {
        return playerGrid;
    }

    public boolean hasPlacementRights() {
        return placementRights;
    }

    public boolean hasCardRights() {
        return cardRights;
    }
}
