package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

public class RMIClientListenerTest {
    private String sentObj;
    private RMICommLayer testComm;
    @Before
    public void setUp() {
        testComm=new TestCComm(new TestAbsReq());
        sentObj=null;
    }

    @After
    public void tearDown() {
        sentObj=null;
    }

    @Test
    public void testObjectHandle() {

        RMIClientListener rC=new RMIClientListener(testComm,false);
        rC.methodToCall();
        assertEquals("Obj",sentObj);
    }

    @Test
    public void testReqHandle() {

        RMIClientListener rC=new RMIClientListener(testComm,true);
        rC.methodToCall();
        assertEquals("Req",sentObj);
    }




    private class TestCComm extends RMICommLayer{
        AbsReq req;
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
    private class TestAbsReq extends AbsReq {
        String str="Req";
    }
}