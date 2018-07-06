package it.polimi.se2018.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tester class for ScoreEntry
 * @author Francesco Franzini
 */
public class ScoreEntryTest {

    /**
     * Tests that the class is correctly initialized
     */
    @Test
    public void testScoreEntry() {
        String usn="A";
        int tot=1;
        int w=25;
        ScoreEntry sE=new ScoreEntry(usn,tot,w);
        assertEquals(1, sE.tot);
        assertEquals(25, sE.wins);
        assertEquals(sE.usn, usn);
    }
}