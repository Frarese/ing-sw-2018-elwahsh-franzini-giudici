package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.Assert.*;

public class MatchmakingRequestTest {
    private ServerMainMock s;
    private Client c;

    @Before
    public void setUp() throws Exception{
        s=new ServerMainMock();
        c=new Client("test",s);
    }

    @Test
    public void testServerHandle(){
        MatchmakingRequest uut = new MatchmakingRequest(true);
        uut.clientHandle(null,null);

        uut.serverVisit(c.getServerVisitor());
        assertTrue(s.added);

        assertTrue(uut.checkValid());

        uut =new MatchmakingRequest(false);
        uut.serverVisit(c.getServerVisitor());
        assertFalse(s.added);
    }

    private class ServerMainMock extends ServerMain{
        boolean added=false;
        ServerMainMock() throws IOException {
            super(0,0,0,"a",InetAddress.getLocalHost(),null);
        }

        @Override
        public void addToMatchMaking(Client client) {
            added=true;
        }

        @Override
        public void removeFromMatchMaking(Client client) {
            added=false;
        }
    }
}