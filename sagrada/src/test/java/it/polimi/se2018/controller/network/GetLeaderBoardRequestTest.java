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
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.*;

public class GetLeaderBoardRequestTest {
    private GetLeaderBoardRequest uut;

    @Before
    public void setUp() {
        uut=new GetLeaderBoardRequest();
    }

    @Test(expected = NullPointerException.class)
    public void testInit() {
        assertTrue(uut.checkValid());
        uut.serverHandle(null,null);
    }

    @Test
    public void testServer() throws Exception{
        ServerMain s=new ServerMain(0,0,false,0,"",InetAddress.getLocalHost(),null);
        Client c=new Client("test",s);
        uut.serverHandle(c,s);
        Field f=Client.class.getDeclaredField("outReqQueue");
        f.setAccessible(true);
        Queue q=(Queue)f.get(c);
        assertNotNull(q.peek());
    }

    @Test
    public void testClient() throws Exception {
        uut.clientHandle(null,null);
        Field f=uut.getClass().getDeclaredField("leaderboard");
        f.setAccessible(true);
        List<ScoreEntry> list=new ArrayList<>();
        list.add(new ScoreEntry("user1",100,200));
        list.add(new ScoreEntry("user2",100,300));

        f.set(uut,list);
        uut.clientHandle(null, new UtilizerMock());
        assertTrue(isOrderedVersion(list, (List) f.get(uut)));

    }

    @SuppressWarnings("unchecked")
    private boolean isOrderedVersion(List<ScoreEntry> l,List oL){
        if(!(l.containsAll(oL)&&oL.containsAll(l)))return false;
        ScoreEntry prev=null;
        for(Object e:oL){
            if(prev!=null){
                if(((ScoreEntry)e).wins>prev.wins){
                    return false;
                }
            }
            prev=(ScoreEntry)e;
        }
        return true;
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

        }
    }
}