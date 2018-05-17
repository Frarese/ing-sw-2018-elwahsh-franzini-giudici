package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.KeepAliveRequest;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Queue;

import static org.junit.Assert.*;

public class CommTest {
    private Comm uut;
    private Queue<AbsReq> outReqQueue;
    private Queue<Serializable> outObjQueue;
    private Field commLayerF;
    private boolean sentReq;
    private boolean closed;
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception{
        uut=new Comm();
        Field f=Comm.class.getDeclaredField("outObjQueue");
        f.setAccessible(true);
        outObjQueue=(Queue)f.get(uut);
        f=Comm.class.getDeclaredField("outReqQueue");
        f.setAccessible(true);
        outReqQueue=(Queue)f.get(uut);
        commLayerF=Comm.class.getDeclaredField("commLayer");
        commLayerF.setAccessible(true);
        sentReq=false;
        closed=false;
    }

    @Test(timeout = 1000)
    public void testPushPop() throws Exception{
        KeepAliveRequest req=new KeepAliveRequest();
        uut.pushOutReq(req);
        uut.pushOutObj("test");
        assertEquals(req,outReqQueue.peek());
        assertEquals("test",outObjQueue.peek());

        Instant ts=uut.getLastSeen();
        uut.pushInReq(req);
        assertEquals(req,uut.popInPendingReq());
        while(ts==uut.getLastSeen());

        ts=uut.getLastSeen();
        uut.pushInObj("test");
        assertEquals("test",uut.popInPendingObj());
        while(ts==uut.getLastSeen());
    }

    @Test
    public void testInit() throws Exception{
        assertNull(uut.getUsername());
        assertNull(uut.getHost());
        assertNull(uut.getPassword());
        assertNull(uut.getMatchInfo());
        uut.changeLayer(true,0,0);
        uut.sendOutObj();
        uut.sendOutReq();

        MatchIdentifier mId=new MatchIdentifier("a","b",null,null);
        uut.setMatchInfo(mId);
        assertEquals(mId,uut.getMatchInfo());

        Method setPw=Comm.class.getDeclaredMethod("setPassword", String.class);
        setPw.setAccessible(true);
        setPw.invoke(uut,"testPw");
        assertEquals("testPw",uut.getPassword());

        Method setUsn=Comm.class.getDeclaredMethod("setUsername", String.class);
        setUsn.setAccessible(true);
        setUsn.invoke(uut,"test");
        assertEquals("test",uut.getUsername());
    }

    @Test
    public void testLogout() throws Exception{
        commLayerF.set(uut,new TestCommLayer(uut));
        assertTrue(uut.logout());
        assertTrue(sentReq);
        assertTrue(closed);
    }

    @Test
    public void testSend() throws Exception{
        commLayerF.set(uut,new TestCommLayer(uut));
        KeepAliveRequest req=new KeepAliveRequest();
        uut.pushOutReq(req);
        uut.pushOutObj("test");

        uut.sendOutReq();
        assertTrue(sentReq);
        sentReq=false;
        uut.sendOutObj();
        assertTrue(sentReq);
    }

    @Test
    public void testChangeLayer() throws Exception{
        commLayerF.set(uut,new TestCommLayer(uut));
        uut.changeLayer(true,1,2);
        assertTrue(sentReq);

        sentReq=false;
        uut.changeLayer(false,1,2);
        assertFalse(sentReq);
    }

    @Test
    public void testCommLayer() throws Exception{
        CommLayer cL=new TestCommLayer(uut);
        KeepAliveRequest kA=new KeepAliveRequest();
        commLayerF.set(uut,cL);
        cL.receiveInObj("test");
        cL.receiveInReq(kA);
        assertEquals(kA,uut.popInPendingReq());
        assertEquals("test",uut.popInPendingObj());
    }
    private class TestCommLayer extends CommLayer{

        TestCommLayer(Comm comm) {
            super(comm);
        }

        @Override
        String establishCon(String host, int reqPort, int objPort, boolean isRecovery, String usn, String pw, boolean newUser) {
            return null;
        }

        @Override
        boolean endCon() {
            return false;
        }

        @Override
        boolean sendOutObj(Serializable obj) {
            sentReq=true;
            return true;
        }

        @Override
        boolean sendOutReq(AbsReq req) {
            sentReq=true;
            return true;
        }

        @Override
        boolean close() {
            closed=true;
            return true;
        }
    }
}