package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.ChangeCLayerRequest;
import it.polimi.se2018.controller.network.MatchResponseRequest;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.*;

public class ServerVisitorImplTest {

    private ServerVisitorImpl uut;
    private Client c;
    private ServerMain s;

    @Before
    public void testSetUp()throws Exception{
        s=new ServerMain(0,0,0,"test",InetAddress.getLocalHost(),null);
        c=new Client("usn",s);
        Client c2 = new Client("usn2", s);
        s.addClient(c);
        s.addClient(c2);
        uut=new ServerVisitorImpl(c,s);
    }

    @Test
    public void testChange() {
        ChangeCLayerRequest cL=new ChangeCLayerRequest(true,1,2);
        uut.handle(cL);
        assertTrue(c.isZombie());
    }

    @Test
    public void testResponse() {
        MatchIdentifier mId=new MatchIdentifier("usn","usn2",null,null);
        s.addPendingMatch(mId,c);
        uut.handle(new MatchResponseRequest(true,mId));
        uut.handle(new MatchResponseRequest(false,mId));
        assertNull(s.getPendingMatch(mId));
    }
}