package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Model representation of the Lathekin tool card
 * @author Alì El Wahsh
 */
public class Lathekin extends ToolCard {

    /**
     * Lathekin's constructor
     */
    public Lathekin()
    {
        this. id = 23;
        this.text = "Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamnto";
    }

    /**
     * Checks if the player can use this card on his turn
     * @param player the player using the card
     * @param firstTurn true on first turn, false on second
     * @return true if the player can use the card, else false
     */
    @Override
    public boolean isUsable(Player player, boolean firstTurn) {

        return player.canUseCardOnThisTurn(firstTurn) && (player.getGrid().getPlacedDice() >=2);
    }
}
