package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Queue;

import static org.junit.Assert.*;

public class MatchMakingQueueTest {
    MatchMakingQueue m;
    Queue<Client> q;
    @Before
    public void setUp() throws Exception{
        m=new MatchMakingQueue(null);
        Field f=m.getClass().getDeclaredField("q");
        f.setAccessible(true);
        q=(Queue<Client>)f.get(m);
    }

    @Test
    public void testAdd() {
        Client c=new Client("us1",null);
        m.add(c);
        assertEquals(c,q.peek());
    }

    @Test
    public void testRemove() {
        Client c=new Client("us1",null);
        m.add(c);
        assertEquals(c,q.peek());
        m.remove(c);
        assertTrue(q.isEmpty());

    }

    @Test
    public void testDetach() {
        Client c1=new Client("us1",null);
        m.add(c1);
        Client c2=new Client("us2",null);
        m.add(c2);
        Client c3=new Client("us3",null);
        m.add(c3);
        Client c4=new Client("us4",null);
        try{
            m.add(c4);
            fail();
        }catch (NullPointerException e){
            assertTrue(q.isEmpty());
        }
    }

}