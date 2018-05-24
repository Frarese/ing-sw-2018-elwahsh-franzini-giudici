package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
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
        this.id = 14;
        this.text = "Set of 1&2 on the grid";
  }

    /**
     * Score of LightShades
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {

            return shadeScore(player,1,2)*multiplier;
    }
}
