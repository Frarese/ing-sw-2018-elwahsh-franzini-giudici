package it.polimi.se2018.controller.game.server;

import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServerControllerFactoryTest {

    @Test
    public void testMethod(){
        MatchIdentifier mId=new MatchIdentifier("a","b",null,null);
        assertEquals(ServerController.class,new ServerControllerFactory().buildMatch(mId,null).getClass());
    }
}