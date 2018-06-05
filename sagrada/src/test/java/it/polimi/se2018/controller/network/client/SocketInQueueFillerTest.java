package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.server.ServerVisitor;
import it.polimi.se2018.util.SafeSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;

import static org.junit.Assert.*;

public class SocketInQueueFillerTest {
    private String sentObj;
    private SocketCommLayer testComm;
    private SafeSocket ssDummy;
    private boolean req;
    @Before
    public void setUp() throws Exception{
        testComm=new TestCComm();
        sentObj=null;
        ssDummy=new TestSSocket(new TestAbsReq());
        req=false;
    }

    @After
    public void tearDown() {
        sentObj=null;
    }

    @Test
    public void testObjectHandle() throws Exception{

        SocketInQueueFiller inQ=new SocketInQueueFiller(testComm,ssDummy,false);
        inQ.methodToCall();
        assertEquals("Obj",sentObj);
    }

    @Test
    public void testReqHandle() throws Exception{
        req=true;
        SocketInQueueFiller inQ=new SocketInQueueFiller(testComm,ssDummy,true);
        inQ.methodToCall();
        assertEquals("Req",sentObj);
    }

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