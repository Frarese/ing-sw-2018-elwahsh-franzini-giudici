package it.polimi.se2018.controller.game.server.handlers;

import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.actions.DiePlacementMove;
import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.PatternCard;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Reserve;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.Serializable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for the DiePlacementHandler class
 * @author Alì El wahsh
 */
public class DiePlacementHandlerTest {

    private Player player;
    private Reserve reserve;
    private boolean result;

    /**
     * Test initialization
     */
    @Before
    public void testInit()
    {
        player = new Player("Gigino",0);
        player.setPattern(new PatternCard("resources" + File.separator + "patterncard10.xml").getBackSide());
        reserve = new Reserve();
        result=false;
    }


    /**
     * Test fro a single execution of the handler
     */
    @Test
    public void testRun()
    {
        DiePlacementMove move = new DiePlacementMove(0, 0, 0, "Gigino", true, true, true);
        reserve.add(new Die(ColorModel.RED));
        DiePlacementHandler uut = new DiePlacementHandler(player, move, reserve, true, new TestNetwork());
        uut.run();
        assertTrue(result);
        result=false;
        uut.run();
        assertFalse(result);
        player.setPlacementRights(true,true);
        uut.run();
        assertFalse(result);
    }

    /**
     * Network mock for the test
     */
    private class TestNetwork implements MatchNetworkInterface
    {
        @Override
        public void end(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {

        }

        @Override
        public void sendReq(Serializable req, String dst) {
        }

        @Override
        public void sendObj(Serializable obj) {
            result = true;
        }

        @Override
        public void abort() {

        }
    }
}