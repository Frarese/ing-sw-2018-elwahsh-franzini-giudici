package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Grid;

import java.util.stream.IntStream;

/**
 * Abstract class representing a generic Objective Card behaviour
 * @author Alì El Wahsh
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
     * Calculates number of a specific couple of shades
     * @param player player to be scored
     * @param value1 first shade
     * @param value2 second shade
     * @return minimum between value1 and value2
     */
    protected static int shadeScore(Player player, int value1, int value2)
    {
        int temp1 = 0;
        int temp2 = 0;
        for(int i = 0; i< Grid.HEIGHT; i++)
            for(int j = 0; j<Grid.WIDTH; j++) {
                if (player.getGrid().getDie(i, j) != null) {
                    if (player.getGrid().getDie(i, j).getValue() == value1)
                        temp1++;
                    else if (player.getGrid().getDie(i, j).getValue() == value2)
                        temp2++;
                }
            }
        return Math.min(temp1,temp2);
    }

    /**
     * Checks if an array of dice is made of different colors
     * @param dice array of dice
     * @return 1 if all the color are different, 0 otherwise
     */
    protected static int differentColor(Die[] dice)
    {
        int[] colors = new int[5];
        for(Die d: dice) {
            if( d== null)
                return 0;
            else
            {
                colors[d.getColor().ordinal()]++;
            }
        }
        if( IntStream.of(colors).max().orElse(2) <= 1)
            return 1;
        else
            return 0;
    }

    /**
     * Checks if an array of dce is made of different shades
     * @param dice array of dice
     * @return 1 if dice is made of different shades, 0 otherwise
     */
    protected static int differentShade(Die[] dice)
    {
        int[] values = new int[6];
        for(Die d: dice) {
            if( d== null)
                return 0;
            else
                values[d.getValue()-1]++;
        }
        for(int i: values)
        {
            if(i>1)
                return 0;
        }
        return 1;
    }

    /**
     * Each Objective card must have this method
     * It calculates the score from the player's Grid
     * @param player the player to score
     * @return the score reached according to the card's objective
     */
    public abstract int score(Player player);
}
