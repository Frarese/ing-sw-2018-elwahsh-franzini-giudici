package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;
import org.junit.Test;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;

import static org.junit.Assert.*;

public class ChatMessageRequestTest {
    private ChatMessageRequest uut;
    private boolean received;
    @Test
    public void testInit() {
        uut=new ChatMessageRequest("test","test2","test",MessageTypes.MATCH);
        assertEquals("test",uut.sender);
        assertEquals("test2",uut.destination);
        assertEquals("test",uut.msg);
        assertEquals(MessageTypes.MATCH,uut.type);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailInit() {
        uut=new ChatMessageRequest("test","test","test",MessageTypes.MATCH);
    }

    @Test
    public void testClient(){
        received=false;
        uut=new ChatMessageRequest("test","test2","test",MessageTypes.MATCH);
        uut.clientHandle(null,new TestUtilizer());
        assertTrue(received);
    }

    @Test
    public void testServer() throws Exception{
        ServerMain s=new ServerMain(1,3,2,"test",InetAddress.getLocalHost(),null);
        uut=new ChatMessageRequest("test","test2","test",MessageTypes.PM);
        uut.serverVisit(new Client("test",s).getServerVisitor());

        uut=new ChatMessageRequest("test","test2","test",MessageTypes.MATCH);
        uut.serverVisit(new Client("test",s).getServerVisitor());

        uut=new ChatMessageRequest("test","test2","test",MessageTypes.MATCH);
        uut.serverVisit(new Client("testInvalid",s).getServerVisitor());
    }

    private class TestUtilizer implements CommUtilizer{

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

        }

        @Override
        public void pushChatMessage(String msg, MessageTypes type, String source) {
            received=true;
        }

        @Override
        public void notifyReconnect() {

        }

        @Override
        public void notifyUserReconnected(String usn) {

        }
    }
}