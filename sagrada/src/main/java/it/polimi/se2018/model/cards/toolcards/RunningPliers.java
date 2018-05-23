package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Model representation of the RunningPliers tool card
 * @author Alì El Wahsh
 */
public class RunningPliers extends ToolCard {

    /**
     * RunningPliers's constructor
     */
    public RunningPliers() {
        this.id = 27;
        this.text = "Dopo il tuo primo turno scegli immediatamente un altro dado" +
                "Salta il tuo secondo turno in questo round";
    }

    /**
     * Checks if the player can use this card on his turn
     * @param player the player using the card
     * @param firstTurn true on first turn, false on second
     * @return true if the player can use the card, else false
     */
    @Override
    protected boolean isUsable(Player player, boolean firstTurn) {
        return firstTurn && player.canPlaceOnThisTurn(true) && player.canUseCardOnThisTurn(true);
    }
}
