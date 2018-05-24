package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
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
     * Score of ColumnShadeVariety
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {
        int temp = 0;
        for(int i = 0; i< Grid.WIDTH; i++)
            temp = temp + differentShade(player.getGrid().getColumn(i));

        return temp*multiplier;
    }
}
