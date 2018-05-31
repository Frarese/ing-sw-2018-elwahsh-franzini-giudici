package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Model representation of the EglomiseBrush tool card
 * @author Alì El Wahsh
 */
public class EglomiseBrush extends ToolCard {

    /**
     * EglomiseBrush's constructor
     */
    public EglomiseBrush()
    {
        this.id = 21;
        this.text = "Muovi un qualsiasi dado nella ua vetrata ignorando le restrzioni di colore\n" +
                "Devi rispettare tutte le altre restrizioni di piazzamento";
    }

    /**
     * Checks if the player can use this card on his turn
     * @param player the player using the card
     * @param firstTurn true on first turn, false on second
     * @return true if the player can use the card, else false
     */
    @Override
    public boolean isUsable(Player player, boolean firstTurn) {

        return player.canUseCardOnThisTurn(firstTurn) && (player.getGrid().getPlacedDice() >0);
    }
}
