package it.polimi.se2018.controller.network.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tester class for the ServerInQueueEmptier class
 * @author Francesco Franzini
 */
public class ServerInQueueEmptierTest {
    private ServerInQueueEmptier uut;
    private String hit;

    /**
     * Prepares the object to be used
     */
    @Before
    public void setUp() {
        hit=null;
    }

    /**
     * Tests that requests are correctly handled
     */
    @Test
    public void testReq() {
        uut=new ServerInQueueEmptier(new ClientTest(),true);
        uut.methodToCall();
        assertEquals("req",hit);
    }

    /**
     * Tests that objects are correctly handled
     */
    @Test
    public void testObj() {
        uut=new ServerInQueueEmptier(new ClientTest(),false);
        uut.methodToCall();
        assertEquals("obj",hit);
    }

    /**
     * Mock client used to intercept method calls
     */
    private class ClientTest extends Client{
        ClientTest(){
            super("",null);
        }

        @Override
        public void handleReq(){
            hit="req";
        }
        @Override
        public void handleObj(){
            hit="obj";
        }
    }

}