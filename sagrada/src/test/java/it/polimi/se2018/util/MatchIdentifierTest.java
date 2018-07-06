package it.polimi.se2018.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tester class for the MatchIdentifier
 * @author Francesco Franzini
 */
public class MatchIdentifierTest {

    /**
     * Tests behaviour with four names
     */
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

    /**
     * Tests behaviour with 3 names
     */
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

    /**
     * Tests behaviour with two names
     */
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

    /**
     * Tests that initialization fails with invalid arguments
     */
    @Test(expected = IllegalArgumentException.class)
    public void testOne() {
        String a="AAAAA";
        new MatchIdentifier(a,null,null,null);
    }

    /**
     * Tests the {@code equals} method
     */
    @Test
    public void testEquals(){
        String a="AAAAA";
        String b="BBBBB";
        MatchIdentifier mId1=new MatchIdentifier(b,a,null,null);
        MatchIdentifier mId2=new MatchIdentifier(a,b,null,null);
        MatchIdentifier mId3=new MatchIdentifier(a,b,"CCCCC",null);
        assertEquals(mId1,mId1);
        assertEquals(mId1,mId2);
        assertEquals(mId1.hashCode(),mId2.hashCode());
        assertNotEquals(mId1,mId3);
    }
}