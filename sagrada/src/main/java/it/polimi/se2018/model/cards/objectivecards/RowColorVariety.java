package it.polimi.se2018.model.cards.objectivecards;


import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Grid;


import java.util.stream.IntStream;

/**
 * Row Color Variety's objective card
 */
public class RowColorVariety extends ObjectiveCard {

    /**
     * RowColorVariety's constructor
     */
    public RowColorVariety() {
        this.multiplier = 6;
        this.id = 1;
        this.text = "Rows with no repeated colors";
    }

    /**
     * This method check if a row contains dice with different colors inside.
     * To do so it counts all the colors in the row and if there are no more than one
     * for each color it validates the row.
     * @param dice the row to be checked
     * @return 1 if it's a valid row, 0 otherwise
     */
    private int differentColorRow(Die[] dice)
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
     * Score of RowColorVariety
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {
        int temp = 0;
        for(int i = 0; i<Grid.HEIGHT; i++)
            temp = temp + differentColorRow(player.getGrid().getRow(i));

        return temp*multiplier;
    }
}
