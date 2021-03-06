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
 * Tester class for the LeaveMatchRequest
 * @author Francesco Franzini
 */
public class LeaveMatchRequestTest {

    private boolean notified;

    /**
     * Tests the request
     */
    @Test
    public void testRequest() {
        LeaveMatchRequest uut;
        try {
            uut = new LeaveMatchRequest(null);
            fail();
        }catch (IllegalArgumentException e){
            uut=new LeaveMatchRequest("test");
            assertEquals("test",uut.usn);
        }
        notified=false;
        CommUtilizerMock mock=new CommUtilizerMock();
        uut.clientHandle(null,mock);
        assertTrue(notified);

        uut.serverVisit(new Client("usn",null).getServerVisitor());
    }

    /**
     * Mock utilizer used to intercept method calls
     */
    private class CommUtilizerMock implements CommUtilizer{

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

        }

        @Override
        public void notifyMatchStart(MatchIdentifier mId) {

        }

        @Override
        public void notifyUserLeft(String usn) {
            notified=true;
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
