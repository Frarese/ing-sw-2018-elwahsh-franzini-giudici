package it.polimi.se2018.model.dice;

/**
 * Model representation of the game grid where the players
 * insert their dice
 */
public class Grid {

    public static final int HEIGHT = 4; /*height of the grid*/
    public static final int  WIDTH = 5; /*width of the grid*/

    private Die[][] diceGrid = new Die[HEIGHT][WIDTH];
    private int placedDice; /* number of dice placed in the grid */

    /**
     * Constructor of Grid
     * sets the numbers of dice to 0
     */
    public Grid ()
    {
        this.placedDice = 0;
    }

    /**
     * Getter of a particular die in the grid
     * @param h height of the die inside the grid
     * @param w width of the die inside the grid
     * @return the die in the given location or null in case of no die present
     * @exception IndexOutOfBoundsException return null as if the spot was empty
     */
    public Die getDie(int h, int w)
    {
        try {
            return diceGrid[h][w];
        } catch(IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     * Getter for a specific row of the grid.
     * @param h height of the row
     * @return the desired row
     * @exception  IndexOutOfBoundsException return an empty array in case of invalid height
     */
    public Die[] getRow(int h)
    {
        try {
            return diceGrid[h];
        } catch(IndexOutOfBoundsException e)
        {
            return new Die[Grid.WIDTH];
        }
    }

    /**
     * Getter for a specific column of the grid
     * @param w width of the column
     * @return the desired column
     * @exception IndexOutOfBoundsException return an empty array in case of invalid width
     */
    public Die[] getColumn(int w)
    {
        try {
            Die[] column = new Die[Grid.HEIGHT];
            for (int i = 0; i < Grid.HEIGHT; i++) {
                column[i] = diceGrid[i][w];
            }
            return column;
        } catch (IndexOutOfBoundsException e)
        {
            return new Die[Grid.HEIGHT];
        }
    }

    /**
     * Getter of the number of placed dice
     * @return the number of placed dice
     */
    public int getPlacedDice()
    {
        return placedDice;
    }

    /**
     * Setter of a die inside the grid
     * @param h height inside the grid
     * @param w width inside the grid
     * @param d the die to be placed inside the grid (it can be null)
     * @return the previous die in the spot or null if the spot was empty
     * @exception  IndexOutOfBoundsException it returns the die
     */
    public Die setDie(int h, int w, Die d)
    {
        try {
            if (diceGrid[h][w] == null) {
                diceGrid[h][w] = d;
                placedDice++;
                return null;
            } else {
                Die temp = diceGrid[h][w];
                diceGrid[h][w] = d;
                return temp;
            }
        }catch (IndexOutOfBoundsException e)
        {
            return d;
        }
    }

    /**
     * Basic toString method for CLI or log purposes
     * @return String version of the grid structured as a matrix
     */
    @Override
    public String toString()
    {
        StringBuilder strb = new StringBuilder();

        for(int i=0; i<HEIGHT;i++) {
            for (int j = 0; j < WIDTH; j++) {
                if(diceGrid[i][j]==null)
                    strb.append(" / ");
                else
                    strb.append(diceGrid[i][j]);
            }
            strb.append("\n");
        }
        return strb.toString();
    }
}
