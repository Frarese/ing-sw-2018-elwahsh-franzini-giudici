package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Grid;


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
     * Score of ColumnColorVariety
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {
        int temp = 0;
        for(int i = 0; i< Grid.WIDTH; i++)
            temp = temp + differentColor(player.getGrid().getColumn(i));

        return temp*multiplier;
    }
}
