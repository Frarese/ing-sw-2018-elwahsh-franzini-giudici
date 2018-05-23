package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.KeepAliveRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Queue;

import static org.junit.Assert.*;

public class RMIClientCommTest {
    private RMIClientComm uut;
    private Queue<AbsReq> outReqQueue;
    private Queue<Serializable> outObjQueue;
    private RMISessionImpl sesObj;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        sesObj=new RMISessionImpl(LoginResponsesEnum.USER_ALREADY_LOGGED);
        Client c = new Client("test", null);
        uut=new RMIClientComm(sesObj, c);
        Field f=RMIClientComm.class.getDeclaredField("rmiOutObjQueue");
        f.setAccessible(true);
        outObjQueue=(Queue)f.get(uut);
        f=RMIClientComm.class.getDeclaredField("rmiOutReqQueue");
        f.setAccessible(true);
        outReqQueue=(Queue)f.get(uut);
    }

    @Test
    public void testPushPop() {
        KeepAliveRequest kA=new KeepAliveRequest();

        uut.sendReq(kA);
        uut.sendObj("test");
        assertEquals(kA,outReqQueue.peek());
        assertEquals("test",outObjQueue.peek());

        assertTrue(uut.hasObj());
        assertTrue(uut.hasReq());

        assertEquals(kA,uut.getOutReq());
        assertEquals("test",uut.getOutObj());
    }

    @Test
    public void testTerminate(){
        KeepAliveRequest kA=new KeepAliveRequest();
        uut.sendReq(kA);
        uut.sendObj("test");

        uut.terminate();
        assertTrue(sesObj.isTerminated());

        assertFalse(uut.hasObj());
        assertFalse(uut.hasReq());

        assertNull(uut.getOutObj());
        assertNull(uut.getOutReq());
    }

    @Test
    public void testSessionObj(){
        KeepAliveRequest kA=new KeepAliveRequest();
        uut.sendReq(kA);
        uut.sendObj("test");

        assertTrue(sesObj.hasObj());
        assertTrue(sesObj.hasReq());

        assertEquals(kA,sesObj.getReq());
        assertEquals("test",sesObj.getObj());

        assertTrue(sesObj.sendObj("test"));
        assertTrue(sesObj.sendReq(kA));

        assertEquals(LoginResponsesEnum.USER_ALREADY_LOGGED,sesObj.getLoginOutput());
        assertEquals(sesObj,sesObj);
    }

}