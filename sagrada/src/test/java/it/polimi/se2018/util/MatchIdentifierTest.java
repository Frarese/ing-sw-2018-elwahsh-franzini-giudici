package it.polimi.se2018.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MatchIdentifierTest {
    @Test
    public void testFour() {
        String a="AAAAA";
        String b="BBBBB";
        String c="CCCCC";
        String d="AAAAC";
        MatchIdentifier mId=new MatchIdentifier(b,a,c,d);
        assertEquals(a,mId.player0);
        assertEquals(d,mId.player1);
        assertEquals(b,mId.player2);
        assertEquals(c,mId.player3);

        assertEquals(0, mId.findPos(a));
        assertEquals(1, mId.findPos(d));
        assertEquals(2, mId.findPos(b));
        assertEquals(3, mId.findPos(c));
        assertEquals(-1, mId.findPos("X"));

        assertEquals(a+":"+d+":"+b+":"+c,mId.toString());
    }

    @Test
    public void testThree() {
        String a="AAAAA";
        String b="BBBBB";
        String c="CCCCC";
        MatchIdentifier mId=new MatchIdentifier(a,b,c,null);
        assertEquals(a,mId.player0);
        assertEquals(b,mId.player1);
        assertEquals(c,mId.player2);
        assertEquals("",mId.player3);
    }
    @Test
    public void testTwo() {
        String a="AAAAA";
        String b="BBBBB";
        MatchIdentifier mId=new MatchIdentifier(b,a,null,null);
        assertEquals(a,mId.player0);
        assertEquals(b,mId.player1);
        assertEquals("",mId.player2);
        assertEquals("",mId.player3);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testOne() {
        String a="AAAAA";
        MatchIdentifier mId=new MatchIdentifier(a,null,null,null);
    }
}