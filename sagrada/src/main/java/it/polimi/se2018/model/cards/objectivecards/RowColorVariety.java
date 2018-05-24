package it.polimi.se2018.model.cards.objectivecards;


import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Grid;



/**
 * Row Color Variety's objective card
 * @author Alì El Wahsh
 */
public class RowColorVariety extends ObjectiveCard {

    /**
     * RowColorVariety's constructor
     */
    public RowColorVariety() {
        this.multiplier = 6;
        this.id = 10;
        this.text = "Rows with no repeated colors";
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
            temp = temp + differentColor(player.getGrid().getRow(i));

        return temp*multiplier;
    }
}
