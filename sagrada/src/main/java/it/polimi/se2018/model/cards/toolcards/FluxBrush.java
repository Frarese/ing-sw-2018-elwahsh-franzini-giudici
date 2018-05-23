package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Model representation of the FluxBrush tool card
 * @author Alì El Wahsh
 */
public class FluxBrush extends ToolCard{

    /**
     * FluxBrush's constructor
     */
    public FluxBrush()
    {
        this.id = 25;
        this.text ="Dopo aver scelto un dado tira nuovamente quel dado\n" +
                "Se non puoi piazzarlo, riponilo nella Riserva";
    }

    /**
     * Checks if the player can use this card on his turn
     * @param player the player using the card
     * @param firstTurn true on first turn, false on second
     * @return true if the player can use the card, else false
     */
    @Override
    protected boolean isUsable(Player player, boolean firstTurn) {

        return player.canUseCardOnThisTurn(firstTurn) && player.canPlaceOnThisTurn(firstTurn);
    }
}
