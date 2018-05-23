package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Grid;

import java.util.stream.IntStream;

/**
 * Color Variety's objective card
 * @author Alì El Wahsh
 */
public class ColorVariety extends ObjectiveCard {

    /**
     * ColorVariety's constructor
     */
    public ColorVariety()
    {
        this.multiplier = 4;
        this.id = 19;
        this.text = "Sets of dice with every color on the grid";
    }

    /**
     * This methods finds the number of sets of dice with every color inside.
     * To do so it counts every color in the grid and it returns the least encountered
     * @param grid the player's grid
     * @return the number of found sets
     */
    private int findSets(Grid grid)
    {
        int[] colors = new int[5];
        for(int i = 0; i< Grid.HEIGHT; i++)
            for(int j = 0; j<Grid.WIDTH; j++)
            {
                if(grid.getDie(i,j) != null)
                    colors[grid.getDie(i,j).getColor().ordinal()]++;
            }
        return IntStream.of(colors).min().orElse(0);
    }

    /**
     * Score of ColorVariety
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {
        return findSets(player.getGrid())*multiplier;
    }
}
