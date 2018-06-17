package it.polimi.se2018.events.messages;

import it.polimi.se2018.controller.game.client.ClientController;
import it.polimi.se2018.events.actions.DieFromGrid;
import it.polimi.se2018.events.actions.PassTurn;
import it.polimi.se2018.events.actions.PlayerMove;
import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.Pattern;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ActiveObjectives;
import it.polimi.se2018.model.cards.ActiveTools;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.model.dice.Reserve;
import it.polimi.se2018.model.dice.RoundTracker;
import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.view.app.App;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ServerMessageHandlerImplTest {
    private ServerMessageHandlerImpl uut;
    private CardView cV;
    private MockController cC;
    private AppMock app;
    private List<PlayerView> list;
    private ReserveView rV;
    private RoundTrackerView rT;
    private PassTurn move;

    private String setResult;
    private String askResult;
    private String selectResult;

    @Before
    public void testSetUp() {
        move=new PassTurn("test");
        app=new AppMock();
        cC=new MockController(app);
        list=new ArrayList<>();
        list.add(new PlayerView("test",0));
        cV=new CardView(null,null,null);
        rV=new ReserveView(new IntColorPair[9]);
        rT=new RoundTrackerView(0,new IntColorPair[5][5]);
        uut=new ServerMessageHandlerImpl(cC,app,list,rV,rT,cV);
        setResult=null;
        askResult=null;
        selectResult=null;
    }

    @Test
    public void testCardInfo() {
        ActiveTools aT=new ActiveTools();
        ActiveObjectives aO=new ActiveObjectives();
        CardInfo cInfo=new CardInfo(aT,aO);
        uut.handle(cInfo);
        assertNotNull(cV.getToolCards());
        assertNotNull(cV.getPublicObjectiveCards());
    }

    @Test
    public void testInvalidMove() {
        InvalidMove iM=new InvalidMove(new DieFromGrid(null,0,0),"error",false);
        uut.handle(iM);
        assertNull(setResult);

        iM=new InvalidMove(new PassTurn("test"),"error",false);
        uut.handle(iM);
        assertEquals("tool",setResult);

        iM=new InvalidMove(new PassTurn("test"),"error",true);
        uut.handle(iM);
        assertEquals("die",setResult);
    }

    @Test
    public void testSetHisDie() {
        SetThisDie sD=new SetThisDie(new IntColorPair(1,ColorModel.RED));
        uut.handle(sD);
        assertEquals("dieGrid",setResult);
    }

    @Test
    public void testConfirmMove() {
        ConfirmMove cM=new ConfirmMove(new DieFromGrid(null,0,0),false);
        uut.handle(cM);
        assertNull(setResult);

        cM=new ConfirmMove(new PassTurn("test"),false);
        uut.handle(cM);
        assertEquals("tool",setResult);

        cM=new ConfirmMove(new PassTurn("test"),true);
        uut.handle(cM);
        assertEquals("die",setResult);
    }

    @Test
    public void testMatchStart() {
        MatchStart mS=new MatchStart(true);
        uut.handle(mS);

        assertEquals("pattern-false",askResult);
        assertTrue(app.init);
    }

    @Test
    public void testPatternSelect() {
        IntColorPair[][] array=new IntColorPair[4][5];
        Pattern p=new Pattern(array,"test",5);
        PatternSelect pS=new PatternSelect(p);

        uut.handle(pS);
        assertTrue(cC.addedP);
    }

    @Test
    public void testPlayerStatus() {
        PlayerStatus pS=new PlayerStatus(new Player("test",0),true);
        uut.handle(pS);

        assertNotNull(list.get(0).getPlayerGrid());
    }

    @Test
    public void testPrivateObjectiveStatus() {
        PrivateObjectiveStatus pO=new PrivateObjectiveStatus(new PrivateObjectiveCard(ColorModel.RED));
        uut.handle(pO);

        assertNotNull(cV.getPrivateObjectiveCard());
    }

    @Test
    public void testCardExecutionError() {
        CardExecutionError cE=new CardExecutionError("error");
        uut.handle(cE);
        assertEquals("error",app.errorShown);
    }

    @Test
    public void testSetDie() {
        SetDie sD=new SetDie(0);
        uut.handle(sD);
        assertEquals("dieGrid",setResult);
    }

    @Test
    public void testAskDieByColor() {
        AskDieByColor aC=new AskDieByColor(ColorModel.RED);
        uut.handle(aC);
        assertEquals("gridColor",selectResult);
    }

    @Test
    public void testAskFace() {
        AskNewFace aF=new AskNewFace(0);
        uut.handle(aF);
        assertEquals("face",selectResult);
    }

    @Test
    public void testAskRoundTrack() {
        AskDieFromRoundTrack aR=new AskDieFromRoundTrack();
        uut.handle(aR);
        assertEquals("round",selectResult);
    }

    @Test
    public void testAskDieGrid() {
        AskDieFromGrid aD=new AskDieFromGrid();
        uut.handle(aD);
        assertEquals("dieGrid",selectResult);
    }

    @Test
    public void testAskValue() {
        AskNewValue aV=new AskNewValue(1,2);
        uut.handle(aV);
        assertEquals("value",selectResult);
    }

    @Test
    public void testReserveStatus(){
        ReserveStatus rS=new ReserveStatus(new Reserve());
        uut.handle(rS);

        assertNotNull(rV.getReserve());
    }

    @Test
    public void testRoundTrackStatus(){
        RoundTrackStatus rTS=new RoundTrackStatus(new RoundTracker());
        uut.handle(rTS);

        assertNotNull(rT.getRoundTracker());
    }

    @Test
    public void testAskDieReserve() {
        AskDieFromReserve aR=new AskDieFromReserve();
        uut.handle(aR);
        assertEquals("dieReserve",selectResult);
    }

    @Test
    public void testTurnStart() {
        TurnStart tS=new TurnStart("old","test");
        uut.handle(tS);
        assertEquals("started",app.status);

        tS=new TurnStart("test","NotTest");
        uut.handle(tS);
        assertEquals("passedOk",app.status);

        tS=new TurnStart("NotTest","NotTest");
        uut.handle(tS);
        assertEquals("updatedTurn",app.status);
    }

    private class MockController extends ClientController{
        boolean addedP=false;
        MockController(App app) {
            super(app);
        }

        @Override
        public PlayerMove getLastAction() {
            return move;
        }

        @Override
        public void addPatternView(PatternSelect patternSelect) {
            addedP=true;
        }

        @Override
        public String getLocalPlayer() {
            return "test";
        }
    }

    private class AppMock extends App{
        private boolean animation=true;
        boolean init=false;
        String errorShown=null;
        String status=null;

        AppMock() {
            super(null,null,null);
        }

        @Override
        public void animation(boolean enable) {
            animation=enable;
        }

        @Override
        public void startLogin(boolean displayWelcome) {

        }

        @Override
        public void loginResult(boolean success, String error) {

        }

        @Override
        public void changeLayerResult(boolean successRMI) {

        }

        @Override
        public void leaveMatchResult(boolean success) {

        }

        @Override
        public void logoutResult(boolean success) {

        }

        @Override
        public void createLobby() {

        }

        @Override
        public void askPattern(PatternView pattern1, PatternView pattern2, PatternView pattern3, PatternView pattern4, CardView cardView) {
            askResult="pattern-"+animation;
        }

        @Override
        public void initGame(List<PlayerView> players, ReserveView reserveView, RoundTrackerView roundTrackerView) {
            init=true;
        }

        @Override
        public void otherPlayerLeave(String playerName) {

        }

        @Override
        public void otherPlayerReconnection(String playerName) {

        }

        @Override
        public void startTurn() {
            status="started";
        }

        @Override
        public void setDieResult(boolean result, String errorString) {
            setResult="die";
        }

        @Override
        public void addUpdate(String playerName, int height, int width) {

        }

        @Override
        public void useToolCardResult(boolean result, String errorString) {
            setResult="tool";
        }

        @Override
        public void useToolCardUpdate(String playerName, int card) {

        }

        @Override
        public void passTurnResult(boolean result) {
            status="passedOk";
        }

        @Override
        public void passTurnUpdate(String playerName) {
            status="updatedTurn";
        }

        @Override
        public void gameEnd(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3) {

        }

        @Override
        public void abortMatch() {

        }

        @Override
        public void selectDieFromReserve() {
            selectResult="dieReserve";
        }

        @Override
        public void selectNewValueForDie(int low, int high) {
            selectResult="value";
        }

        @Override
        public void updateReserve() {

        }

        @Override
        public void selectDieFromGrid() {
            selectResult="dieGrid";
        }

        @Override
        public void setDieOnGrid(IntColorPair die) {
            setResult="dieGrid";
        }

        @Override
        public void selectDieFromRoundTracker() {
            selectResult="round";
        }

        @Override
        public void selectFace(IntColorPair die) {
            selectResult="face";
        }

        @Override
        public void selectDieFromGridByColor(ColorModel color) {
            selectResult="gridColor";
        }

        @Override
        public void showError(String error) {
            errorShown=error;
        }
    }
}