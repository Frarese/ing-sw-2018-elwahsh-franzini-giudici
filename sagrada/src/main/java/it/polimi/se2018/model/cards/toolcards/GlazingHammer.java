package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Model representation of the GlazingHammer tool card
 * @author Alì El Wahsh
 */
public class GlazingHammer extends ToolCard {

    /**
     * GlazingHammer's constructor
     */
    public GlazingHammer() {
        this.id = 26;
        this.text ="Tira nuovamente tutti i dadi della Riserva" +
                "Questa carta può essere usata solo durante il tuo secondo turno, prima di scegiere il secondo dado";
    }

    /**
     * Checks if the player can use this card on his turn
     * @param player the player using the card
     * @param firstTurn true on first turn, false on second
     * @return true if the player can use the card, else false
     */
    @Override
    public boolean isUsable(Player player, boolean firstTurn) {

        return !firstTurn && player.canUseCardOnThisTurn(false) && player.canPlaceOnThisTurn(false);
    }
}
