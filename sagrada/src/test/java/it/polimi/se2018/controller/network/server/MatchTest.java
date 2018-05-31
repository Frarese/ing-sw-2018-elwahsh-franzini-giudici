package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.*;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MatchTest {
    private Match uut;
    private ClientTest c1,c2,c3,c4;
    private List<Client> list;
    private MatchIdentifier mId;
    private ServerMain s;
    private boolean handled;
    @Before
    public void setUp() throws Exception{
        c1=new ClientTest("us1");
        c2=new ClientTest("us2");
        c3=new ClientTest("us3");
        c4=new ClientTest("us4");
        mId=new MatchIdentifier("us1","us2","us3","us4");
        list=new ArrayList<>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        s=new ServerMain(0,0,0,"a",InetAddress.getLocalHost(),new MockFactory());
        uut=new Match(mId,list,s);
        s.addMatch(uut);
        handled=false;
    }

    @Test
    public void testInit() {
        List<Client> listRetr=uut.getClients();
        assertEquals(list,listRetr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailInit() {
        list=new ArrayList<>();
        list.add(c1);
        uut=new Match(mId,list,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateInit() {
        list=new ArrayList<>();
        list.add(c1);
        list.add(c1);
        list.add(c2);
        list.add(c3);
        uut=new Match(mId,list,s);
        assertNull(s.getMatch(mId));
    }

    @Test
    public void testPushObj() {
        uut.handleObj("test",c1);
        assertFalse(c1.pushed);
        assertTrue(c2.pushed);
        assertTrue(c3.pushed);
        assertTrue(c4.pushed);
    }

    @Test
    public void testLeave() {
        s.addClient(c1);
        uut.playerLeft("us1",false);
        assertFalse(c1.dc);
        assertTrue(c2.dc);
        assertTrue(c3.dc);
        assertTrue(c4.dc);
        assertNull(c1.getMatch());
    }

    @Test
    public void testTerminalLeave() {
        uut.playerLeft("us1",true);
        uut.playerLeft("us2",true);
        uut.playerLeft("us3",true);
        assertTrue(c1.aborted);
        assertTrue(c2.aborted);
        assertTrue(c3.aborted);
        assertTrue(c4.aborted);
    }

    @Test
    public void testReconnection() {
        uut.playerLeft("us1",true);
        uut.playerReconnected("us1");
        assertFalse(c1.rec);
        assertTrue(c2.rec);
        assertTrue(c3.rec);
        assertTrue(c4.rec);
    }

    @Test
    public void testEnd(){
        uut.end(0,1,2,3);
        assertNull(s.getMatch(mId));
    }

    @Test
    public void testSend(){
        uut.sendObj("test");
        assertTrue(c1.pushed);

        uut.sendReq("test","us1");
        assertTrue(c1.cReq);
    }

    @Test
    public void testHandle(){
        uut.handleReq(null,null);
        assertTrue(handled);
    }

    private class ClientTest extends Client{
        boolean pushed=false;
        boolean aborted=false;
        boolean dc=false;
        boolean rec=false;
        boolean cReq=false;
        ClientTest(String usn) {
            super(usn, null);
        }

        @Override
        public void pushOutObj(Serializable obj){
            pushed=true;
        }
        @Override
        public void pushOutReq(AbsReq req){
            if(req.getClass().equals(LeaveMatchRequest.class)){
                dc=true;
            }else if(req.getClass().equals(MatchAbortedRequest.class)){
                aborted=true;
            }else if(req.getClass().equals(UserReconnectedRequest.class)){
                rec=true;
            }else if(req.getClass().equals(ClientRequest.class)){
                cReq=true;
            }
        }
    }

    private class MockFactory implements MatchControllerFactory{

        @Override
        public MatchController buildMatch(MatchIdentifier mId, MatchNetworkInterface network) {
            return new MockController();
        }
    }

    private class MockController implements MatchController{
        @Override
        public void handleRequest(String sender, Serializable req) {
            handled=true;
        }

        @Override
        public void kill() {

        }

        @Override
        public void userReconnected(String username) {

        }

        @Override
        public void playerLeft(String username, boolean permanent) {

        }
    }
}