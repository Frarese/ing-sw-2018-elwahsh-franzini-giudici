package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.server.ServerVisitor;
import it.polimi.se2018.util.SafeSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;

import static org.junit.Assert.*;

/**
 * Tester class for the client inbound listener
 * @author Francesco Franzini
 */
public class SocketInQueueFillerTest {
    private String sentObj;
    private SocketCommLayer testComm;
    private SafeSocket ssDummy;
    private boolean req;

    /**
     * Initializes the flags and objects used
     * @throws Exception if an error occurs
     */
    @Before
    public void testSetUp() throws Exception{
        testComm=new TestCComm();
        sentObj=null;
        ssDummy=new TestSSocket(new TestAbsReq());
        req=false;
    }

    /**
     * Tests if the filler correctly handles an inbound object
     * @throws Exception if an error occurs
     */
    @Test
    public void testObjectHandle() throws Exception{
        SocketInQueueFiller inQ=new SocketInQueueFiller(testComm,ssDummy,false);
        inQ.methodToCall();
        assertEquals("Obj",sentObj);
    }

    /**
     * Tests if the filler correctly handles an inbound request
     * @throws Exception if an error occurs
     */
    @Test
    public void testReqHandle() throws Exception{
        req=true;
        SocketInQueueFiller inQ=new SocketInQueueFiller(testComm,ssDummy,true);
        inQ.methodToCall();
        assertEquals("Req",sentObj);
    }

    /**
     * Mock socket layer used to intercept calls
     */
    private class TestCComm extends SocketCommLayer{

        TestCComm() {
            super(null);
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
     * Mock SafeSocket used to intercept calls
     */
    private class TestSSocket extends SafeSocket{
        AbsReq requ;
        TestSSocket(AbsReq requ) throws IOException {
            super(100);
            this.requ=requ;
        }

        @Override
        public Serializable receive(){
            if(req)return this.requ;
            return "Obj";
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