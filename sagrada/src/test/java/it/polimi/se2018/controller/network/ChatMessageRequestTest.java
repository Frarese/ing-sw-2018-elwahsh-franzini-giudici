package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;
import org.junit.Test;

import java.io.Serializable;
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
    }

    @Test
    public void testServer() throws Exception{
        ServerMain s=new ServerMain(1,3,false,2,"test");
        uut=new ChatMessageRequest("test","test2","test",MessageTypes.PM);
        uut.serverHandle(new Client("test",s),s);

        uut=new ChatMessageRequest("test","test2","test",MessageTypes.MATCH);
        uut.serverHandle(new Client("test",s),s);
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
        public void notifyMatchEnd() {

        }

        @Override
        public void notifyMatchStart(boolean isHost) {

        }

        @Override
        public void notifyKicked(String usn) {

        }

        @Override
        public void notifyUserLeft(String usn, boolean isNewHost) {

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