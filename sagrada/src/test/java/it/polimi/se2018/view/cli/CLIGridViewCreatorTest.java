package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.cli_creators.CLIGridViewCreator;
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

    private ArrayList<String> result;

    private Pair<Integer,ColorModel>[][] grid, pattern;

    private CLIGridViewCreator cliGridViewCreator;

    @Test
    public void displayTest(){
        result = new ArrayList<String>();
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        grid = null;
        pattern = new Pair[1][2];
        pattern[0][0] = new Pair<>(1,ColorModel.RED);
        pattern[0][1] = new Pair<>(1,ColorModel.RED);
        this.cliGridViewCreator = new CLIGridViewCreator(grid,pattern);

        assertEquals(result,cliGridViewCreator.display());

        //TODO
        //test with grid not null
    }

    @Test
    public void addDieTest(){
        this.cliGridViewCreator = new CLIGridViewCreator(null,null);
        cliGridViewCreator.addADie("Test Add",0,0);
    }

    @Test
    public void pickDieTest(){
        grid = new Pair[1][1];
        grid[0][0] = new Pair<>(1,ColorModel.RED);
        this.cliGridViewCreator = new CLIGridViewCreator(grid,null);
        assertEquals("1-RED",cliGridViewCreator.pickDie(0,0));
    }
}
