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
    private boolean failLogin;

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
        failLogin=false;
    }

    @Test(timeout = 1000)
    public void testPushPop() throws Exception{
        KeepAliveRequest req=new KeepAliveRequest();

        uut.pushOutReq(req);
        uut.pushOutObj("test");
        assertEquals(req,outReqQueue.peek());
        assertEquals("test",outObjQueue.peek());

        uut.pushInReq(req);
        assertEquals(req,uut.popInPendingReq());

        Instant ts=uut.getLastSeen();
        uut.pushInObj("test");
        assertEquals("test",uut.popInPendingObj());

        //Timestamp must change(otherwise JUnit timeout kicks in)
        while(ts==uut.getLastSeen());
    }

    @Test
    public void testInit(){
        assertNull(uut.getUsername());
        assertNull(uut.getHost());
        assertNull(uut.getPassword());
        assertNull(uut.getMatchInfo());

        uut.changeLayer(true,0,0);

    }

    @Test
    public void testSetters() throws Exception{
        Method setPw=Comm.class.getDeclaredMethod("setPassword", String.class);
        setPw.setAccessible(true);
        setPw.invoke(uut,"testPw");
        assertEquals("testPw",uut.getPassword());

        Method setUsn=Comm.class.getDeclaredMethod("setUsername", String.class);
        setUsn.setAccessible(true);
        setUsn.invoke(uut,"test");
        assertEquals("test",uut.getUsername());

        MatchIdentifier mId=new MatchIdentifier("a","b",null,null);
        uut.setMatchInfo(mId);
        assertEquals(mId,uut.getMatchInfo());
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
        assertEquals(req,outReqQueue.peek());
        assertEquals("test",outObjQueue.peek());

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

    @Test
    public void testReconnection(){
        CommMock uut2=new CommMock();
        assertTrue(uut2.tryRecover(false));
        assertFalse(uut2.purged);

        uut2.fail=true;
        assertFalse(uut2.tryRecover(true));
        assertTrue(uut2.purged);
    }

    @Test
    public void testCreateComm() throws Exception{
        assertTrue(uut.createComm(true));
        assertEquals(RMICommLayer.class,commLayerF.get(uut).getClass());

        assertFalse(uut.createComm(false));

        commLayerF.set(uut,null);
        assertTrue(uut.createComm(false));
        assertEquals(SocketCommLayer.class,commLayerF.get(uut).getClass());
    }

    @Test
    public void testLogin(){
        CommMock uut2=new CommMock();
        uut2.noMockLogin=true;
        failLogin=true;

        String result=uut2.login("host",1,2,true,"usn","pw",true,false,null);
        assertNotNull(result);

        failLogin=false;
        result=uut2.login("host",1,2,true,"usn","pw",true,false,null);
        assertNull(result);
    }

    @Test
    public void testDCRoutines() throws Exception{
        Field f=Comm.class.getDeclaredField("reconnectW");
        f.setAccessible(true);

        uut.beginDisconnectedRoutines();
        ReconnectWaker rW=(ReconnectWaker)f.get(uut);
        assertTrue(rW.isRunning());

    }
    private class TestCommLayer extends CommLayer{

        TestCommLayer(Comm comm) {
            super(comm);
        }

        @Override
        String establishCon(String host, int reqPort, int objPort, boolean isRecovery, String usn, String pw, boolean newUser) {
            if(failLogin)return "Login failed as requested";
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

    private class CommMock extends Comm{
        boolean fail;
        boolean purged;
        boolean noMockLogin=false;
        CommMock(){
            this.fail=false;
            purged=false;
        }
        @Override
        public String login(String host, int requestPort, int objectPort, boolean isRecovery, String usn, String pw, boolean newUser, boolean useRMI, CommUtilizer utilizer) {
            if(noMockLogin)return super.login(host,requestPort,objectPort,isRecovery,usn,pw,newUser,useRMI,utilizer);
            return (fail)?"Failed":null;
        }

        @Override
        public void purgeComm() {
            this.purged=true;
        }

        @Override
        boolean createComm(boolean useRMI) {
            try {
                commLayerF.set(this,new TestCommLayer(this));
            } catch (IllegalAccessException e) {
                return false;
            }
            return !fail;
        }
    }
}