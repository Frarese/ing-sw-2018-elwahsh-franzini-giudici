package it.polimi.se2018.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CardIdentifier class
 *
 * @author Mathyas Giudici
 */

public class CardIdentifierTest {

    private CardIdentifier cardIdentifier;

    @Before
    public void testInit(){
        cardIdentifier = new CardIdentifier();
    }

    @Test
    public void getCardInfoTest(){
        assertEquals("Sfumature Gialle",cardIdentifier.getCardInfo(1));
        assertEquals("Colori Diversi - Riga (6 punti)",cardIdentifier.getCardInfo(10));
        assertEquals("Martelleto",cardIdentifier.getCardInfo(26));
        assertEquals(null,cardIdentifier.getCardInfo(32));
    }
}
