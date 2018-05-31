package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;

import static org.junit.Assert.*;

public class MatchInviteRequestTest {
    private ServerMainMock s;
    private MatchInviteRequest uut;
    private boolean invite;

    @Before
    public void setUp() throws Exception{
        s=new ServerMainMock();
        invite=false;
    }

    @Test
    public void testHandle() {
        MatchIdentifier mId=new MatchIdentifier("us1","us2",null,null);
        uut=new MatchInviteRequest(mId);
        uut.serverHandle(new Client("us1",s),s);
        assertTrue(s.added);

        uut.clientHandle(null,new CommUtilizerMock());
        assertTrue(invite);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failInit() {
        uut=new MatchInviteRequest(null);
    }


    private class ServerMainMock extends ServerMain {
        boolean added=false;
        ServerMainMock() throws IOException {
            super(0,0,0,"a",InetAddress.getLocalHost(),null);
        }

        @Override
        public void addPendingMatch(MatchIdentifier mId, Client c) {
            added=true;
        }
    }

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
            invite=true;
        }

        @Override
        public void notifyMatchEnd(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {

        }

        @Override
        public void notifyMatchStart() {

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