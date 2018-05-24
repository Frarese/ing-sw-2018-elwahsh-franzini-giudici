package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;

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

        return shadeScore(player,3,4)*multiplier;
    }
}
