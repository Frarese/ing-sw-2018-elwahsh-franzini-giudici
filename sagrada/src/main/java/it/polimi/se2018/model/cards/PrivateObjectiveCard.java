package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.dice.Grid;
import it.polimi.se2018.model.Player;

/**
 * Model's representation of a private objective card
 * @author Alì El Wahsh
 */
public class PrivateObjectiveCard extends ObjectiveCard {

    private ColorModel color;

    /**
     * PrivateObjectiveCard's constructor
     * @param color the color of the private objective
     */
    public PrivateObjectiveCard( ColorModel color)
    {
        this.id = color.ordinal();
        this.color = color;
    }

    /**
     * Calculates the private score of the player
     * @param player the player to be scored
     * @return sum of the dice having the color of the private objective
     */
    public int score(Player player)
    {
        int temp = 0;
        for(int i = 0; i< Grid.HEIGHT; i++)
        {
            for(int j = 0; j<Grid.WIDTH; j++)
            {
                if(player.getGrid().getDie(i,j)!= null && player.getGrid().getDie(i,j).getColor() == color)
                    temp = temp + player.getGrid().getDie(i,j).getValue();
            }
        }
        return temp;
    }
}
