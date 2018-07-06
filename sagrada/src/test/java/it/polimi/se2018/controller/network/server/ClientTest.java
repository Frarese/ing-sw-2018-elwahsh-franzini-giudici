package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.ChangeCLayerRequest;
import it.polimi.se2018.controller.network.KeepAliveRequest;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.SafeSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Tester class for the Client class
 * @author Francesco Franzini
 */
public class ClientTest {
    private Client uut;
    private Queue<AbsReq> outReqQueue;
    private Queue<Serializable> outObjQueue;

    private Queue<AbsReq> inReqQueue;
    private Queue<Serializable> inObjQueue;

    private ServerMain s;

    /**
     * Prepares the flags and objects to be used and the object to be tested
     * @throws Exception if an error occurs
     */
    @SuppressWarnings("unchecked")
    @Before
    public void testSetUp() throws Exception {
        s=new ServerMain(0,0
                ,0,"a",InetAddress.getLocalHost(),new MockFactory());

        uut=new ClientMock();

        Field f=Client.class.getDeclaredField("outObjQueue");
        f.setAccessible(true);
        outObjQueue=(Queue)f.get(uut);
        f=Client.class.getDeclaredField("outReqQueue");
        f.setAccessible(true);
        outReqQueue=(Queue)f.get(uut);

        f=Client.class.getDeclaredField("inObjQueue");
        f.setAccessible(true);
        inObjQueue=(Queue)f.get(uut);
        f=Client.class.getDeclaredField("inReqQueue");
        f.setAccessible(true);
        inReqQueue=(Queue)f.get(uut);
    }

    /**
     * Tests the basic methods(setters and getters)
     */
    @Test
    public void testBasics() {
        assertEquals("usn",uut.usn);
        assertTrue(uut.isZombie());
        assertFalse(uut.hasAcceptedInvite());
        assertNull(uut.getMatch());
        assertNull(uut.getPAM());

        uut.acceptInvite();
        assertTrue(uut.hasAcceptedInvite());
        uut.resetAccepted();
        assertFalse(uut.hasAcceptedInvite());

        MatchIdentifier mId=new MatchIdentifier("a","b",null,null);
        List<Client> list=new ArrayList<>();
        list.add(new Client("a",null));
        list.add(new Client("b",null));
        Match match=new Match(mId,list,s);
        PendingApprovalMatch pA=new PendingApprovalMatch(10000,mId,null,new Client("a",null));

        uut.enrollInMatch(match);
        assertEquals(match,uut.getMatch());
        assertFalse(uut.acceptPAMatch(pA));
        uut.removeMatchInstance();
        assertNull(uut.getMatch());

        assertTrue(uut.acceptPAMatch(pA));

        uut.removePAMInstance();
        assertNull(uut.getPAM());
    }

    /**
     * Tests the push and pop methods for the input pipes
     */
    @Test
    public void testPushPopIn(){
        KeepAliveRequest kA=new KeepAliveRequest();

        uut.pushInReq(kA);
        uut.pushInObj("test");
        assertEquals(kA,inReqQueue.peek());
        assertEquals("test",inObjQueue.peek());

        uut.handleObj();
        assertNull(inObjQueue.peek());

        uut.handleReq();
        assertNull(inReqQueue.peek());
    }

    /**
     * Tests the push and pop methods for the output pipes
     */
    @Test
    public void testPushPopOut(){
        KeepAliveRequest kA=new KeepAliveRequest();

        uut.pushOutReq(kA);
        uut.pushOutObj("test");
        assertEquals(kA,outReqQueue.peek());
        assertEquals("test",outObjQueue.peek());

        uut.processOutReq();
        assertEquals(kA,outReqQueue.peek());

        uut.processOutObj();
        assertEquals("test",outObjQueue.peek());
    }

    /**
     * Tests the push-back methods
     */
    @Test
    public void testPushBack(){
        KeepAliveRequest kA1=new KeepAliveRequest();
        KeepAliveRequest kA2=new KeepAliveRequest();
        uut.pushOutReq(kA1);
        uut.pushOutObj("test1");

        uut.pushObjBack("test2");
        uut.pushRequestBack(kA2);

        assertEquals(kA2,outReqQueue.peek());
        assertEquals("test2",outObjQueue.peek());
    }

    /**
     * Tests the RMI initialization method
     * @throws Exception if an error occurs
     */
    @Test
    public void testInitRMI() throws Exception{
        List<Client> list=new ArrayList<>();
        list.add(uut);
        list.add(new Client("test2",s));
        Field matchF=Client.class.getDeclaredField("match");
        matchF.setAccessible(true);
        matchF.set(uut,new Match(new MatchIdentifier("usn","test2",null,null),list,s));
        assertTrue(uut.createRMIComm(new RMISessionImpl(LoginResponsesEnum.LOGIN_OK)));
        assertFalse(uut.createRMIComm(new RMISessionImpl(LoginResponsesEnum.LOGIN_OK)));
        assertFalse(uut.createSocketComm(new SafeSocket(100),new SafeSocket(100)));
    }

    /**
     * Tests the socket initialization method
     * @throws Exception if an error occurs
     */
    @Test
    public void testInitSocket() throws Exception{
        assertTrue(uut.createSocketComm(new SafeSocket(100),new SafeSocket(100)));
        assertFalse(uut.createSocketComm(new SafeSocket(100),new SafeSocket(100)));
    }

    /**
     * Tests the {@code zombiefy} method
     * @throws Exception if an error occurs
     */
    @Test
    public void testZombiefy() throws Exception{
        uut=new Client("test",s);
        uut.createSocketComm(new SafeSocket(100),new SafeSocket(100));
        uut.zombiefy(false,null);
        assertFalse(uut.sendReq(new KeepAliveRequest()));
    }

    /**
     * Tests the purge method
     * @throws Exception if an error occurs
     */
    @Test
    public void testPurge() throws Exception{
        s.addClient(uut);
        uut.createSocketComm(new SafeSocket(100),new SafeSocket(100));
        uut.purge(true);
        assertNull(s.getClient(uut.usn));
    }

    /**
     * Mock controller factory used to avoid NullPointer exceptions
     */
    private class MockFactory implements MatchControllerFactory{

        @Override
        public MatchController buildMatch(MatchIdentifier mId, MatchNetworkInterface network) {
            return null;
        }
    }

    /**
     * Mock client used to intercept the call to {@code zombiefy}
     */
    private class ClientMock extends Client{
        ClientMock(){
            super("usn",s);
        }

        @Override
        public void zombiefy(boolean notifyMatchPlayers, ChangeCLayerRequest cReq) {

        }
    }
}