package it.polimi.se2018.controller.network.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tester class for the ServerOutQueueEmptier class
 * @author Francesco Franzini
 */
public class ServerOutQueueEmptierTest {
    private ServerOutQueueEmptier uut;
    private String hit;

    /**
     * Prepares the object to be used
     */
    @Before
    public void setUp() {
        hit=null;
    }

    /**
     * Tests that requests are correctly handler
     */
    @Test
    public void testReq() {
        uut=new ServerOutQueueEmptier(new ClientTest(),true);
        uut.methodToCall();
        assertEquals("req",hit);
    }

    /**
     * Tests that objects are correctly handled
     */
    @Test
    public void testObj() {
        uut=new ServerOutQueueEmptier(new ClientTest(),false);
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
        public void processOutReq(){
            hit="req";
        }
        @Override
        public void processOutObj(){
            hit="obj";
        }
    }
}