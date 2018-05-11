package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.dice.Grid;

/**
 * Color Diagonal's objective card
 */
public class ColorDiagonal extends ObjectiveCard {

    private boolean[][] visited;

    public ColorDiagonal()
    {
        this.multiplier = 1;
        this.id = 9;
        this.text = "Number of dice of the same color diagonally adjacent";
    }

    private  int recursiveDiagonalsFinder(Grid grid, int h, int w, int score)
    {


       if(grid.getDie(h,w) == null || visited[h][w] ){
           return 0;
       }
       else {
           visited[h][w] = true;

           if(grid.getDie(h+1,w+1) != null
                   &&
                   grid.getDie(h+1,w+1).getColor() == grid.getDie(h,w).getColor())
               score = recursiveDiagonalsFinder(grid, h+1, w+1,score +1);

           if(grid.getDie(h+1,w-1) != null
                   &&
                   grid.getDie(h+1,w-1).getColor() == grid.getDie(h,w).getColor())
               score = recursiveDiagonalsFinder(grid, h+1, w-1,score +1);

           if(score>0
                   &&
                   (grid.getDie(h-1,w-1) == null
                   ||
                           grid.getDie(h-1,w-1).getColor() != grid.getDie(h,w).getColor())
                   &&
                   (grid.getDie(h-1,w+1) == null
                   ||
                           grid.getDie(h-1,w+1).getColor() != grid.getDie(h,w).getColor()))
               score++;

       }
       return  score;

    }


    @Override
    public int score(Player player) {
        int temp = 0;
        visited = new boolean[Grid.HEIGHT][Grid.WIDTH];
        for(int h = 0; h<Grid.HEIGHT; h++ )
            for(int w = 0; w<Grid.WIDTH; w++)
                    temp += recursiveDiagonalsFinder(player.getGrid(), h , w,0);

        return temp*multiplier;
    }
}
