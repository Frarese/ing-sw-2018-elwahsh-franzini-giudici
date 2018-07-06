package it.polimi.se2018.controller.network.server;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Tester class for the MatchMakingQueue
 * @author Francesco Franzini
 */
public class MatchMakingQueueTest {
    private MatchMakingQueue uut;
    private Queue q;

    /**
     * Prepares the object to be tested
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception{
        uut =new MatchMakingQueue(null);
        Field f= uut.getClass().getDeclaredField("q");
        f.setAccessible(true);
        q=(Queue)f.get(uut);

    }

    /**
     * Tests the {@code add} method
     */
    @Test
    public void testAdd() {
        Client c=new Client("us1",null);
        uut.add(c);
        assertEquals(c,q.peek());
    }

    /**
     * Tests the {@code remove} method
     */
    @Test
    public void testRemove() {
        Client c=new Client("us1",null);
        uut.add(c);
        assertEquals(c,q.peek());
        uut.remove(c);
        assertTrue(q.isEmpty());

    }

    /**
     * Tests that clients are correctly put in a match
     */
    @Test
    public void testDetach() {
        Client c1=new Client("us1",null);
        uut.add(c1);
        Client c2=new Client("us2",null);
        uut.add(c2);
        Client c3=new Client("us3",null);
        uut.add(c3);
        Client c4=new Client("us4",null);
        try{
            uut.add(c4);
            fail();
        }catch (NullPointerException e){
            assertTrue(q.isEmpty());
        }
    }

}