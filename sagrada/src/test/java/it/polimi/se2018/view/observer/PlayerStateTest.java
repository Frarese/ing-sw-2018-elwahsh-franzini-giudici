package it.polimi.se2018.view.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for PlayerState class
 *
 * @author Mathyas Giudici
 */

public class PlayerStateTest {

    private PlayerState playerState;
    private IntColorPair[][] template;
    private IntColorPair[][] grid;

    /**
     * Creates a new PlayerState objects
     */
    @Before
    public void testInit() {
        template = new IntColorPair[4][5];
        template[0][0] = new IntColorPair(6, ColorModel.RED);
        template[3][4] = new IntColorPair(5, ColorModel.GREEN);

        grid = new IntColorPair[4][5];
        grid[1][1] = new IntColorPair(1, ColorModel.BLUE);
        grid[2][2] = new IntColorPair(2, ColorModel.YELLOW);

        playerState = new PlayerState("Test", 10, template, grid, true, false);
    }

    /**
     * Tests getter method of player's name
     */
    @Test
    public void testGetPlayerName() {
        assertEquals("Test", playerState.getPlayerName());
    }

    /**
     * Tests getter method of player's favours points
     */
    @Test
    public void testGetPlayerFavours() {
        assertEquals(10, playerState.getPlayerFavours());
    }

    /**
     * Tests getter method of player's template
     */
    @Test
    public void testGetPlayerTemplate() {
        for (int i = 0; i < template.length; i++) {
            for (int j = 0; j < template[i].length; j++) {
                assertEquals(template[i][j], playerState.getPlayerTemplate()[i][j]);
            }
        }
    }

    /**
     * Tests getter method of player's grid
     */
    @Test
    public void testGetPlayerGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                assertEquals(grid[i][j], playerState.getPlayerGrid()[i][j]);
            }
        }
    }

    /**
     * Tests getter method of player's placement rights
     */
    @Test
    public void testIsPlacementRights() {
        assertTrue(playerState.isPlacementRights());
    }

    /**
     * Tests getter method of player's cards rights
     */
    @Test
    public void testIsCardRights() {
        assertFalse(playerState.isCardRights());
    }
}