package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Grid;


import java.util.stream.IntStream;

/**
 * Column Color Variety's objective card
 * @author Alì El Wahsh
 */
public class ColumnColorVariety extends ObjectiveCard {

    /**
     * ColumnColorVariety's constructor
     */
    public ColumnColorVariety() {
        this.multiplier = 5;
        this.id = 11;
        this.text = "Columns with no repeated colors";
    }

    /**
     * This method check if a column contains dice with different colors inside.
     * To do so it counts all the colors in the column and if there are no more than one
     * for each color it validates the column.
     * @param dice the column to be checked
     * @return 1 if it's a valid column, 0 otherwise
     */
    private int differentColorColumn(Die[] dice)
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
     * Score of ColumnColorVariety
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {
        int temp = 0;
        for(int i = 0; i< Grid.WIDTH; i++)
            temp = temp + differentColorColumn(player.getGrid().getColumn(i));

        return temp*multiplier;
    }
}
