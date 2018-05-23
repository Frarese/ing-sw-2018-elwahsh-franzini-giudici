package it.polimi.se2018.controller.network.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServerOutQueueEmptierTest {
    private ServerOutQueueEmptier uut;
    private String hit;
    @Before
    public void setUp() {
        hit=null;
    }

    @Test
    public void testReq() {
        uut=new ServerOutQueueEmptier(new ClientTest(),true);
        uut.methodToCall();
        assertEquals("req",hit);
    }

    @Test
    public void testObj() {
        uut=new ServerOutQueueEmptier(new ClientTest(),false);
        uut.methodToCall();
        assertEquals("obj",hit);
    }

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