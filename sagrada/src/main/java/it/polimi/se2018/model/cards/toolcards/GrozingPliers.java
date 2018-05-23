package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Model representation of the GrozingPliers tool card
 * @author Alì El Wahsh
 */
public class GrozingPliers extends ToolCard {

    /**
     * GrozingPliers's constructor
     */
    public GrozingPliers()
    {
        this.id = 20;
        this.text = "Dopo aver scelto un dado, aumenta o diminuisci il valore del dado di 1\n" +
                "Non puoi cambiare un 6 in un 1 o un 1 in un 6";
    }

    /**
     * Checks if the player can use this card on his turn
     * @param player the player using the card
     * @param firstTurn true on first turn, false on second
     * @return true if the player can use the card, else false
     */
    @Override
    public boolean isUsable(Player player, boolean firstTurn)
    {
            return player.canPlaceOnThisTurn(firstTurn) && player.canUseCardOnThisTurn(firstTurn);
    }
}
