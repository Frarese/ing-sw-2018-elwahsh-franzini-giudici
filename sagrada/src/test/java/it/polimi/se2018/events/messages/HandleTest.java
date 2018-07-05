package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;
import it.polimi.se2018.events.actions.UseToolCardMove;
import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.Pattern;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ActiveObjectives;
import it.polimi.se2018.model.cards.ActiveTools;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.model.dice.Reserve;
import it.polimi.se2018.model.dice.RoundTracker;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test for the message handler
 * @author Francesco Franzini
 */
public class HandleTest {
    private ServerMessageHandler hand;
    private ServerMessage mov;

    /**
     * Test initialization
     */
    @Before
    public void setUp() {
        hand=new ServerMessageHandlerMock();
    }

    /**
     * Test for message handling
     */
    @Test
    public void testCalls(){
        ActiveTools aT=new ActiveTools();
        ActiveObjectives aO=new ActiveObjectives();
        IntColorPair[][] array=new IntColorPair[4][5];
        Pattern p=new Pattern(array,"test",5);
        PrivateObjectiveCard pO=new PrivateObjectiveCard(ColorModel.RED);
        Reserve r=new Reserve();

        ServerMessage s=new CardInfo(aT,aO);
        s.visit(hand);
        assertEquals(s,mov);

        s=new InvalidMove(new UseToolCardMove("a",0),"error",false);
        s.visit(hand);
        assertEquals(s,mov);

        s=new MatchStart(false);
        s.visit(hand);
        assertEquals(s,mov);

        s=new PatternSelect(p);
        s.visit(hand);
        assertEquals(s,mov);

        s=new PlayerStatus(new Player("a",0),false);
        s.visit(hand);
        assertEquals(s,mov);

        s=new PrivateObjectiveStatus(pO);
        s.visit(hand);
        assertEquals(s,mov);

        s=new ReserveStatus(r);
        s.visit(hand);
        assertEquals(s,mov);

        s=new RoundTrackStatus(new RoundTracker());
        s.visit(hand);
        assertEquals(s,mov);

        s=new TurnStart("a","b");
        s.visit(hand);
        assertEquals(s,mov);

        s=new ReadyView("a");
        s.visit(hand);
        assertEquals(s,mov);

        s=new ConfirmMove(new UseToolCardMove("a",0),true);
        assertNotNull(((ConfirmMove) s).getMove());
        assertTrue(((ConfirmMove) s).isPlacement());
        s.visit(hand);
        assertEquals(s,mov);

        s=new AskDieByColor(ColorModel.RED);
        assertEquals(ColorModel.RED,((AskDieByColor) s).getColorModel());
        s.visit(hand);
        assertEquals(s,mov);

        s=new AskDieFromGrid();
        s.visit(hand);
        assertEquals(s,mov);

        s=new AskDieFromReserve();
        s.visit(hand);
        assertEquals(s,mov);

        s=new AskDieFromRoundTrack();
        s.visit(hand);
        assertEquals(s,mov);

        s=new AskNewFace(ColorModel.RED,2);
        s.visit(hand);
        assertEquals(s,mov);

        s=new AskNewValue(0,1);
        assertEquals(0,((AskNewValue) s).getVal1());
        assertEquals(1,((AskNewValue) s).getVal2());
        s.visit(hand);
        assertEquals(s,mov);

        s=new CardExecutionError("test");
        s.visit(hand);
        assertEquals(s,mov);

        s=new SetDie(0);
        assertEquals(0,((SetDie) s).getIndex());
        s.visit(hand);
        assertEquals(s,mov);
}

    /**
     * ServerMessageHandler mock
     */
    private class ServerMessageHandlerMock implements ServerMessageHandler{

        @Override
        public void handle(CardInfo move) {
            mov=move;
        }

        @Override
        public void handle(SetThisDie move) {
            mov = move;
        }

        @Override
        public void handle(InvalidMove move) {
            mov=move;
        }

        @Override
        public void handle(MatchStart move) {
            mov=move;
        }

        @Override
        public void handle(AskDieFromReserve move) {
            mov = move;
        }

        @Override
        public void handle(AskNewValue move) {
            mov = move;
        }

        @Override
        public void handle(SetDie move) {
            mov = move;
        }


        @Override
        public void handle(AskDieFromGrid move) {
            mov = move;
        }

        @Override
        public void handle(AskDieFromRoundTrack move) {
            mov = move;
        }

        @Override
        public void handle(AskDieByColor move) {
            mov = move;
        }

        @Override
        public void handle(AskNewFace move) {
            mov = move;
        }

        @Override
        public void handle(CardExecutionError move) {
            mov = move;
        }

        @Override
        public void handle(ConfirmMove move) {mov = move;}
        @Override
        public void handle(PatternSelect move) {
            mov=move;
        }

        @Override
        public void handle(PlayerStatus move) {
            mov=move;
        }

        @Override
        public void handle(PrivateObjectiveStatus move) {
            mov=move;
        }

        @Override
        public void handle(ReserveStatus move) {
            mov=move;
        }

        @Override
        public void handle(RoundTrackStatus move) {
            mov=move;
        }

        @Override
        public void handle(TurnStart move) {
            mov=move;
        }

        @Override
        public void handle(ReadyView move) {
            mov=move;
        }

        @Override
        public void handle(AskPlacements move) {mov=move;}
    }
}
