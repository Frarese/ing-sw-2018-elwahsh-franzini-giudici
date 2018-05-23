package it.polimi.se2018.controller.network.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServerInQueueEmptierTest {
    private ServerInQueueEmptier uut;
    private String hit;
    @Before
    public void setUp() {
        hit=null;
    }

    @Test
    public void testReq() {
        uut=new ServerInQueueEmptier(new ClientTest(),true);
        uut.methodToCall();
        assertEquals("req",hit);
    }

    @Test
    public void testObj() {
        uut=new ServerInQueueEmptier(new ClientTest(),false);
        uut.methodToCall();
        assertEquals("obj",hit);
    }

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