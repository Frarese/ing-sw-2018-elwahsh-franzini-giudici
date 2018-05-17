package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class PendingApprovalMatchTest {
    private PendingApprovalMatch uut;
    private MatchIdentifier mId;
    private ClientTest c1,c2,c3,c4;
    private Field timerField;
    @Before
    public void setUp() throws Exception {
        timerField=PendingApprovalMatch.class.getDeclaredField("t");
        c1=new ClientTest("us1",null);
        c2=new ClientTest("us2",null);
        c3=new ClientTest("us3",null);
        c4=new ClientTest("us4",null);
        mId=new MatchIdentifier("us1","us2","us3","us4");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInit() {
        c1=new ClientTest("a",null);
        uut=new PendingApprovalMatch(1000,mId,null,c1);
    }

    @Test
    public void testAccept(){
        uut=new PendingApprovalMatch(1000,mId,null,c1);
        assertTrue(uut.clientAccepted(c2));
        assertFalse(uut.clientAccepted(new Client("a",null)));
    }

    @Test(expected = NullPointerException.class)
    public void testComplete(){
        uut=new PendingApprovalMatch(1000,mId,null,c1);
        uut.clientAccepted(c2);
        uut.clientAccepted(c3);
        uut.clientAccepted(c4);
    }

    @Test(expected = NullPointerException.class)
    public void abort(){
        uut=new PendingApprovalMatch(1000,mId,null,c1);
        uut.run();
        assertTrue(c1.aborted);
    }

    private class ClientTest extends Client{
        boolean aborted=false;
        ClientTest(String usn, ServerMain server) {
            super(usn, server);
        }
        @Override
        public void pushOutReq(AbsReq req){
            aborted=true;
        }
    }
}