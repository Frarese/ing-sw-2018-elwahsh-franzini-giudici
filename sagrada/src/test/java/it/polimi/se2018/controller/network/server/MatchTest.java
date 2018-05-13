package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.LeaveMatchRequest;
import it.polimi.se2018.controller.network.MatchAbortedRequest;
import it.polimi.se2018.controller.network.UserReconnectedRequest;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MatchTest {
    private Match uut;
    private ClientTest c1,c2,c3,c4;
    private List<Client> list;
    private MatchIdentifier mId;
    @Before
    public void setUp() throws Exception {
        c1=new ClientTest("us1",null);
        c2=new ClientTest("us2",null);
        c3=new ClientTest("us3",null);
        c4=new ClientTest("us4",null);
        mId=new MatchIdentifier("us1","us2","us3","us4");
        list=new ArrayList<>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
    }

    @Test
    public void testInit() {
        uut=new Match(mId,list,null);

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
        uut=new Match(mId,list,null);
    }

    @Test(expected = NullPointerException.class)
    public void testAbort() {
        uut=new Match(mId,list,null);
        uut.abort();
    }

    @Test
    public void testPushObj() {
        uut=new Match(mId,list,null);
        uut.handleObj("test",c1);
        assertFalse(c1.pushed);
        assertTrue(c2.pushed);
        assertTrue(c3.pushed);
        assertTrue(c4.pushed);
    }

    @Test
    public void testLeave() {
        uut=new Match(mId,list,null);
        uut.playerLeft("us1",true);
        assertFalse(c1.dc);
        assertTrue(c2.dc);
        assertTrue(c3.dc);
        assertTrue(c4.dc);
    }

    @Test
    public void testTerminalLeave() {
        uut=new Match(mId,list,null);
        uut.playerLeft("us1",true);
        uut.playerLeft("us2",true);
        try{
            uut.playerLeft("us3",true);
            fail();
        }catch (NullPointerException e){
            assertTrue(c1.aborted);
            assertTrue(c2.aborted);
            assertTrue(c3.aborted);
            assertTrue(c4.aborted);
        }
    }

    @Test
    public void testReconnection() {
        uut=new Match(mId,list,null);
        uut.playerLeft("us1",true);
        uut.playerReconnected("us1");
        assertFalse(c1.rec);
        assertTrue(c2.rec);
        assertTrue(c3.rec);
        assertTrue(c4.rec);
    }

    private class ClientTest extends Client{
        boolean pushed=false;
        boolean aborted=false;
        boolean dc=false;
        boolean rec=false;
        ClientTest(String usn, ServerMain server) {
            super(usn, server);
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
            }
        }
    }
}