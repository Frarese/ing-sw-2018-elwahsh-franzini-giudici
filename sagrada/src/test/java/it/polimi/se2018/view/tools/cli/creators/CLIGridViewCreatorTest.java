package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for CLIGridViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLIGridViewCreatorTest {

    private final String regexControl = "\\p{Cntrl}";
    private final String emptyString = "";

    private ArrayList<String> result;

    private IntColorPair[][] grid, pattern;

    private CLIGridViewCreator cliGridViewCreator;

    @Test
    public void testSimpleDisplay() {
        result = new ArrayList<>();
        result.add("Pattern");
        result.add("--------------------");
        result.add("|1-RED   ||        |");
        result.add("--------------------");
        grid = null;
        pattern = new IntColorPair[1][2];
        pattern[0][0] = new IntColorPair(1, ColorModel.RED);
        pattern[0][1] = new IntColorPair(0, ColorModel.WHITE);
        this.cliGridViewCreator = new CLIGridViewCreator(grid, pattern);

        assertEquals(result.toString().replaceAll(regexControl, emptyString), cliGridViewCreator.display().toString().replaceAll(regexControl, emptyString));
    }

    @Test
    public void testEmptyGridDisplay() {
        result = new ArrayList<>();
        result.add("Pattern");
        result.add("--------------------");
        result.add("|1-RED   ||1-VIOLET|");
        result.add("--------------------");
        result.add("Griglia");
        result.add("--------------------");
        result.add("|        ||        |");
        result.add("--------------------");
        grid = new IntColorPair[1][2];
        pattern = new IntColorPair[1][2];
        pattern[0][0] = new IntColorPair(1, ColorModel.RED);
        pattern[0][1] = new IntColorPair(1, ColorModel.VIOLET);
        this.cliGridViewCreator = new CLIGridViewCreator(grid, pattern);

        assertEquals(result.toString().replaceAll(regexControl, emptyString), cliGridViewCreator.display().toString().replaceAll(regexControl, emptyString));
    }

    @Test
    public void testFullGridDisplay() {
        result = new ArrayList<>();
        result.add("Pattern");
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        result.add("Griglia");
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        grid = new IntColorPair[1][2];
        pattern = new IntColorPair[1][2];
        pattern[0][0] = new IntColorPair(1, ColorModel.RED);
        pattern[0][1] = new IntColorPair(1, ColorModel.RED);
        grid[0][0] = new IntColorPair(1, ColorModel.RED);
        grid[0][1] = new IntColorPair(1, ColorModel.RED);
        this.cliGridViewCreator = new CLIGridViewCreator(grid, pattern);

        assertEquals(result.toString().replaceAll(regexControl, emptyString), cliGridViewCreator.display().toString().replaceAll(regexControl, emptyString));
    }

    @Test
    public void testGetGridPattern() {
        grid = new IntColorPair[1][2];
        grid[0][0] = new IntColorPair(1, ColorModel.RED);
        grid[0][1] = new IntColorPair(1, ColorModel.RED);
        this.cliGridViewCreator = new CLIGridViewCreator(null, grid);
        assertArrayEquals(grid, cliGridViewCreator.getGridPattern());
    }

    @Test
    public void testGetGrid() {
        grid = new IntColorPair[1][2];
        grid[0][0] = new IntColorPair(1, ColorModel.RED);
        grid[0][1] = new IntColorPair(1, ColorModel.RED);
        this.cliGridViewCreator = new CLIGridViewCreator(grid, null);
        assertArrayEquals(grid, cliGridViewCreator.getGrid());
    }

    @Test
    public void testSetGrid() {
        grid = new IntColorPair[1][1];
        this.cliGridViewCreator = new CLIGridViewCreator(null, null);
        this.cliGridViewCreator.setGrid(grid);
        assertArrayEquals(grid, cliGridViewCreator.getGrid());
    }
}
