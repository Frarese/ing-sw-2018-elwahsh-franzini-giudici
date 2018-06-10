package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.*;

public class UserReconnectedRequestTest {
    private UserReconnectedRequest uut;
    private String name;
    private boolean called;

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument() {
        uut=new UserReconnectedRequest(null);
    }

    @Test
    public void testInit() {
        uut=new UserReconnectedRequest("test");
        assertTrue(uut.checkValid());
        uut.clientHandle(null,new UtilizerMock());
        assertEquals("test",name);

        called=false;
        uut.serverVisit(new VisitorMock());
        assertTrue(called);
    }
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
            name=usn;
        }
    }

    private class VisitorMock implements ServerVisitor{

        @Override
        public void handle(ChangeCLayerRequest c) {

        }

        @Override
        public void handle(ChatMessageRequest c) {

        }

        @Override
        public void handle(ClientRequest c) {

        }

        @Override
        public void handle(GetLeaderBoardRequest c) {

        }

        @Override
        public void handle(KeepAliveRequest c) {

        }

        @Override
        public void handle(LeaveMatchRequest c) {

        }

        @Override
        public void handle(ListUsersRequest c) {

        }

        @Override
        public void handle(LogoutRequest c) {

        }

        @Override
        public void handle(MatchAbortedRequest c) {

        }

        @Override
        public void handle(MatchBeginRequest c) {

        }

        @Override
        public void handle(MatchEndedRequest c) {

        }

        @Override
        public void handle(MatchInviteRequest c) {

        }

        @Override
        public void handle(MatchmakingRequest c) {

        }

        @Override
        public void handle(MatchResponseRequest c) {

        }

        @Override
        public void handle(UserReconnectedRequest c) {
            called=true;
        }
    }
}