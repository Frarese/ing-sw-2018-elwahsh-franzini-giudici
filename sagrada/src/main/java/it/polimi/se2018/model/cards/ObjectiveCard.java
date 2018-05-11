package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Player;

/**
 * Abstract class representing a generic Objective Card behaviour
 */
public abstract class ObjectiveCard extends CardModel {
    protected int multiplier; /*Card score multiplier*/

    /**
     * Getter for points multiplier
     * @return the multiplier
     */
    public int getMultiplier() {
        return multiplier;
    }

    /**
     * Each Objective card must have this method
     * It calculates the score from the player's Grid
     * @return the score reached according to the card's objective
     */
    public abstract int score(Player player);
}