package it.polimi.se2018.controller.game.server;

import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ServerControllerTest {
    private ServerController uut;
    private boolean sentObj;
    private boolean ended;
    @Before
    public void testSetUp() {
        MatchIdentifier mId = new MatchIdentifier("test1", "test2", "test3", "test4");
        uut=new ServerController(mId,new MatchNetMock());
        sentObj=false;
        ended=false;
    }

    @Test
    public void testBasics() {
        uut.kill();
        assertNotNull(uut.getInBus());
        uut.playerLeft("test1",true);
        uut.handleRequest("test2","test");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testReconnect(){
        uut.playerLeft("test2",false);
        uut.userReconnected("test2");
    }


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
        startM.invoke(uut,"test");
        assertTrue(ended);
    }

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

    private class MatchNetMock implements MatchNetworkInterface {

        @Override
        public void end(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {
            ended=true;
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