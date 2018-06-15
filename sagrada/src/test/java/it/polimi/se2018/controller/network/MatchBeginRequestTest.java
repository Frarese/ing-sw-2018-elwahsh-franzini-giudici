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

import static org.junit.Assert.*;

public class MatchBeginRequestTest {
    private MatchBeginRequest uut;
    private boolean notified;
    private boolean set;


    @Test
    public void testOkInit() {
        uut=new MatchBeginRequest(new MatchIdentifier("a","b",null,null));
        uut.serverVisit(new Client("test",null).getServerVisitor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailInit() {
        uut=new MatchBeginRequest(null);
    }

    @Test
    public void testClientHandle() {
        uut=new MatchBeginRequest(new MatchIdentifier("a","b",null,null));
        notified=false;
        set=false;
        uut.clientHandle(new CommMock(),new UtilizerMock());
        assertTrue(notified);
        assertTrue(set);
    }

    private class UtilizerMock implements CommUtilizer {

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
            notified=true;
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

    private class CommMock extends Comm {
        @Override
        public void setMatchInfo(MatchIdentifier newInfo) {
            set=true;
        }
    }
}