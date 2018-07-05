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

/**
 * Tester class for the MatchInviteRequest
 * @author Francesco Franzini
 */
public class MatchInviteRequestTest {
    private ServerMainMock s;
    private MatchInviteRequest uut;
    private boolean invite;

    /**
     * Initializes the flags and objects to be used
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception{
        s=new ServerMainMock();
        invite=false;
    }

    /**
     * Tests the client and server methods
     */
    @Test
    public void testHandle() {
        MatchIdentifier mId=new MatchIdentifier("us1","us2",null,null);
        uut=new MatchInviteRequest(mId);
        uut.serverVisit(new Client("us1",s).getServerVisitor());
        assertTrue(s.added);

        uut.clientHandle(null,new CommUtilizerMock());
        assertTrue(invite);
    }

    /**
     * Tests that illegal arguments are correctly detected
     */
    @Test(expected = IllegalArgumentException.class)
    public void failInit() {
        uut=new MatchInviteRequest(null);
    }

    /**
     * Mock Server used to intercept method calls
     */
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
            invite=true;
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