package it.polimi.se2018.controller.game.server;

import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

public class ServerControllerTest {
    private ServerController uut;

    @Before
    public void testSetUp() {
        MatchIdentifier mId = new MatchIdentifier("test1", "test2", "test3", "test4");
        uut=new ServerController(mId,new MatchNetMock());
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

    private class MatchNetMock implements MatchNetworkInterface {

        @Override
        public void end(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {

        }

        @Override
        public void sendReq(Serializable req, String dst) {

        }

        @Override
        public void sendObj(Serializable obj) {

        }

        @Override
        public void abort() {

        }
    }
}