package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.ScoreEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

/**
 * Tester class for the ServerMain class
 * @author Francesco Franzini
 */
public class ServerMainTest {
    private ServerMain uut;
    private ConcurrentHashMap<String,Client> clientMap;
    private ConcurrentHashMap<String,Match> matches;
    private MatchMakingQueue matchMakingQueue;

    /**
     * Prepares the objects to be used
     * @throws Exception if an error occurs
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        File uf=new File("users.csv");
        if(uf.exists())assertTrue(uf.delete());
        uut=new ServerMain(-1,-1,-1,LoginResponsesEnum.RESOURCE_NAME.msg,InetAddress.getLocalHost(),new MockFactory());

        Field f=ServerMain.class.getDeclaredField("clientMap");
        f.setAccessible(true);
        clientMap=(ConcurrentHashMap)f.get(uut);

        f=ServerMain.class.getDeclaredField("matches");
        f.setAccessible(true);
        matches=(ConcurrentHashMap)f.get(uut);

        f=ServerMain.class.getDeclaredField("matchMakingQueue");
        f.setAccessible(true);
        matchMakingQueue=(MatchMakingQueue)f.get(uut);
    }

    /**
     * Tests that the object has been correctly initialized
     */
    @Test
    public void testInit() {
        assertTrue(matches.isEmpty());
        assertTrue(clientMap.isEmpty());
    }

    /**
     * Tests the client related methods
     */
    @Test
    public void testAddRemoveClient(){
        Client c=new Client("test",uut);
        assertTrue(uut.addClient(c));
        assertFalse(uut.addClient(c));
        assertTrue(uut.isUserLogged("test"));
        assertEquals(c,uut.getClient("test"));

        uut.removeClient("test");
        assertFalse(uut.isUserLogged("test"));
    }

    /**
     * Tests the user related methods
     */
    @Test
    public void testUserDB(){
        assertFalse(uut.existsUsn("user"));
        assertFalse(uut.isPwCorrect("user","pw"));

        assertTrue(uut.createUser("user","pw"));

        assertFalse(uut.createUser("user","pw"));
        assertTrue(uut.existsUsn("user"));
        assertTrue(uut.isPwCorrect("user","pw"));
        assertFalse(uut.isPwCorrect("user","pw2"));

        uut.createUser("user2","pw2");
        Client c=new Client("user",uut);
        uut.addClient(c);

        List<ScoreEntry> userList=uut.getUserListCopy();
        assertEquals(1,userList.size());
        assertEquals("user",userList.get(0).usn);

        uut.updateScore("user2",100,100);
        List<ScoreEntry> userLead=uut.getRegisteredUsers();
        assertEquals(2,userLead.size());
        int i;
        if(userLead.get(0).usn.equals("user2")){
            i=0;
        }else{
            i=1;
        }
        assertEquals(100,userLead.get(i).wins);
    }

    /**
     * Tests the methods for matchmaking handling
     */
    @Test
    public void testMMaking(){
        Client c=new Client("test",uut);
        uut.addToMatchMaking(c);
        assertTrue(matchMakingQueue.contains(c));
        uut.removeFromMatchMaking(c);
        assertFalse(matchMakingQueue.contains(c));

    }

    /**
     * Tests the methods for pending approval match handling
     * @throws Exception if an error occurs
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testPAMatch() throws Exception{
        Client c1=new Client("test1",uut);
        Client c2=new Client("test2",uut);
        Client c3=new Client("test3",uut);
        Client c4=new Client("test4",uut);
        MatchIdentifier mId=new MatchIdentifier("test1","test2","test3","test4");
        uut.addClient(c1);
        uut.addClient(c2);
        uut.addClient(c3);
        uut.addClient(c4);
        uut.addPendingMatch(mId,c1);
        Field f=ServerMain.class.getDeclaredField("pendingMatchesMap");
        f.setAccessible(true);
        ConcurrentHashMap<String, PendingApprovalMatch> pendingMatchesMap = (ConcurrentHashMap) f.get(uut);
        assertTrue(pendingMatchesMap.containsKey(mId.toString()));
        assertTrue(c1.hasAcceptedInvite());

        c2.acceptInvite();
        uut.addPendingMatch(new MatchIdentifier("test1","test2",null,null),c2);
    }

    /**
     * Tests the methods for match handling
     */
    @Test
    public void testMatch(){
        Client c1=new Client("test",uut);
        Client c2=new Client("test1",uut);

        MatchIdentifier mId=new MatchIdentifier("test","test1",null,null);
        uut.addClient(c1);
        uut.addClient(c2);
        List<Client>list=new ArrayList<>();
        list.add(c1);
        list.add(c2);
        Match m=new Match(mId,list,uut);
        uut.addMatch(m);
        assertNotNull(uut.getMatch(mId));
        uut.removeMatch(m);
        assertNull(uut.getMatch(mId));

    }

    /**
     * Tests the close method
     */
    @Test
    public void testClose() {
        Client c1=new Client("test",uut);
        uut.addClient(c1);
        uut.closeDown();
        assertNull(uut.getClient(c1.usn));
    }

    /**
     * Mock controller factory used to avoid NullPointer exceptions
     */
    private class MockFactory implements MatchControllerFactory{

        @Override
        public MatchController buildMatch(MatchIdentifier mId, MatchNetworkInterface network) {
            return null;
        }
    }

}