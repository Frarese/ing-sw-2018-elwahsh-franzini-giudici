package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.cli_creators.CLIDieViewCreator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CLIDieViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLIDieViewCreatorTest {

    private CLIDieViewCreator cliDieViewCreator;

    @Before
    public void testInit() {
        cliDieViewCreator = new CLIDieViewCreator();
    }

    @Test
    public void testMakeDie() {
        assertEquals("1-RED", cliDieViewCreator.makeDie(new Pair<>(1, ColorModel.RED)));
        assertEquals("2-BLUE", cliDieViewCreator.makeDie(new Pair<>(2, ColorModel.BLUE)));
    }
}
