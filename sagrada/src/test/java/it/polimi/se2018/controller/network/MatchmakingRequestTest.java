package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.Assert.*;

/**
 * Tester class for the MatchMakingRequest
 * @author Francesco Franzini
 */
public class MatchmakingRequestTest {
    private ServerMainMock s;
    private Client c;

    /**
     * Initializes the objects to be used
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception{
        s=new ServerMainMock();
        c=new Client("test",s);
    }

    /**
     * Tests the server method
     */
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

    /**
     * Mock server used to intercept method calls
     */
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