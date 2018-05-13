package it.polimi.se2018.model.dice;

import it.polimi.se2018.model.ColorModel;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;


public class GridTest {
    private Grid grid;
    private Die d ;

    /**
     * Grid initialization for every test
     */
    @Before
    public void initTest()
    {
       grid = new Grid();

    }

    /**
     * Checking the public information about grid
     * like the height and the width and the initial number of
     * placed dice
     */
    @Test
    public void testPublicInfo()
    {
        assertEquals(Grid.HEIGHT,4);
        assertEquals(Grid.WIDTH,5);
        assertEquals(0,grid.getPlacedDice());
    }

    /**
     * Checking if setDie() and getDie() work properly
     */
    @Test
    public void testPlacement()
    {
        d = new Die(ColorModel.RED);
        Die d2= new Die(ColorModel.BLUE);

        /*Correct placement */
        assertEquals(null,grid.setDie(0,0,d));
        assertEquals(d,grid.getDie(0,0));
        assertEquals(1,grid.getPlacedDice());

        /*Placement on a spot with a die inside*/
        assertEquals(d,grid.setDie(0,0,d2));
        assertEquals(d2,grid.getDie(0,0));
        assertEquals(1,grid.getPlacedDice());

        /*Incorrect placement*/
        assertEquals(d,grid.setDie(Grid.HEIGHT,Grid.WIDTH,d));
        assertEquals(null, grid.getDie(Grid.HEIGHT,Grid.WIDTH));

    }

    /**
     * Checking if toString return a non Null String
     */
    @Test
    public void testToString()
    {
        d = new Die(ColorModel.YELLOW);
        /*To cover all toString I need to have an existing die inside the grid*/
        grid.setDie(0,0,d);
        assertNotEquals(null,grid.toString());
    }

    @Test
    public void testRowsAndColumns()
    {
        assertNotEquals(null,grid.getRow(-1));
        assertNotEquals(null, grid.getRow(0));
        assertNotEquals(null,grid.getColumn(-1));
        assertNotEquals(null,grid.getColumn(0));

        d = new Die(ColorModel.RED);
        grid.setDie(0,0, d);
        assertEquals(d,grid.getRow(0)[0]);
        assertEquals(d,grid.getColumn(0)[0]);
    }
}
