package it.polimi.se2018.controller.game.server.handlers;

import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.actions.UseToolCardMove;
import it.polimi.se2018.events.messages.InvalidMove;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

public class ToolCardsHandlerTest {
    private ToolCardsHandler uut;
    private BoardMock b;
    private Player p;
    private boolean notifiedFailure;

    @Before
    public void testSetUp() {
        b=new BoardMock();
        p=new Player("test",0);
        p.setFavourPoints(2);
        notifiedFailure=false;
    }

    @Test
    public void testDefault() {
        b.setId(-1);
        UseToolCardMove move=new UseToolCardMove("test",b.getTool(-1).getId());
        uut=new ToolCardsHandler(move,p,b,false,new MatchNetworkMock());
        uut.run();
        assertTrue(notifiedFailure);
    }


    private class MatchNetworkMock implements MatchNetworkInterface{

        @Override
        public void end(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {

        }

        @Override
        public void sendReq(Serializable req, String dst) {
            if(req.getClass().equals(InvalidMove.class)){
                notifiedFailure=true;
            }
        }

        @Override
        public void sendObj(Serializable obj) {

        }

        @Override
        public void abort() {

        }
    }

    private class BoardMock extends Board{
        private int id;

        void setId(int id){
            this.id=id;
        }

        @Override
        public ToolCard getTool(int toolPosition) {
            return new ToolMock(id);
        }
    }

    private class ToolMock extends ToolCard{
        private int id;
        ToolMock(int id) {
            super();
            this.id=id;
        }


        @Override
        public boolean isUsable(Player player, boolean firstTurn) {
            return true;
        }

        @Override
        public int getId() {
            return id;
        }
    }
}