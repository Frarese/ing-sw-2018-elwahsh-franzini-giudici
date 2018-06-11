package it.polimi.se2018.controller.game.client;

import it.polimi.se2018.events.messages.ReadyView;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientControllerTest {
    private ClientController uut;

    @Before
    public void testSetUp(){
        uut=new ClientController(null);
    }

    @Test
    public void testStart() {
        MatchIdentifier mId=new MatchIdentifier("user","user2","user3","user4");
        uut.setMId(mId);
        uut.notifyMatchStart();
        uut.notifyReconnect();
        uut.setLocalPlayer("player");
        assertEquals("player",uut.getLocalPlayer());

        uut.receiveObject("test");
        uut.receiveRequest("test");
        uut.receiveObject(new ReadyView("test"));
        uut.receiveRequest(new ReadyView("test"));
    }
}