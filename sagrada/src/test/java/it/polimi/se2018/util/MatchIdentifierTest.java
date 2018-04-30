package it.polimi.se2018.util;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MatchIdentifierTest {
    @Test
    public void testIdentifier() {
        String a="AAAAA";
        String b="BBBBB";
        String c="CCCCC";
        String d="AAAAC";
        MatchIdentifier mId=new MatchIdentifier(b,a,c,d);
        assertTrue(mId.player0.equals(a));
        assertTrue(mId.player1.equals(d));
        assertTrue(mId.player2.equals(b));
        assertTrue(mId.player3.equals(c));
    }
}