package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;

import org.junit.Test;



/**
 * Tests for FXGridViewCreator class
 *
 * @author Mathyas Giudici
 */

public class FXGridViewCreatorTest {


    @Test
    public void testDisplay(){
        IntColorPair[][] pattern = new IntColorPair[2][3];
        pattern[0][0] = new IntColorPair(1, ColorModel.WHITE);
        pattern[0][1] = new IntColorPair(0, ColorModel.RED);
        pattern[0][2] = new IntColorPair(0, ColorModel.RED);

        IntColorPair[][] grid = new IntColorPair[2][3];
        grid[1][0] = new IntColorPair(1, ColorModel.BLUE);
        grid[1][1] = new IntColorPair(6, ColorModel.VIOLET);

        new FXGridViewCreator(grid,pattern,"BLACK");


    }
}