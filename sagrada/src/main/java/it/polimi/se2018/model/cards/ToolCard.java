package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Player;


/**
 * Abstract class representing a generic Tool Card behaviour
 * @author Alì El Wahsh
 */
public abstract class ToolCard extends CardModel {
    protected boolean used; /*Unused card cost 1 favour points, while used ones 2*/

    /**
     * When a card is used the used flag must be set on true
     */
    protected void updateUsed()
    {
        used = true;
    }

    /**
     * Tells if the card is used or not
     * @return the used flag
     */
    public boolean isUsed()
    {
        return used;
    }
    /**
     * To use a Tool card a player must spend its favour points
     * @param player the player to use
     */
    protected void burnFavourPoints(Player player)
    {
        if(used)
            player.setFavourPoints(player.getFavourPoints() -2);
        else
            player.setFavourPoints(player.getFavourPoints() -1);
    }

    /**
     * Some cards have few conditions for their effects, so it must be checked
     * if the player can use them or not
     * @return true if the card is usable by the player, false otherwise
     */
    protected abstract boolean isUsable(Player player);


}
