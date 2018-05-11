package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Grid;

/**
 * Deep Shade's objective card
 */
public class DeepShades extends ObjectiveCard {

    /**
     * DeepShades's constructor
     */
    public  DeepShades() {
        this.multiplier = 2;
        this.id = 7;
        this.text = "Set of 5&6 on the grid";
    }

    /**
     * Score of DeepShades
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
                    if (player.getGrid().getDie(i, j).getValue() == 5)
                        temp1++;
                    else if (player.getGrid().getDie(i, j).getValue() == 6)
                        temp2++;
                }
            }
        return Math.min(temp1,temp2)*multiplier;
    }
}
