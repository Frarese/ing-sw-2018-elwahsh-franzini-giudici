package it.polimi.se2018.util;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ScoreEntryTest {
    @Test
    public void testScoreEntry() {
        String usn="A";
        int tot=1;
        int w=25;
        ScoreEntry sE=new ScoreEntry(usn,tot,w);
        assertTrue(sE.tot==1);
        assertTrue(sE.wins==25);
        assertTrue(sE.usn.equals(usn));
    }
}