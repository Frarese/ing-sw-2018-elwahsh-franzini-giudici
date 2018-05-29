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
    private MatchmakingRequest uut;

    @Before
    public void setUp() throws Exception{
        s=new ServerMainMock();
    }

    @Test
    public void testServerHandle(){
        uut=new MatchmakingRequest(true);
        uut.serverHandle(null,s);
        assertTrue(s.added);

        assertTrue(uut.checkValid());

        uut=new MatchmakingRequest(false);
        uut.serverHandle(null,s);
        assertFalse(s.added);
    }

    private class ServerMainMock extends ServerMain{
        boolean added=false;
        ServerMainMock() throws IOException {
            super(0,0,false,0,"a",InetAddress.getLocalHost(),null);
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