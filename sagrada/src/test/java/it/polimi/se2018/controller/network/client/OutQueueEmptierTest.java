package it.polimi.se2018.controller.network.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class OutQueueEmptierTest {
    private String sentObj;
    private Comm testComm;
    @Before
    public void setUp() {
        testComm=new TestCComm();
        sentObj=null;
    }

    @After
    public void tearDown() {
        sentObj=null;
    }

    @Test
    public void testObjectHandle() throws Exception{

        OutQueueEmptier outQ=new OutQueueEmptier(testComm,false);
        outQ.methodToCall();
        assertEquals("Obj",sentObj);
    }

    @Test
    public void testReqHandle() throws Exception{

        OutQueueEmptier outQ=new OutQueueEmptier(testComm,true);
        outQ.methodToCall();
        assertEquals("Req",sentObj);
    }




    private class TestCComm extends Comm{
        @Override
        public void sendOutObj(){
            sentObj="Obj";
        }
        @Override
        public void sendOutReq(){
            sentObj="Req";
        }
    }
}