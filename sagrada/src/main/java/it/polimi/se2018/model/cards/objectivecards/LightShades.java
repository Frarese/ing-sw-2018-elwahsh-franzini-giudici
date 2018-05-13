package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Grid;
/**
 * Light Shade's objective card
 * @author Alì El Wahsh
 */
public class LightShades extends ObjectiveCard {

    /**
     * LightShades's constructor
     */
    public LightShades() {
        this.multiplier = 2;
        this.id = 5;
        this.text = "Set of 1&2 on the grid";
  }

    /**
     * Score of LightShades
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
                    if (player.getGrid().getDie(i, j).getValue() == 1)
                        temp1++;
                    else if (player.getGrid().getDie(i, j).getValue() == 2)
                        temp2++;
                }
            }
            return Math.min(temp1,temp2)*multiplier;
    }
}
