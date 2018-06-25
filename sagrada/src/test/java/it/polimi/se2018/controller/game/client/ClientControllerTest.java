package it.polimi.se2018.controller.game.client;

import it.polimi.se2018.events.actions.UseToolCardMove;
import it.polimi.se2018.events.messages.PatternSelect;
import it.polimi.se2018.events.messages.ReadyView;
import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.Pattern;
import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.app.App;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClientControllerTest {
    private ClientController uut;
    private boolean aborted;
    private String notified;
    @Before
    public void testSetUp(){
        uut=new ClientController(new AppMock());
        aborted=false;
        notified="";
        uut.notifyCommDropped();//Calls cleanUp
    }

    @Test
    public void testStart() {
        MatchIdentifier mId=new MatchIdentifier("user","user2","user3","user4");
        uut.setMId(mId);
        uut.notifyMatchStart(mId);
        uut.notifyReconnect();
        uut.setLocalPlayer("player");
        assertEquals("player",uut.getLocalPlayer());

        uut.receiveObject("test");
        uut.receiveRequest("test");
        uut.receiveObject(new ReadyView("test"));
        uut.receiveRequest(new ReadyView("test"));

        uut.setLastAction(new UseToolCardMove("n",0));
        assertNotNull(uut.getLastAction());
    }

    @Test
    public void testAbort() {
        uut.abortMatch();
        assertTrue(aborted);
    }

    @Test
    public void testNotifies() {
        uut.notifyInvite(null);
        assertEquals("invite",notified);

        uut.notifyMatchEnd(0,0,0,0);
        assertEquals("end",notified);

        uut.notifyUserLeft("test");
        assertEquals("left",notified);

        uut.notifyCommDropped();
        assertEquals("logout",notified);

        uut.notifyUserReconnected(null);
        assertEquals("join",notified);
    }

    @Test
    public void testPattern() {
        IntColorPair[][] array=new IntColorPair[4][5];
        Pattern p=new Pattern(array,"test",5);
        uut.addPatternView(new PatternSelect(p));
        uut.addPatternView(new PatternSelect(p));
        uut.addPatternView(new PatternSelect(p));
        uut.addPatternView(new PatternSelect(p));
        assertEquals("asked",notified);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPushChat() {
        uut.pushChatMessage("test",MessageTypes.MATCH,"test1");
    }

    @Test
    public void testPush() {
        uut.pushLeaderboard(null);
        assertEquals("pushedLeaderboard",notified);

        uut.pushUserList(null);
        assertEquals("pushedList",notified);
    }

    private class AppMock extends App {

        AppMock() {
            super(null,null);
        }

        @Override
        public void animation(boolean enable) {

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
            notified="logout";
        }

        @Override
        public void createLobby() {

        }

        @Override
        public void askPattern(PatternView pattern1, PatternView pattern2, PatternView pattern3, PatternView pattern4, CardView cardView) {
            notified="asked";
        }

        @Override
        public void initGame(List<PlayerView> players, ReserveView reserveView, RoundTrackerView roundTrackerView) {

        }

        @Override
        public void otherPlayerLeave(String playerName) {
            notified="left";
        }

        @Override
        public void otherPlayerReconnection(String playerName) {
            notified="join";
        }

        @Override
        public void startTurn() {

        }

        @Override
        public void setDieResult(boolean result, String errorString) {

        }

        @Override
        public void addUpdate(String playerName, int height, int width) {

        }

        @Override
        public void useToolCardResult(boolean result, String errorString) {

        }

        @Override
        public void useToolCardUpdate(String playerName, int card) {

        }

        @Override
        public void passTurnResult(boolean result) {

        }

        @Override
        public void passTurnUpdate(String playerName) {

        }

        @Override
        public void gameEnd(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3) {
            notified="end";
        }

        @Override
        public void abortMatch() {
            aborted=true;
        }

        @Override
        public void selectDieFromReserve() {

        }

        @Override
        public void selectNewValueForDie(int low, int high) {

        }

        @Override
        public void updateReserve() {

        }

        @Override
        public void selectDieFromGrid() {

        }

        @Override
        public void setDieOnGrid(IntColorPair die) {

        }

        @Override
        public void selectDieFromRoundTracker() {

        }

        @Override
        public void selectFace(IntColorPair die) {

        }

        @Override
        public void selectDieFromGridByColor(ColorModel color) {

        }

        @Override
        public void showError(String error) {

        }

        @Override
        public void pullInvitate(MatchIdentifier invite) {
            notified="invite";
        }

        @Override
        public void pullLeaderBoard(List<ScoreEntry> leaderBoard) {
            notified="pushedLeaderboard";
        }

        @Override
        public void pullConnectedPlayers(List<ScoreEntry> players) {
            notified="pushedList";
        }
    }
}