package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;

/**
 * Deep Shade's objective card
 * @author Alì El Wahsh
 */
public class DeepShades extends ObjectiveCard {

    /**
     * DeepShades's constructor
     */
    public  DeepShades() {
        this.multiplier = 2;
        this.id = 16;
        this.text = "Set of 5&6 on the grid";
    }

    /**
     * Score of DeepShades
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {

        return shadeScore(player,5,6)*multiplier;
    }
}
