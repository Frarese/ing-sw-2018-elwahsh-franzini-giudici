package it.polimi.se2018.controller.network.client;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;


/**
 * Tester class for the client outbound process
 * @author Francesco Franzini
 */
public class OutQueueEmptierTest {
    private String sentObj;
    private Comm testComm;

    /**
     * Initializes the flag and Comm to be used
     */
    @Before
    public void testSetUp() {
        testComm=new TestCComm();
        sentObj=null;
    }

    /**
     * Tests if the object is correctly forwarded
     * @throws Exception if an error occurs
     */
    @Test
    public void testObjectHandle() throws Exception{
        OutQueueEmptier outQ=new OutQueueEmptier(testComm,false);
        outQ.methodToCall();
        assertEquals("Obj",sentObj);
    }

    /**
     * Tests if the request is correctly forwarded
     * @throws Exception if an error occurs
     */
    @Test
    public void testReqHandle() throws Exception{
        OutQueueEmptier outQ=new OutQueueEmptier(testComm,true);
        outQ.methodToCall();
        assertEquals("Req",sentObj);
    }

    /**
     * Comm mock used to intercept calls to Comm
     */
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