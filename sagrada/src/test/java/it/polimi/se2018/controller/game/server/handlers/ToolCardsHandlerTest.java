package it.polimi.se2018.controller.game.server.handlers;

import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.Event;

import it.polimi.se2018.events.actions.UseToolCardMove;
import it.polimi.se2018.events.messages.InvalidMove;
import it.polimi.se2018.model.Board;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.PatternCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Reserve;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Observable;


import static org.junit.Assert.*;

public class ToolCardsHandlerTest {
    private ToolCardsHandler uut;
    private BoardMock b;
    private Player p;
    private boolean notifiedFailure;
    private final BusMock bus = new BusMock();
    private final MatchNetworkMock networkMock = new MatchNetworkMock();
    private boolean sentObj;

    @Before
    public void testSetUp() {
        b=new BoardMock();
        p=new Player("test",0);
        p.setPattern(new PatternCard("resources" + File.separator + "patterncard01.xml").getBackSide());
        p.initFavourPoints();
        p.setFavourPoints(2);
        notifiedFailure=false;
        sentObj=false;
    }

    @Test
    public void testDefault() {
        b.setId(-1);
        UseToolCardMove move=new UseToolCardMove("test",b.getTool(-1).getId());
        uut=new ToolCardsHandler(move,p,b,false,new MatchNetworkMock(), new RandomDice());
        uut.run();
        assertTrue(notifiedFailure);
    }

    @Test
    public void testUpdate() throws Exception{
        uut=new ToolCardsHandler(null,p,b,false,new MatchNetworkMock(), new RandomDice());
        Method m=ToolCardsHandler.class.getDeclaredMethod("updateGameState");
        m.setAccessible(true);

        m.invoke(uut);
        assertTrue(sentObj);
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
            sentObj=true;
        }

        @Override
        public void abort() {

        }
    }

    private class BusMock extends Observable
    {
        private void push (Event event)
        {
            setChanged();
            notifyObservers(event);
        }
    }

    private class ReserveMock extends Reserve
    {
        private Die d1;
        private Die d2;

        @Override
        public Die get(int diePosition) {
            if(diePosition == 1) return d1;
            else return d2;
        }

        @Override
        public void add(Die d) {
            d2 = d;
        }

        @Override
        public Die popDie(int diePosition) {
            return d1;
        }

        @Override
        public void rollReserve() {
            d1.roll();
            d2.roll();
        }

    }

    private class BoardMock extends Board{
        private int id;
        private final ReserveMock reserveMock = new ReserveMock();

        void setId(int id){
            this.id=id;
        }

        @Override
        public ToolCard getTool(int toolPosition) {
            return new ToolMock(id);
        }

        @Override
        public Reserve getReserve()
        {
            return reserveMock;
        }
    }

    private class ToolMock extends ToolCard{
        private final int id;
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


    @Test
    public void testGrozingPliers()
    {
        p.setCardRights(true,false);
        uut = new ToolCardsHandler(new UseToolCardMove("test",20),p,b,true,networkMock, new RandomDice());
        b.setId(20);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);
    }

    @Test
    public void testEglomiseBrush()
    {
        p.setCardRights(true,false);
        uut = new ToolCardsHandler(new UseToolCardMove("test",21),p,b,true,networkMock, new RandomDice());
        b.setId(21);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);
    }

    @Test
    public void testCopperFoilBurnisher()
    {
        p.setCardRights(true,false);
        uut = new ToolCardsHandler(new UseToolCardMove("test",22),p,b,true,networkMock, new RandomDice());
        b.setId(22);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);
    }

    @Test
    public void testLathekin()
    {
        p.setCardRights(true,false);
        uut = new ToolCardsHandler(new UseToolCardMove("test",23),p,b,true,networkMock, new RandomDice());
        b.setId(23);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);
    }

    @Test
    public void testLensCutter()
    {
        p.setCardRights(true,false);
        uut = new ToolCardsHandler(new UseToolCardMove("test",24),p,b,true,networkMock, new RandomDice());
        b.setId(24);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);
    }

    @Test
    public void testGlazingHammer()
    {
        p.setCardRights(true,false);
        uut = new ToolCardsHandler(new UseToolCardMove("test",26),p,b,true,networkMock, new RandomDice());
        b.setId(26);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);
    }

    @Test
    public void testCorkBackedStraightedge()
    {
        p.setCardRights(true,false);
        uut = new ToolCardsHandler(new UseToolCardMove("test",28),p,b,true,networkMock, new RandomDice());
        b.setId(28);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);
    }

    @Test
    public void testGrindingStone()
    {
        p.setCardRights(true,false);
        uut = new ToolCardsHandler(new UseToolCardMove("test",29),p,b,true,networkMock, new RandomDice());
        b.setId(29);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);
    }

}