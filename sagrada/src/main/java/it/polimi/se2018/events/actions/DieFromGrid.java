package it.polimi.se2018.events.actions;

/**
 * Die selected from the Grid
 * @author Alì El wahsh
 */
public class DieFromGrid extends PlayerMove {

    private final int h;
    private final int w;

    /**
     * Constructor
     * @param player player's name
     * @param h die's height inside the grid
     * @param w die's width inside the grid
     */
    public DieFromGrid(String player, int h, int w)
    {
        this.playerName = player;
        this.h = h;
        this.w = w;
        this.description = "DieFromGrid";
    }

    /**
     * Getter for height
     * @return height
     */
    public int getH() {
        return h;
    }

    /**
     * Getter for width
     * @return width
     */
    public int getW() {
        return w;
    }
}
