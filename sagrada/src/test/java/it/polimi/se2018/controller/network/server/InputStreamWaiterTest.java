package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.util.SafeSocket;
import org.junit.After;
import org.junit.Test;


import java.io.IOException;
import java.io.Serializable;

import static org.junit.Assert.*;

public class InputStreamWaiterTest {
    private InputStreamWaiter uut;
    private String recObj;
    private boolean req;

    @After
    public void tearDown(){
        recObj=null;
    }

    @Test
    public void testObj() throws Exception{
        req=false;
        uut=new InputStreamWaiter(new TestSSocket(new TestAbsReq()),false,new TestCComm());
        uut.methodToCall();
        assertEquals("Obj",recObj);
    }

    @Test
    public void testReq() throws Exception{
        req=true;
        uut=new InputStreamWaiter(new TestSSocket(new TestAbsReq()),true,new TestCComm());
        uut.methodToCall();
        assertEquals("Req",recObj);
    }

    private class TestCComm extends SocClientComm{
        TestCComm(){
            super(null,null,null);
        }
        @Override
        public void pushInObj(Serializable obj){
            recObj=(String)obj;
        }
        @Override
        public void pushInReq(AbsReq obj){
            recObj=((TestAbsReq)obj).str;
        }
    }

    private class TestSSocket extends SafeSocket {
        AbsReq requ;
        public TestSSocket(AbsReq requ) throws IOException {
            super(100);
            this.requ=requ;
        }

        @Override
        public Serializable receive(){
            if(req)return this.requ;
            return "Obj";
        }

    }

    private class TestAbsReq extends AbsReq {
        String str="Req";
    }
}