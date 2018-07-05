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

/**
 * Tester class for the GetLeaderBoardRequest
 * @author Francesco Franzini
 */
public class GetLeaderBoardRequestTest {
    private GetLeaderBoardRequest uut;
    private Client c;

    /**
     * Prepares the object to be tested
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception{
        uut=new GetLeaderBoardRequest();
        ServerMain s = new ServerMain(0, 0, 0, "", InetAddress.getLocalHost(), null);
        c=new Client("test", s);
    }

    /**
     * Tests the {@code checkValid} method and that the server call does not cause exceptions
     */
    @Test
    public void testInit() {
        assertTrue(uut.checkValid());
        uut.serverVisit(c.getServerVisitor());
    }

    /**
     * Tests the server call
     * @throws Exception if an error occurs
     */
    @Test
    public void testServer() throws Exception{
        uut.serverVisit(c.getServerVisitor());
        Field f=Client.class.getDeclaredField("outReqQueue");
        f.setAccessible(true);
        Queue q=(Queue)f.get(c);
        assertNotNull(q.peek());
    }

    /**
     * Tests the client call
     * @throws Exception if an error occurs
     */
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

    /**
     * Checks if a given list is an ordered version of another list
     * @param l the reference list
     * @param oL the potentially ordered list
     * @return true if {@code oL} is ordered and has only(and all) the objects in {@code l}
     */
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

    /**
     * Mock utilizer used to intercept method calls
     */
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