package it.polimi.se2018.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreEntryTest {
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