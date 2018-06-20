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


/**
 * Tester class for the client Comm
 * @author Francesco Franzini
 */
public class CommTest {
    private Comm uut;
    private Queue<AbsReq> outReqQueue;
    private Queue<Serializable> outObjQueue;
    private Field commLayerF;
    private boolean sentReq;
    private boolean closed;
    private boolean failLogin;
    private boolean fail;

    /**
     * Prepares the flags to be used, instantiates the tested object
     * and extracts the needed objects to check
     */
    @SuppressWarnings("unchecked")
    @Before
    public void testSetUp() throws Exception{
        uut=new Comm();
        fail=false;
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

    /**
     * Tests if the push and pop methods work and if they update the timestamp
     * @throws Exception if something fails
     */
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

    /**
     * Tests if the user parameters are correctly initialized to {@code null}
     * and if changeLayer without a {@code CommLayer} initialized correctly does nothing
     */
    @Test
    public void testInit(){
        assertNull(uut.getUsername());
        assertNull(uut.getHost());
        assertNull(uut.getPassword());
        assertNull(uut.getMatchInfo());

        uut.changeLayer(true,0,0);
    }

    /**
     * Tests if the setters correctly alter the values of the user info
     * @throws Exception if something fails
     */
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

    /**
     * Tests if the {@code logout} method correctly closes the connection
     * @throws Exception if something fails
     */
    @Test
    public void testLogout() throws Exception{
        commLayerF.set(uut,new TestCommLayer(uut));
        assertTrue(uut.logout());
        assertTrue(sentReq);
        assertTrue(closed);
    }

    /**
     * Tests if the send methods correctly push the objects to the queues
     * @throws Exception if something fails
     */
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

        uut.pushOutObj("test");
        fail=true;

        sentReq=false;
        uut.sendOutObj();
        assertTrue(sentReq);

        uut=new Comm();
        sentReq=false;
        commLayerF.set(uut,new TestCommLayer(uut));
        uut.pushOutReq(req);
        uut.sendOutReq();
        assertTrue(sentReq);
    }

    /**
     * Tests if the {@code changeLayer} method correctly handles invalid or valid parameters
     * @throws Exception if something fails
     */
    @Test
    public void testChangeLayer() throws Exception{
        commLayerF.set(uut,new TestCommLayer(uut));
        uut.changeLayer(true,1,2);
        assertTrue(sentReq);

        sentReq=false;
        uut.changeLayer(false,1,2);
        assertFalse(sentReq);

        fail=true;
        commLayerF.set(uut,new TestCommLayer(uut));
        uut.changeLayer(true,1,2);
        assertTrue(sentReq);
    }

    /**
     * Tests if the incoming objects are correctly pushed in the inbound queues
     * @throws Exception if something fails
     */
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

    /**
     * Tests if the {@code tryRecover} method correctly behaves when reconnection fails
     */
    @Test
    public void testReconnection(){
        CommMock uut2=new CommMock();
        assertTrue(uut2.tryRecover(false));
        assertFalse(uut2.purged);

        uut2.fail=true;
        assertFalse(uut2.tryRecover(true));
        assertTrue(uut2.purged);
    }

    /**
     * Tests if the {@code createComm} method correctly creates a CommLayer
     * @throws Exception if something fails
     */
    @Test
    public void testCreateComm() throws Exception{
        assertTrue(uut.createComm(true));
        assertEquals(RMICommLayer.class,commLayerF.get(uut).getClass());

        assertFalse(uut.createComm(false));

        commLayerF.set(uut,null);
        assertTrue(uut.createComm(false));
        assertEquals(SocketCommLayer.class,commLayerF.get(uut).getClass());
    }

    /**
     * Tests if the {@code login} method correctly handles login responses from the comm layer
     */
    @Test
    public void testLogin(){
        CommMock uut2=new CommMock();
        uut2.noMockLogin=true;
        failLogin=true;

        String result=uut2.login("host",1,2,"usn","pw",true,false,null);
        assertNotNull(result);

        failLogin=false;
        result=uut2.login("host",1,2,"usn","pw",true,false,null);
        assertNull(result);
    }

    /**
     * Tests if the {@code logoutRequestReceived} method correctly handles logout
     * @throws Exception if something fails
     */
    @Test
    public void testLogoutReq() throws Exception{
        commLayerF.set(uut,new TestCommLayer(uut));
        uut.logoutRequestReceived();
        assertTrue(closed);
    }

    /**
     * Tests if Comm correctly initiates reconnect procedures
     * @throws Exception if something fails
     */
    @Test
    public void testDCRoutines() throws Exception{
        Field f=Comm.class.getDeclaredField("reconnectW");
        f.setAccessible(true);

        uut.beginDisconnectedRoutines();
        ReconnectWaker rW=(ReconnectWaker)f.get(uut);
        assertTrue(rW.isRunning());

    }

    /**
     * Mock class used to intercept calls to CommLayer
     */
    private class TestCommLayer extends CommLayer{

        TestCommLayer(Comm comm) {
            super(comm);
        }

        @Override
        String establishCon(String host, int reqPort, int objPort, String usn, String pw, boolean newUser) {
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
            return !fail;
        }

        @Override
        boolean sendOutReq(AbsReq req) {
            sentReq=true;
            return !fail;
        }

        @Override
        boolean close() {
            closed=true;
            return true;
        }
    }

    /**
     * Mock class used to intercept calls to methods of Comm that would fail in a simulated environment
     */
    private class CommMock extends Comm{
        boolean fail;
        boolean purged;
        boolean noMockLogin=false;
        CommMock(){
            this.fail=false;
            purged=false;
        }
        @Override
        public String login(String host, int requestPort, int objectPort, String usn, String pw, boolean newUser, boolean useRMI, CommUtilizer utilizer) {
            if(noMockLogin)return super.login(host,requestPort,objectPort,usn,pw,newUser,useRMI,utilizer);
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