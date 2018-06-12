package it.polimi.se2018.events.actions;

/**
 * Die selected from reserve
 * @author Alì El wahsh
 */
public class DieFromReserve extends PlayerMove {

    private final int index;

    /**
     * Constructor
     * @param username player's name
     * @param index die's position
     */
    public DieFromReserve(String username, int index)
    {
        this.index = index;
        this.playerName = username;
        this.description = "DieFromReserve";
    }

    /**
     * Getter for reserve index
     * @return die's position
     */
    public int getIndex() {
        return index;
    }
}
