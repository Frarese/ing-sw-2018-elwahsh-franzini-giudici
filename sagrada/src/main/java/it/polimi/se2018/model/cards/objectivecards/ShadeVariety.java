package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Grid;
import java.util.stream.IntStream;

/**
 * Shade Variety's objective card
 * @author Alì El Wahsh
 */
public class ShadeVariety extends ObjectiveCard {

    /**
     * ShadeVariety's constructor
     */
    public ShadeVariety()
    {
        this.multiplier = 5;
        this.id = 8;
        this.text = "Sets of dice with every value on the grid";
    }

    /**
     * This methods finds the number of sets of dice with every shade inside.
     * To do so it counts every shade in the grid and it returns the least encountered
     * @param grid the player's grid
     * @return the number of found sets
     */
    private int findSets(Grid grid)
    {
        int[] values = new int[6];
        for(int i = 0; i< Grid.HEIGHT; i++)
            for(int j = 0; j<Grid.WIDTH; j++)
            {
                if(grid.getDie(i,j) != null)
                    values[grid.getDie(i,j).getValue() -1]++;
            }
            return IntStream.of(values).min().orElse(0);
    }

    /**
     * Score of ShadeVariety
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {
       return findSets(player.getGrid())*multiplier;
    }
}
