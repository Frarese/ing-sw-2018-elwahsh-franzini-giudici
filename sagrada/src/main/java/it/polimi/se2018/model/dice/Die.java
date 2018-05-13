package it.polimi.se2018.model.dice;

import it.polimi.se2018.model.ColorModel;

import java.util.Random;

/**
 *  Model representation of a die with six faces(d6)
 *  @author Alì El Wahsh
 */
public class Die {

    private final ColorModel color;
    private int value;

    /**
     * The die will have a color and a temporary value when built,
     * in case it's rendered before the first roll
     * @param color it's the dice color assigned during game initialization
     */
    public Die(ColorModel color)
    {
        this.color = color;
        roll();
    }

    /**
     * This method simulates the roll of the dice assigning a value between 1 and 6 to it.
     * During a roll no thread can see the die, avoiding possible inconsistencies.
     * For now I'll use a simple random generation. May use a different and less polarized
     * algorithm in the future.
     */
     public synchronized void  roll()
    {
        Random random = new Random();
        value = random.nextInt(6) +1;
    }

    /**
     * Getter for the color variable
     * @return die's color
     */
     public ColorModel getColor() {
        return color;
    }

    /**
     * Getter for the value variable
     * @return die's upper face
     */
     public int getValue() {
        return value;
    }

    /**
     * Some rules give the player the ability to change a die's upper face, so
     * I added this method to let external entities change the value within the
     * admitted range
      * @param newValue new die's upper face
     *  @return true if the new value is valid, false otherwise
     */
     public boolean setFace(int newValue)
    {
        if(newValue > 0 && newValue < 7) {
            this.value = newValue;
            return true;
        }
        else
            return false;
    }

    /**
     * Simple toString method for CLI and log
     * @return A string formed by the color and the value of the die
     * @see ColorModel toString() method
     */
    @Override
    public String toString() {
        return color + " " + value;
    }
}
