package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Grid;

/**
 * Color Diagonal's objective card
 * @author Alì El Wahsh
 */
public class ColorDiagonal extends ObjectiveCard {

    private boolean[][] visited;

    /**
     * ColorDiagonal's constructor
     */
    public ColorDiagonal()
    {
        this.multiplier = 1;
        this.id = 9;
        this.text = "Number of dice of the same color diagonally adjacent";
    }


    /**
     * Finds a suitable spot in the grid to be traversed diagonally
     * to find desired adjacencies
     * @param grid the grid to check
     * @param h height in the grid
     * @param w width in the grid
     * @param score score
     * @return total score for that spot
     */
    private int findFirstSpot(Grid grid, int h, int w, int score)
    {
        if(grid.getDie(h,w) == null || visited[h][w] ){
            return score;
        }
        else {
            visited[h][w] = true;
            if (grid.getDie(h + 1, w + 1) != null
                    &&
                    grid.getDie(h + 1, w + 1).getColor() == grid.getDie(h, w).getColor())
                score = recursiveDiagonalsFinder(grid, h + 1, w + 1, score);

            if (grid.getDie(h + 1, w - 1) != null
                    &&
                    grid.getDie(h + 1, w - 1).getColor() == grid.getDie(h, w).getColor())
                score = recursiveDiagonalsFinder(grid, h + 1, w - 1, score);

            if (score >0)
                score++;        }
        return score;
    }


    /**
     * Recursively traverses the grid to find all the branches of
     * diagonally adjacencies
     * @param grid the grid to check
     * @param h height in the grid
     * @param w width in the grid
     * @param score score
     * @return score for the branch
     */
    private int recursiveDiagonalsFinder(Grid grid, int h, int w, int score)
    {


       if(grid.getDie(h,w) == null || visited[h][w] ){
           return score;
       }
       else {
           visited[h][w] = true;
            score++;

           if(grid.getDie(h+1,w+1) != null
                   &&
                   grid.getDie(h+1,w+1).getColor() == grid.getDie(h,w).getColor())
               score = recursiveDiagonalsFinder(grid, h+1, w+1,score);

           if(grid.getDie(h+1,w-1) != null
                   &&
                   grid.getDie(h+1,w-1).getColor() == grid.getDie(h,w).getColor())
               score = recursiveDiagonalsFinder(grid, h+1, w-1,score);

           if(grid.getDie(h-1,w+1) != null
                   &&
                   grid.getDie(h-1,w+1).getColor() == grid.getDie(h,w).getColor())
               score = recursiveDiagonalsFinder(grid, h-1, w+1,score);

           if(grid.getDie(h-1,w-1) != null
                   &&
                   grid.getDie(h-1,w-1).getColor() == grid.getDie(h,w).getColor())
               score = recursiveDiagonalsFinder(grid, h-1, w-1,score);
       }
       return  score;

    }

    /**
     * Score of ColorDiagonal
     * @param player the to be scored
     * @return the score of the player for this card
     */
    @Override
    public int score(Player player) {
        int temp = 0;
        visited = new boolean[Grid.HEIGHT][Grid.WIDTH];
        for(int h = 0; h<Grid.HEIGHT; h++ )
            for(int w = 0; w<Grid.WIDTH; w++)
                    temp += findFirstSpot(player.getGrid(), h , w,0);

        return temp*multiplier;
    }
}
