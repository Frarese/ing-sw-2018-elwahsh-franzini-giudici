package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Grid;

/**
 * Medium Shade's objective card
 * @author Alì El Wahsh
 */
public class MediumShades extends ObjectiveCard {

    /**
     * MediumShades's constructor
     */
    public MediumShades() {
        this.multiplier = 2;
        this.id = 15;
        this.text = "Set of 3&4 on the grid";
    }

    /**
     * Score of MediumShades
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {
        int temp1 = 0;
        int temp2 = 0;
        for(int i = 0; i< Grid.HEIGHT; i++)
            for(int j = 0; j<Grid.WIDTH; j++) {
                if (player.getGrid().getDie(i, j) != null) {
                    if (player.getGrid().getDie(i, j).getValue() == 3)
                        temp1++;
                    else if (player.getGrid().getDie(i, j).getValue() == 4)
                        temp2++;
                }
            }
        return Math.min(temp1,temp2)*multiplier;
    }
}
