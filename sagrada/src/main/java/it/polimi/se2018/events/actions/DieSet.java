package it.polimi.se2018.events.actions;

/**
 * Request for setting a die outside from the normal rules
 * @author Alì El Wahsh
 */
public class DieSet extends  PlayerMove {

    private final int h;
    private final int w;

    /**
     * Constructor
     * @param player player's name
     * @param h height
     * @param w width
     */
    public DieSet(String player, int h, int w)
    {
        this.playerName = player;
        this.h = h;
        this.w = w;
        this.description = "DieSet";
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
