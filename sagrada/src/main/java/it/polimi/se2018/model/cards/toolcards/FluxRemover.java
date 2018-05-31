package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Model representation of the FluxRemover tool card
 * @author Alì El Wahsh
 */
public class FluxRemover extends ToolCard {

    /**
     * FluxRemover's constructor
     */
    public FluxRemover() {
        this.id = 30;
        this.text = "Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto" +
                "Scegli il valore del nuovo dado e piazzzalo, rispettando tutte le restrizioni di piazzamento";
    }

    /**
     * Checks if the player can use this card on his turn
     * @param player the player using the card
     * @param firstTurn true on first turn, false on second
     * @return true if the player can use the card, else false
     */
    @Override
    public boolean isUsable(Player player, boolean firstTurn) {
        return player.canPlaceOnThisTurn(firstTurn) && player.canUseCardOnThisTurn(firstTurn);
    }
}
