package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


/**
 * Tests for CLIReserveViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLIReserveViewCreatorTest {

    private final String regexControl = "\\p{Cntrl}";
    private final String emptyString = "";


    private IntColorPair[] reserve;

    private CLIReserveViewCreator cliReserveViewCreator;

    @Before
    public void testInit() {
        this.reserve = new IntColorPair[2];
        this.reserve[0] = new IntColorPair(1, ColorModel.RED);
        this.reserve[1] = new IntColorPair(2, ColorModel.RED);
        this.cliReserveViewCreator = new CLIReserveViewCreator(reserve);
    }

    @Test
    public void testDisplay() {
        assertEquals("0) 1-RED" + "1) 2-RED", this.cliReserveViewCreator.display().replaceAll(regexControl,emptyString));
    }

    @Test
    public void testPickDie() {
        assertEquals("1-RED", this.cliReserveViewCreator.pickDie(0));
    }

    @Test
    public void testGetReserve() {
        assertArrayEquals(reserve, cliReserveViewCreator.getReserve());
    }

    @Test
    public void testSetReserve() {
        IntColorPair[] reserveTest = new IntColorPair[2];
        reserveTest[0] = new IntColorPair(1, ColorModel.RED);
        this.cliReserveViewCreator = new CLIReserveViewCreator(null);
        this.cliReserveViewCreator.setReserve(reserveTest);
        assertArrayEquals(reserveTest, cliReserveViewCreator.getReserve());
    }
}
