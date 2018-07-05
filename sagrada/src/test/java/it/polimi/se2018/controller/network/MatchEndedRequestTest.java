package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tester class for the MatchEndedRequest
 * @author Francesco Franzini
 */
public class MatchEndedRequestTest {
    private MatchEndedRequest uut;
    private boolean notified;

    /**
     * Tests that the object is correctly initialized
     */
    @Test
    public void testOkInit() {
        uut=new MatchEndedRequest(new MatchIdentifier("a","b",null,null)
                ,0,0,0,0);
        uut.serverVisit(new Client("test",null).getServerVisitor());
    }

    /**
     * Tests that illegal arguments are correctly detected
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFailInit() {
        uut=new MatchEndedRequest(null,0,0,0,0);
    }

    /**
     * Tests the client method
     */
    @Test
    public void testClientHandle() {
        uut=new MatchEndedRequest(new MatchIdentifier("a","b",null,null)
                ,0,0,0,0);
        notified=false;
        uut.clientHandle(null,new UtilizerMock());
        assertTrue(notified);
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

        }

        @Override
        public void abortMatch() {

        }

        @Override
        public void notifyInvite(MatchIdentifier match) {

        }

        @Override
        public void notifyMatchEnd(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {
            notified=true;
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