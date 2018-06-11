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

public class DiePlacementHandlerTest {

    private Player player;
    private DiePlacementHandler test;
    private DiePlacementMove move;
    private Reserve reserve;
    private String result;

    private class TestNetwork implements MatchNetworkInterface
    {
        @Override
        public void end(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {

        }

        @Override
        public void sendReq(Serializable req, String dst) {
            result = "Fail";
        }

        @Override
        public void sendObj(Serializable obj) {

            result = "Success";
        }

        @Override
        public void abort() {

        }
    }

    @Before
    public void testInit()
    {
        player = new Player("Gigino",0);
        player.setPattern(new PatternCard("resources" + File.separator + "patterncard10.xml").getBackSide());
        reserve = new Reserve();
    }


    @Test
    public void testRunFail()
    {
        move = new DiePlacementMove(0,0,0,"Gigino",true,true,true);
        reserve.add(new Die(ColorModel.RED));
        new Thread(new DiePlacementHandler(player,move,reserve,true,new TestNetwork())).start();

    }

    @Test
    public void testRunSuccess()
    {
        move = new DiePlacementMove(0,0,0,"Gigino",true,true,true);
        reserve.add(new Die(ColorModel.RED));
        player.setPlacementRights(true,true);
        new Thread(new DiePlacementHandler(player,move,reserve,true,new TestNetwork())).start();

    }

}