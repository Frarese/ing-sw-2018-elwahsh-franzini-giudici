package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Model representation of the LensCutter tool card
 * @author Alì El Wahsh
 */
public class LensCutter extends ToolCard {

    /**
     * LensCutter's constructor
     */
    public LensCutter()
    {
        this.id = 24;
        this.text = "Dopo aver scelto un dado scambia quel dado con un dado sul Traccaito dei Round";
    }

    /**
     * Checks if the player can use this card on his turn
     * @param player the player using the card
     * @param firstTurn true on first turn, false on second
     * @return true if the player can use the card, else false
     */
    @Override
    protected boolean isUsable(Player player, boolean firstTurn) {

        return player.canPlaceOnThisTurn(firstTurn) && player.canUseCardOnThisTurn(firstTurn);
    }
}
