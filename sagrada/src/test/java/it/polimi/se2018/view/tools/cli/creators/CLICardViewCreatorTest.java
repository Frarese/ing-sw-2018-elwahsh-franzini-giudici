package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.view.tools.CardViewCreator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for CLICardViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLICardViewCreatorTest {

    private CardViewCreator cliCardViewCreator;

    private int[] public_o, tool;

    @Before
    public void testInit() {
        public_o = new int[3];
        tool = new int[3];
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

    @Test
    public void getPrivateObjectiveCardtest() {
        assertEquals(3,cliCardViewCreator.getPrivateObjectiveCard());
    }

    @Test
    public void getPublicObjectiveCardsTest() {
        assertArrayEquals(public_o,cliCardViewCreator.getPublicObjectiveCards());
    }

    @Test
    public void getToolCardsTest() {
        assertArrayEquals(tool,cliCardViewCreator.getToolCards());
    }

}
