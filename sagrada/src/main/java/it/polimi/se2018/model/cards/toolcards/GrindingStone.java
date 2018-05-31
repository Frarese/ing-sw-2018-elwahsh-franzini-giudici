package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Model representation of the GrindingStone tool card
 * @author Alì El Wahsh
 */
public class GrindingStone extends ToolCard {

    /**
     * GrindingStone's constructor
     */
    public GrindingStone() {
        this.id = 29;
        this.text = "Dopo aver scelto un dado giralo sulla faccia opposta" +
                "6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.";
    }

    /**
     * Checks if the player can use this card on his turn
     * @param player the player using the card
     * @param firstTurn true on first turn, false on second
     * @return true if the player can use the card, else false
     */
    @Override
    public boolean isUsable(Player player, boolean firstTurn) {
        return player.canUseCardOnThisTurn(firstTurn) && player.canPlaceOnThisTurn(firstTurn);
    }
}
