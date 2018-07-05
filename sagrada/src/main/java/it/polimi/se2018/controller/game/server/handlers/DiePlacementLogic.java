package it.polimi.se2018.controller.game.server.handlers;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Pattern;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Grid;

/**
 * Logic for generic die placement inside the grid
 * @author Alì El Wahsh
 */
public abstract class DiePlacementLogic {

    /**
     * Private constructor, to avoid illegal calls
     */
    private DiePlacementLogic()
    {}

    /**
     * Checker for adjacency restriction
     * @param g1 grid
     * @param h height inside the grid
     * @param w width inside the grid
     * @return true if there are adjacent dice, false otherwise
     */
    private static boolean isAdjacent(Grid g1, int h, int w) {

        for (int i = h - 1; i <= h + 1; i++)
            for (int j = w - 1; j <= w + 1; j++) {
                if ((i >= 0 && j >= 0 && i < Grid.HEIGHT && j < Grid.WIDTH) && ((i != h || j != w) && g1.getDie(i, j) != null) ) {
                        return true;
                }
            }
        return false;
    }

    /**
     * Checks if one of the adjacent dice has the same color of d
     * @param g1 grid
     * @param h height inside the grid
     * @param w width inside the grid
     * @param d the die to be placed
     * @return true if there are adjacent dice, false otherwise
     */
    private static boolean adjacentSameColor(Grid g1, int h, int w, Die d)
    {
        boolean temp = false;
        if(g1.getDie(h-1,w)!=null)
            temp = (g1.getDie(h-1,w).getColor() == d.getColor());
        if(g1.getDie(h+1,w)!= null)
            temp = temp ||(g1.getDie(h+1,w).getColor() == d.getColor());
        if(g1.getDie(h,w-1)!= null)
            temp = temp ||(g1.getDie(h,w-1).getColor() == d.getColor());
        if(g1.getDie(h,w+1)!= null)
            temp = temp ||(g1.getDie(h,w+1).getColor() == d.getColor());
        return temp;
    }


    /**
     * Checks if one of the adjacent dice has the same value of d
     * @param g1 grid
     * @param h height inside the grid
     * @param w width inside the grid
     * @param d the die to be placed
     * @return true if there are adjacent dice, false otherwise
     */
    private static boolean adjacentSameValue(Grid g1, int h, int w, Die d)
    {
        boolean temp = false;
        if(g1.getDie(h-1,w)!=null)
            temp = (g1.getDie(h-1,w).getValue() == d.getValue());
        if(g1.getDie(h+1,w)!= null)
            temp = temp ||(g1.getDie(h+1,w).getValue() == d.getValue());
        if(g1.getDie(h,w-1)!= null)
            temp = temp ||(g1.getDie(h,w-1).getValue() == d.getValue());
        if(g1.getDie(h,w+1)!= null)
            temp = temp ||(g1.getDie(h,w+1).getValue() == d.getValue());
        return temp;
    }

    /**
     * Checks if the desired spot is free
     * @param g1 grid
     * @param h height inside the grid
     * @param w width inside the grid
     * @return true if there are adjacent dice, false otherwise
     */
    private static boolean emptySpot(Grid g1, int h, int w)
    {
        return (g1.getDie(h,w) == null);
    }

    /**
     * Checks if the spot is on the border
     * @param h height inside the grid
     * @param w width inside the grid
     * @return true if there are adjacent dice, false otherwise
     */
    private static boolean isBorder (int h, int w)
    {
        return (h==0 || w == 0 || h == Grid.HEIGHT-1 || w == Grid.WIDTH -1);
    }

    /**
     * Checks if the color restriction is respected
     * @param p pattern
     * @param h height inside the grid
     * @param w width inside the grid
     * @param d the die to be placed
     * @return true if there are adjacent dice, false otherwise
     */
    private static boolean rightColor(Pattern p, int h, int w, Die d)
    {
        return p.getColor(h,w).equals(d.getColor()) || p.getColor(h,w) == ColorModel.WHITE;
    }

    /**
     * Checks if the value restriction is respected
     * @param p pattern
     * @param h height inside the grid
     * @param w width inside the grid
     * @param d the die to be placed
     * @return true if there are adjacent dice, false otherwise
     */
    private static boolean rightValue(Pattern p, int h, int w, Die d)
    {
        return p.getValue(h,w) == d.getValue() || p.getValue(h,w) == 0 ;
    }

    /**
     * Checks if it is the right spot for the placement without checking restrictions
     * @param player player who's trying to place the die
     * @param h height of the spot
     * @param w width of the spot
     * @param adjacentRestriction true if adjacency restriction is enabled
     * @return an error or null
     */
    private static String rightSpot(Player player, int h, int w, boolean adjacentRestriction)
    {
        if (player.getGrid().getPlacedDice() == 0) {
            if (!isBorder(h, w))
                return "Bisogna piazzare il primo dado sul bordo";
        } else {
            if (adjacentRestriction &&!isAdjacent(player.getGrid(), h, w))
                return "Bisogna piazzare il dado adiacente ad un altro";
            else
            if(!adjacentRestriction && isAdjacent(player.getGrid(),h,w))
                return "Bisogna piazzare il dado in modo che non sia adiacente ad un altro";
        }
        return null;
    }

    /**
     * Checks all the requirements for placing the die in the desired spot
     * @param player player who's trying to place the die
     * @param h height of the spot
     * @param w width of the spot
     * @param d the die to be placed
     * @param colorRestriction true if color restriction is enabled
     * @param valueRestriction true if value restriction is enabled
     * @param adjacentRestriction true if adjacency restriction is enabled
     * @return null if the requirements are respected, otherwise an error message
     */
    public static String insertDie(Player player, int h, int w, Die d, boolean colorRestriction, boolean valueRestriction, boolean adjacentRestriction) {


        String out = rightSpot(player,h,w,adjacentRestriction);
        if(out!= null)
            return out;

        if (!emptySpot(player.getGrid(), h, w))
            return "Posizione occupata";

        if (adjacentSameColor(player.getGrid(), h, w, d))
            return "I dadi adiacenti non devono avere lo stesso colore";


        if(adjacentSameValue(player.getGrid(),h,w,d))
            return "I dadi adiacenti non devono avere lo stesso valore";


        if (colorRestriction && !rightColor(player.getPattern(),h,w,d))
            return  "Restrizione di colore non ripettata";


        if (valueRestriction &&!rightValue(player.getPattern(),h,w,d))
            return  "Restrizione di valore non ripettata";

        return null;
    }

}
