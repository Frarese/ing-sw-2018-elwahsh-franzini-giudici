package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Model representation of the CorkBackedStraightedge tool card
 * @author Alì El Wahsh
 */
public class CorkBackedStraightedge extends ToolCard {

    /**
     * CorkBackedStraightedge's constructor
     */
    public CorkBackedStraightedge() {
        this.id = 28;
        this.text = "Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado" +
                "Devi rispettare tutte le restrizioni di piazzamento";
    }


    /**
     * Checks if the player can use this card on his turn
     * @param player the player using the card
     * @param firstTurn true on first turn, false on second
     * @return true if the player can use the card, else false
     */
    @Override
    protected boolean isUsable(Player player, boolean firstTurn) {

        return player.canUseCardOnThisTurn(firstTurn) && player.getGrid().getPlacedDice() >0;
    }
}
