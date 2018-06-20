package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.server.ServerVisitor;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

/**
 * Tester class for the RMI client Listener
 * @author Francesco Franzini
 */
public class RMIClientListenerTest {
    private String sentObj;
    private RMICommLayer testComm;

    /**
     * Initializes the flags and Objects used
     */
    @Before
    public void testSetUp() {
        testComm=new TestCComm(new TestAbsReq());
        sentObj=null;
    }

    /**
     * Tests if an object is correctly extracted from the commLayer and then fed back in
     */
    @Test
    public void testObjectHandle() {
        RMIClientListener rC=new RMIClientListener(testComm,false);
        rC.methodToCall();
        assertEquals("Obj",sentObj);
    }

    /**
     * Tests if a request is correctly extracted from the commLayer and then fed back in
     */
    @Test
    public void testReqHandle() {
        RMIClientListener rC=new RMIClientListener(testComm,true);
        rC.methodToCall();
        assertEquals("Req",sentObj);
    }

    /**
     * Comm mock used to intercept calls
     */
    private class TestCComm extends RMICommLayer{
        final AbsReq req;
        TestCComm(AbsReq req){
            super(null);
            this.req=req;
        }
        @Override
        public Serializable getObj(){
            return "Obj";
        }
        @Override
        public AbsReq getReq(){
            return req;
        }
        @Override
        public void receiveInObj(Serializable obj){
            sentObj=(String)obj;
        }
        @Override
        public void receiveInReq(AbsReq req){
            sentObj=((TestAbsReq)req).str;
        }
    }

    /**
     * Mock request used as a payload
     */
    private class TestAbsReq implements AbsReq {
        final String str="Req";

        @Override
        public void serverVisit(ServerVisitor sV) {

        }

        @Override
        public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {

        }

        @Override
        public boolean checkValid() {
            return false;
        }
    }
}