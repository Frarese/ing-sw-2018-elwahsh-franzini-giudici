package it.polimi.se2018.model.cards.objectivecards;


import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Grid;

/**
 * Row Shade Variety's objective card
 * @author Alì El Wahsh
 */
public class RowShadeVariety extends ObjectiveCard {

    /**
     * RowShadeVariety's constructor
     */
    public RowShadeVariety() {
        this.multiplier = 5;
        this.id = 3;
        this.text = "Rows with no repeated shades";
    }

    /**
     * This method check if a row contains dice with different shades inside.
     * To do so it counts all the shades in the row and if there are no more than one
     * for each shade it validates the row.
     * @param dice the row to be checked
     * @return 1 if it's a valid row, 0 otherwise
     */
    private int differentShadeRow(Die[] dice)
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
     * Score of RowShadeVariety
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {
        int temp = 0;
        for(int i = 0; i< Grid.HEIGHT; i++)
            temp = temp + differentShadeRow(player.getGrid().getRow(i));

        return temp*multiplier;
    }
}
