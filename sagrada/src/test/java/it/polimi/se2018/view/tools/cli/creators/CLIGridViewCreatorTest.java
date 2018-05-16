package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.cli.ui.CLIPrinter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CLIGridViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLIGridViewCreatorTest {

    private CLIPrinter printer;
    private ArrayList<String> result;

    private Pair<Integer, ColorModel>[][] grid, pattern;

    private CLIGridViewCreator cliGridViewCreator;

    @Before
    public void testInit() {
        this.printer = new CLIPrinter();
    }

    @Test
    public void simpleDisplayTest() {
        result = new ArrayList<String>();
        result.add("Pattern\n");
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        grid = null;
        pattern = new Pair[1][2];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        pattern[0][1] = new Pair<>(1, ColorModel.RED);
        this.cliGridViewCreator = new CLIGridViewCreator(grid, pattern, printer);

        assertEquals(result, cliGridViewCreator.display());
    }

    @Test
    public void emptyGridDisplayTest() {
        result = new ArrayList<String>();
        result.add("Pattern\n");
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        result.add("\n Griglia\n");
        result.add("--------------------");
        result.add("|        ||        |");
        result.add("--------------------");
        grid = new Pair[1][2];
        pattern = new Pair[1][2];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        pattern[0][1] = new Pair<>(1, ColorModel.RED);
        this.cliGridViewCreator = new CLIGridViewCreator(grid, pattern, printer);

        assertEquals(result, cliGridViewCreator.display());
    }

    @Test
    public void fullGridDisplayTest() {
        result = new ArrayList<String>();
        result.add("Pattern\n");
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        result.add("\n Griglia\n");
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        grid = new Pair[1][2];
        pattern = new Pair[1][2];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        pattern[0][1] = new Pair<>(1, ColorModel.RED);
        grid[0][0] = new Pair<>(1, ColorModel.RED);
        grid[0][1] = new Pair<>(1, ColorModel.RED);
        this.cliGridViewCreator = new CLIGridViewCreator(grid, pattern,printer);

        assertEquals(result, cliGridViewCreator.display());
    }

    @Test
    public void addDieTest() {
        this.cliGridViewCreator = new CLIGridViewCreator(null, null,printer);
        cliGridViewCreator.addADie("Test Add", 0, 0);
    }

    @Test
    public void pickDieTest() {
        grid = new Pair[1][1];
        grid[0][0] = new Pair<>(1, ColorModel.RED);
        this.cliGridViewCreator = new CLIGridViewCreator(grid, null,printer);
        assertEquals("1-RED", cliGridViewCreator.pickDie(0, 0));
    }
}
