package it.polimi.se2018.controller.game.server.handlers;

import it.polimi.se2018.model.dice.Die;

/**
 * Class used to store random results to avoid cheats from players
 * @author Alì El wahsh
 */
public class RandomDice  {

    private Die rollDie;
    private int rollDieIndex;
    private int bagDie;

    /**
     * Constructor
     */
    public RandomDice()
    {
        rollDie = null;
        rollDieIndex = -2;
        bagDie = -1;
    }

    /**
     * Getter die's position inside the bag
     * @return die's position inside the bag
     */
    public int getBagDie() {
        return bagDie;
    }

    /**
     * Getter for a rolled die
     * @return rolled die
     */
    public Die getRollDie() {
        return rollDie;
    }

    /**
     * Getter for the index of the rolled die
     * @return index of the rolled die
     */
    public int getRollDieIndex() {
        return rollDieIndex;
    }

    /**
     * Sets rollDieIndex
     * @param rollDieIndex rollDieIndex's new value
     */
    public void setRollDieIndex(int rollDieIndex) {
        this.rollDieIndex = rollDieIndex;
    }

    /**
     * Sets bagDie
     * @param bagDie bagDie's new value
     */
    public void setBagDie(int bagDie) {
        this.bagDie = bagDie;
    }

    /**
     * Sets rollDie
     * @param rollDie rollDie's new value
     */
    public void setRollDie(Die rollDie) {
        this.rollDie = rollDie;
    }

}
