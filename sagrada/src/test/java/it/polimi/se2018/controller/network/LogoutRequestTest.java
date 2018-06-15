package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class LogoutRequestTest {

    @Test
    public void testClient(){
        LogoutRequest uut=new LogoutRequest();
        assertTrue(uut.checkValid());
        CommUtilizerMock mock=new CommUtilizerMock();
        uut.clientHandle(new CommMock(),mock);
        assertTrue(mock.dropped);
        ClientMock c=new ClientMock();
        uut.serverVisit(c.getServerVisitor());
        assertTrue(c.purged);
    }

    private class CommUtilizerMock implements CommUtilizer {
        boolean dropped=false;
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

        }

        @Override
        public void pushLeaderboard(List<ScoreEntry> list) {

        }

        @Override
        public void pushUserList(List<ScoreEntry> list) {

        }

        @Override
        public void notifyCommDropped() {
            dropped=true;
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
    private class CommMock extends Comm{
        @Override
        public void logoutRequestReceived() {

        }
    }

    private class ClientMock extends Client{
        boolean purged=false;
        ClientMock() {
            super("usn", null);
        }

        @Override
        public void purge(boolean leaveMatch) {
            purged=true;
        }
    }
}