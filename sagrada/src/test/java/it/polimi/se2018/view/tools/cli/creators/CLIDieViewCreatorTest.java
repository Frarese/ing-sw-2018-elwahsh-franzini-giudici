package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
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

    /**
     * Creates a new CLIDieViewCreator
     */
    @Before
    public void testInit() {
        cliDieViewCreator = new CLIDieViewCreator();
    }

    /**
     * Checks correct CLI' die object creation
     */
    @Test
    public void testMakeDie() {
        assertEquals("1-RED", cliDieViewCreator.makeDie(new IntColorPair(1, ColorModel.RED)));
        assertEquals("2-BLUE", cliDieViewCreator.makeDie(new IntColorPair(2, ColorModel.BLUE)));
    }
}
