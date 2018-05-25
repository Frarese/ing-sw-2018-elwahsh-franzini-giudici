package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ListUsersRequestTest {
    private ListUsersRequest uut;
    private List<ScoreEntry> list;
    private ServerMain s;
    private Client c;
    private boolean pushed;
    @Before
    public void setUp() throws Exception{
        pushed=false;
        uut=new ListUsersRequest();
        list=new ArrayList<>();
        s=new ServerMain(0,0,false,0,"a",InetAddress.getLocalHost(),null);
        c=new ClientMock();
    }

    @Test
    public void testInit() {
        assertTrue(uut.checkValid());
        assertNull(uut.getList());
    }

    @Test
    public void testSet() {
        list=new ArrayList<>();
        uut.setList(list);
        assertEquals(list,uut.getList());
    }

    @Test
    public void testClientHandle() {
        uut.setList(list);
        uut.clientHandle(null,new UtilizerMock());
        assertTrue(pushed);
    }

    @Test
    public void serverClientHandle() {
        uut.setList(list);
        uut.serverHandle(c,s);
        assertTrue(pushed);
    }

    private class ClientMock extends Client{

        ClientMock() {
            super("test", s);
        }

        @Override
        public void pushOutReq(AbsReq req) {
            pushed=true;
        }
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
            pushed=true;
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