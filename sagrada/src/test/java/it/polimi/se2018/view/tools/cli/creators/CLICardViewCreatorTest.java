package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.view.tools.cli.creators.CLICardViewCreator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for CLICardViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLICardViewCreatorTest {

    private CLICardViewCreator cliCardViewCreator;

    @Before
    public void testInit() {
        int[] public_o = new int[3];
        int[] tool = new int[3];
        public_o[0] = 10;
        tool[0] = 26;
        cliCardViewCreator = new CLICardViewCreator(3, public_o, tool);
    }

    @Test
    public void getCardInfoTest() {
        assertEquals("Sfumature Gialle", cliCardViewCreator.makeCart(3));
        assertEquals("Colori Diversi - Riga (6 punti)", cliCardViewCreator.makeCart(10));
        assertEquals("Martelleto", cliCardViewCreator.makeCart(26));
        assertNull(cliCardViewCreator.makeCart(32));
    }
}
