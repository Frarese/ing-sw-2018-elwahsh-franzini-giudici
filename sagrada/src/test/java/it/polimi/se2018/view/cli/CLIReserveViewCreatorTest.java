package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.cli_creators.CLIReserveViewCreator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Tests for CLIReserveViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLIReserveViewCreatorTest {

    private Pair<Integer, ColorModel>[] reserve;

    private CLIReserveViewCreator cliReserveViewCreator;

    @Before
    public void testInit() {
        this.reserve = new Pair[2];
        this.reserve[0] = new Pair<>(1, ColorModel.RED);
        this.reserve[1] = new Pair<>(2, ColorModel.RED);
        this.cliReserveViewCreator = new CLIReserveViewCreator(reserve);
    }

    @Test
    public void displayTest() {
        assertEquals("1-RED\n2-RED\n", this.cliReserveViewCreator.display());
    }

    @Test
    public void pickDieTest() {
        assertEquals("1-RED", this.cliReserveViewCreator.pickDie(0));
    }
}
