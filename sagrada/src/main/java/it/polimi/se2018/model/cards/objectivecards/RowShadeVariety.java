package it.polimi.se2018.model.cards.objectivecards;


import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
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
        this.id = 12;
        this.text = "Rows with no repeated shades";
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
            temp = temp + differentShade(player.getGrid().getRow(i));

        return temp*multiplier;
    }
}
