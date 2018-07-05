package it.polimi.se2018.controller.game.server.handlers;

import it.polimi.se2018.controller.network.server.MatchNetworkInterface;

import it.polimi.se2018.events.actions.*;
import it.polimi.se2018.events.messages.InvalidMove;
import it.polimi.se2018.model.Board;

import it.polimi.se2018.model.ColorModel;
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
import java.util.ArrayList;
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
    private PlayerMove response;

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

    }

    private class ReserveMock extends Reserve
    {
        private final Die d1 = new Die(ColorModel.RED);
        private Die d2 = new Die(ColorModel.BLUE);

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


    private class HandlerMock extends ToolCardsHandler
    {

        private HandlerMock(UseToolCardMove move, Player player, Board board, boolean firstTurn, MatchNetworkInterface networkInterface, RandomDice randomDice)
        {
            super(move,player,board,firstTurn,networkInterface,randomDice);
        }

        @Override
        protected boolean waitUpdate() {
            update(bus, response);
            return true;
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
        uut = new HandlerMock(new UseToolCardMove("test",20),p,b,true,networkMock, new RandomDice());
        b.setId(20);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.setCardRights(true,true);
        uut = new HandlerMock(new UseToolCardMove("test",20),p,b,true,networkMock, new RandomDice());
        response = new DieFromReserve("player",1);
        uut.run();

    }

    @Test
    public void testEglomiseBrush()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",21),p,b,true,networkMock, new RandomDice());
        b.setId(21);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.setCardRights(true,true);
        p.getGrid().setDie(0,0,new Die(ColorModel.YELLOW));
        uut = new HandlerMock(new UseToolCardMove("test",21),p,b,true,networkMock, new RandomDice());
        response = new DieFromGrid("player",0,0);
        uut.run();
    }

    @Test
    public void testCopperFoilBurnisher()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",22),p,b,true,networkMock, new RandomDice());
        b.setId(22);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.getGrid().setDie(0,0,new Die(ColorModel.YELLOW));
        p.setCardRights(true,true);
        uut = new HandlerMock(new UseToolCardMove("test",22),p,b,true,networkMock, new RandomDice());
        response = new DieFromGrid("player",0,0);
        uut.run();
    }

    @Test
    public void testLathekin()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",23),p,b,true,networkMock, new RandomDice());
        b.setId(23);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.getGrid().setDie(0,0,new Die(ColorModel.YELLOW));
        p.getGrid().setDie(1,1,new Die(ColorModel.YELLOW));
        p.setCardRights(true,true);
        uut = new HandlerMock(new UseToolCardMove("test",23),p,b,true,networkMock, new RandomDice());
        response = new DieFromGrid("player",0,0);
        uut.run();
    }

    @Test
    public void testLensCutter()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",24),p,b,true,networkMock, new RandomDice());
        b.setId(24);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        b.getRoundTrack().addAll(new ArrayList<>());
        p.setCardRights(true,true);
        uut = new HandlerMock(new UseToolCardMove("test",24),p,b,true,networkMock, new RandomDice());
        response = new DieFromReserve("player",1);
        uut.run();
    }

    @Test
    public void testFluxBrush()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",25),p,b,true,networkMock, new RandomDice());
        b.setId(25);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.setCardRights(true,true);
        uut = new HandlerMock(new UseToolCardMove("test",25),p,b,true,networkMock, new RandomDice());
        response = new DieFromReserve("player",1);
        uut.run();

        RandomDice test = new RandomDice();
        test.setRollDieIndex(1);
        test.setRollDie(new Die(ColorModel.RED));

        uut = new HandlerMock(new UseToolCardMove("test",25),p,b,true,networkMock, test);
        response = new DieSet("player",0,0);
        uut.run();
    }

    @Test
    public void testGlazingHammer()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",26),p,b,true,networkMock, new RandomDice());
        b.setId(26);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.setCardRights(true,true);
        uut = new HandlerMock(new UseToolCardMove("test",26),p,b,false,networkMock, new RandomDice());
        uut.run();
    }

    @Test
    public void testCorkBackedStraightedge()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",28),p,b,true,networkMock, new RandomDice());
        b.setId(28);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.getGrid().setDie(1,1,new Die(ColorModel.YELLOW));
        p.setCardRights(true,true);
        uut = new HandlerMock(new UseToolCardMove("test",28),p,b,true,networkMock, new RandomDice());
        response = new DieFromGrid("player",0,0);
        uut.run();

    }

    @Test
    public void testRunningPliers()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",27),p,b,true,networkMock, new RandomDice());
        b.setId(27);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.setCardRights(true,true);
        p.setPlacementRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",27),p,b,true,networkMock, new RandomDice());
        response = new DieFromReserve("player",1);
        uut.run();

    }
    @Test
    public void testGrindingStone()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",29),p,b,true,networkMock, new RandomDice());
        b.setId(29);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.setCardRights(true,true);
        uut = new HandlerMock(new UseToolCardMove("test",29),p,b,true,networkMock, new RandomDice());
        response = new DieFromReserve("player",1);
        uut.run();

    }

    @Test
    public void testFluxRemover()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",30),p,b,true,networkMock, new RandomDice());
        b.setId(30);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.setCardRights(true,true);
        uut = new HandlerMock(new UseToolCardMove("test",30),p,b,true,networkMock, new RandomDice());
        response = new DieFromReserve("player",1);
        uut.run();

        RandomDice test = new RandomDice();
        test.setBagDie(1);

        uut = new HandlerMock(new UseToolCardMove("test",30),p,b,true,networkMock, test);
        response = new DieSet("player",0,0);
        uut.run();
    }

    @Test
    public void testTapWheel()
    {
        p.setCardRights(true,false);
        uut = new HandlerMock(new UseToolCardMove("test",31),p,b,true,networkMock, new RandomDice());
        b.setId(31);
        bus.addObserver(uut);
        uut.run();
        assertTrue(notifiedFailure);

        p.setCardRights(true,true);
        uut = new HandlerMock(new UseToolCardMove("test",31),p,b,true,networkMock, new RandomDice());
        response = new DieFromRoundTrack("player",1,0);
        uut.run();

    }

}