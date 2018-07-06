package it.polimi.se2018.controller.game.server;

import it.polimi.se2018.controller.network.server.MatchNetworkInterface;

import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


import static org.junit.Assert.*;

/**
 * Test of the ServerController class
 * @author Francesco Franzini
 */
public class ServerControllerTest {
    private ServerController uut;
    private boolean sentObj;

    /**
     * Test initialization
     */
    @Before
    public void testSetUp() {
        MatchIdentifier mId = new MatchIdentifier("test1", "test2", "test3", "test4");
        uut=new ServerController(mId,new MatchNetMock());
        sentObj=false;
    }

    /**
     * Tests basic commands
     */
    @Test
    public void testBasics() {
        uut.kill();
        assertNotNull(uut.getInBus());
        uut.playerLeft("test1",true);
        uut.handleRequest("test2","test");
    }

    /**
     * Test for the player reconnection
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testReconnect(){
        uut.playerLeft("test2",false);
        uut.userReconnected("test2");
    }

    /**
     * Test for the round managing
     * @throws Exception reflection errors
     */
    @Test
    public void testNewRound() throws Exception{
        Method startM=ServerController.class.getDeclaredMethod("manageNewRound", String.class);
        startM.setAccessible(true);
        startM.invoke(uut,"test");
        assertTrue(sentObj);

        Field roundF=ServerController.class.getDeclaredField("round");
        roundF.setAccessible(true);
        Round r=(Round)roundF.get(uut);
        for(int i=0;i<8;i++){
            r.prepareNextRound();
        }
        /*uut.run();
        startM.invoke(uut,"test");
        assertTrue(ended);*/
    }

    /**
     * Test for the turn managing
     * @throws Exception reflection error
     */
    @Test
    public void testTurn() throws Exception{
        Method startM=ServerController.class.getDeclaredMethod("startMatch");
        startM.setAccessible(true);
        startM.invoke(uut);
        assertTrue(sentObj);
        sentObj=false;
        uut.newTurn();
        assertTrue(sentObj);
    }

    /**
     * Network mock
     */
    private class MatchNetMock implements MatchNetworkInterface {

        @Override
        public void end(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {

        }

        @Override
        public void sendReq(Serializable req, String dst) {

        }

        @Override
        public void sendObj(Serializable obj) {
            sentObj=true;
        }

        @Override
        public void abort() {

        }
    }
}