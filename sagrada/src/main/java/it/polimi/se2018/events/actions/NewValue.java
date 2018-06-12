package it.polimi.se2018.events.actions;

/**
 * New value for die to be evaluated
 * @author Alì El wahsh
 */
public class NewValue extends PlayerMove {

    private final int newVal;

    /**
     * Constructor
     * @param player player's name
     * @param newValue die's new value
     */
    public NewValue(String player ,int newValue) {
        this.playerName = player;
        this.newVal = newValue;
        this.description = "NewValue";
    }

    /**
     * Getter for the new value
     * @return new value
     */
    public int getNewValue() {
        return newVal;
    }
}
