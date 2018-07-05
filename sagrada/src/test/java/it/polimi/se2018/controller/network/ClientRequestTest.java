package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tester class for the ClientRequest class
 * @author Francesco Franzini
 */
public class ClientRequestTest {

    private ClientRequest uut;
    private Client c;
    private boolean received;

    /**
     * Prepares the flags to be used and the object to be tested
     */
    @Before
    public void setUp() {
        c=new Client("test",null);
        uut=new ClientRequest("serializable");
        received=false;
    }

    /**
     * Tests that the server method is correctly called
     */
    @Test
    public void testServerHandle() {
        try {
            uut.serverVisit(c.getServerVisitor());
        }catch (NullPointerException e){
            fail(e.getMessage());
        }
    }

    /**
     * Tests that the client method is correctly called
     */
    @Test
    public void testClientHandle() {
        uut.clientHandle(null,new UtilizerMock());
        assertTrue(received);
    }

    /**
     * Mock utilizer used to intercept method calls
     */
    private class UtilizerMock implements CommUtilizer{

        @Override
        public void receiveObject(Serializable obj) {

        }

        @Override
        public void receiveRequest(Serializable req) {
            received=true;
        }

        @Override
        public void abortMatch() {

        }

        @Override
        public void notifyInvite(MatchIdentifier match) {

        }

        @Override
        public void notifyMatchEnd(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {

        }

        @Override
        public void notifyMatchStart(MatchIdentifier mId) {

        }

        @Override
        public void notifyUserLeft(String usn) {

        }

        @Override
        public void pushLeaderboard(List<ScoreEntry> list) {

        }

        @Override
        public void pushUserList(List<ScoreEntry> list) {

        }

        @Override
        public void notifyCommDropped() {

        }

        @Override
        public void pushChatMessage(String msg, MessageTypes type, String source) {

        }

        @Override
        public void notifyReconnect() {

        }

        @Override
        public void notifyUserReconnected(String usn) {

        }
    }
}