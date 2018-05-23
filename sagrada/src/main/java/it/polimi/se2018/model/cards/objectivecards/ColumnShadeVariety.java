package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Grid;

/**
 * Column Shade Variety's objective card
 * @author Alì El Wahsh
 */
public class ColumnShadeVariety extends ObjectiveCard {

    /**
     * ColumnShadeVariety's constructor
     */
    public ColumnShadeVariety() {
        this.multiplier = 4;
        this.id = 13;
        this.text = "Columns with no repeated shades";
    }

    /**
     * This method check if a column contains dice with different shades inside.
     * To do so it counts all the shades in the column and if there are no more than one
     * for each shade it validates the column.
     * @param dice the column to be checked
     * @return 1 if it's a valid column, 0 otherwise
     */
    private int differentShadeColumn(Die[] dice)
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
     * Score of ColumnShadeVariety
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {
        int temp = 0;
        for(int i = 0; i< Grid.WIDTH; i++)
            temp = temp + differentShadeColumn(player.getGrid().getColumn(i));

        return temp*multiplier;
    }
}
