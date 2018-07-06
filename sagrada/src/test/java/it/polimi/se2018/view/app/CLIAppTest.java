package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.observer.PlayerState;
import it.polimi.se2018.view.tools.GridViewCreator;
import it.polimi.se2018.view.tools.cli.creators.CLIGridViewCreator;
import it.polimi.se2018.view.tools.cli.creators.CLIReserveViewCreator;
import it.polimi.se2018.view.tools.cli.ui.CLIPrinter;
import it.polimi.se2018.view.tools.cli.ui.CLIReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for CLIApp class
 *
 * @author Mathyas Giudici
 */

public class CLIAppTest {

    private final String enter = System.lineSeparator();

    private ByteArrayOutputStream savedStream;

    private CLIApp app;

    /**
     * Creates the object to test with viewAction and viewToolCardAction mock's object
     * @param fakeViewAction fake view action to use
     */
    private void testSetApp(ViewActions fakeViewAction) {
        this.app = new CLIAppMock(fakeViewAction, new FakeViewToolActions());
        this.app.animation(true);
    }

    private void testMenuHelper() {
        this.app.loginResult(true, null);
    }

    /**
     * Prepares System.out to be saved in a stream
     */
    @Before
    public void testInit() {
        savedStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(savedStream));
    }

    /**
     * Restore System.in and System.out
     */
    @After
    public void testCloseOperation() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    /**
     * Checks that animation is initialized true
     */
    @Test
    public void testAnimation() {
        testSetApp(new FakeViewAction());
        assertTrue(this.app.animationEnable);
    }

    /**
     * Simulates login with Socket connection
     */
    @Test
    public void testStartLoginDisplayWelcome() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.startLogin(true);
    }

    /**
     * Simulates login with RMI connection
     */
    @Test
    public void testStartLogin() {
        String message = "n" + enter + "Test" + enter + "test" + enter + "test" + enter + "y" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.app.startLogin(false);
    }

    /**
     * Checks notify of login success
     */
    @Test
    public void testLoginResult() {
        testSetApp(new FakeViewAction());
        this.app.loginResult(true, null);

        assertEquals("Login riuscito con successo!" + enter, this.savedStream.toString());
    }

    /**
     * Checks notify of login failure
     */
    @Test
    public void testLoginResultFail() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(false, null);
        assertEquals("Login NON riuscito! Riprova.", this.savedStream.toString().split(enter)[0]);
    }

    /**
     * Checks notify of change layer request to RMI connection
     */
    @Test
    public void testChangeLayerResultRMI() {
        String message = "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.changeLayerResult(true);

        assertEquals("L'attuale layer è RMI.", this.savedStream.toString().split(enter)[1]);
    }

    /**
     * Checks notify of change layer request to Socket connection
     */
    @Test
    public void testChangeLayerResultSocket() {
        String message = "2" + enter + "n" + enter + "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.changeLayerResult(false);

        assertEquals("L'attuale layer è Socket.", this.savedStream.toString().split(enter)[1]);
    }

    /**
     * Checks notify of leave match success
     */
    @Test
    public void testLeaveMatchResult() {
        testSetApp(new FakeViewAction2());

        this.app.leaveMatchResult(true);

        assertEquals("Match lasciato con successo." + enter, this.savedStream.toString());
    }

    /**
     * Checks notify of leave match failure
     */
    @Test
    public void testLeaveMatchResultFail() {
        String message = 1 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.leaveMatchResult(false);

        assertEquals("Non sono riuscito a disconnettermi dal match.", this.savedStream.toString().split(enter)[1]);
    }

    /**
     * Checks notify of logout success
     */
    @Test
    public void testLogoutResult() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.logoutResult(true);

        assertEquals("Logout riuscito con successo!", this.savedStream.toString().split(enter)[0]);
    }

    /**
     * Checks notify of logout failure
     */
    @Test
    public void testLogoutResultFail() {
        String message = 1 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.logoutResult(false);

        assertEquals("Logout NON riuscito!", this.savedStream.toString().split(enter)[1]);
    }

    /**
     * Checks notify of logout success
     */
    @Test
    public void testCreateLobby() {
        String message = 8 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();
        this.app.createLobby();
    }

    /**
     * Test connectedPlayers' list receiving
     */
    @Test
    public void testPullConnectedPlayers() {
        ArrayList<ScoreEntry> arrayList = new ArrayList<>();
        arrayList.add(0, new ScoreEntry("Test", 0, 0));
        arrayList.add(1, new ScoreEntry("Test1", 1, 1));

        testSetApp(new FakeViewAction());

        this.app.pullConnectedPlayers(arrayList);

        assertArrayEquals(arrayList.toArray(), this.app.getConnectedUsers().toArray());
    }

    /**
     * Test leaderBoard's list receiving
     */
    @Test
    public void testPullLeaderBoard() {
        ArrayList<ScoreEntry> arrayList = new ArrayList<>();
        arrayList.add(0, new ScoreEntry("Test", 0, 0));
        arrayList.add(1, new ScoreEntry("Test1", 1, 1));

        testSetApp(new FakeViewAction());

        this.app.pullLeaderBoard(arrayList);

        assertArrayEquals(arrayList.toArray(), this.app.getLeaderBoard().toArray());
    }

    /**
     * Test invites' list receiving
     */
    @Test
    public void testPullInvitate() {
        MatchIdentifier matchIdentifier1 = new MatchIdentifier("TestP0", "TestP1", "TestP2", "TestP3");
        MatchIdentifier matchIdentifier2 = new MatchIdentifier("TestP0", "Test", null, null);

        testSetApp(new FakeViewAction());

        this.app.pullInvitate(matchIdentifier1);
        this.app.pullInvitate(matchIdentifier2);


        assertEquals(matchIdentifier1.toString(), this.app.getInvites().get(0).toString());
        assertEquals(matchIdentifier2.toString(), this.app.getInvites().get(1).toString());
    }

    /**
     * Simulates first pattern selection
     */
    @Test
    public void testAskPattern1() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        IntColorPair[][] pattern = new IntColorPair[1][2];
        pattern[0][0] = new IntColorPair(1, ColorModel.RED);
        pattern[0][1] = new IntColorPair(1, ColorModel.RED);
        PatternView pattern1 = new PatternView("Test1", 1, pattern);
        PatternView pattern2 = new PatternView("Test2", 2, pattern);
        PatternView pattern3 = new PatternView("Test3", 3, pattern);
        PatternView pattern4 = new PatternView("Test4", 4, pattern);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(10, 1));
        cards.add(new SingleCardView(12, 1));

        CardView cardView = new CardView(new SingleCardView(1, 1), cards, cards);

        this.app.askPattern(pattern1, pattern2, pattern3, pattern4, cardView);

        assertEquals(1, this.app.getCardViewCreator().getPrivateObjectiveCard().cardID);
        assertArrayEquals(cards.toArray(), this.app.getCardViewCreator().getPublicObjectiveCards().toArray());
        assertArrayEquals(cards.toArray(), this.app.getCardViewCreator().getToolCards().toArray());

    }

    /**
     * Simulates second pattern selection
     */
    @Test
    public void testAskPattern2() {
        String message = "2" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        IntColorPair[][] pattern = new IntColorPair[1][2];
        pattern[0][0] = new IntColorPair(1, ColorModel.RED);
        pattern[0][1] = new IntColorPair(1, ColorModel.RED);
        PatternView pattern1 = new PatternView("Test1", 1, pattern);
        PatternView pattern2 = new PatternView("Test2", 2, pattern);
        PatternView pattern3 = new PatternView("Test3", 3, pattern);
        PatternView pattern4 = new PatternView("Test4", 4, pattern);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(10, 1));
        cards.add(new SingleCardView(12, 1));

        CardView cardView = new CardView(new SingleCardView(1, 1), cards, cards);

        this.app.askPattern(pattern1, pattern2, pattern3, pattern4, cardView);

        assertEquals(1, this.app.getCardViewCreator().getPrivateObjectiveCard().cardID);
        assertArrayEquals(cards.toArray(), this.app.getCardViewCreator().getPublicObjectiveCards().toArray());
        assertArrayEquals(cards.toArray(), this.app.getCardViewCreator().getToolCards().toArray());
    }

    /**
     * Simulates third pattern selection
     */
    @Test
    public void testAskPattern3() {
        String message = "3" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        IntColorPair[][] pattern = new IntColorPair[1][2];
        pattern[0][0] = new IntColorPair(1, ColorModel.RED);
        pattern[0][1] = new IntColorPair(1, ColorModel.RED);
        PatternView pattern1 = new PatternView("Test1", 1, pattern);
        PatternView pattern2 = new PatternView("Test2", 2, pattern);
        PatternView pattern3 = new PatternView("Test3", 3, pattern);
        PatternView pattern4 = new PatternView("Test4", 4, pattern);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(10, 1));
        cards.add(new SingleCardView(12, 1));

        CardView cardView = new CardView(new SingleCardView(1, 1), cards, cards);

        this.app.askPattern(pattern1, pattern2, pattern3, pattern4, cardView);

        assertEquals(1, this.app.getCardViewCreator().getPrivateObjectiveCard().cardID);
        assertArrayEquals(cards.toArray(), this.app.getCardViewCreator().getPublicObjectiveCards().toArray());
        assertArrayEquals(cards.toArray(), this.app.getCardViewCreator().getToolCards().toArray());

    }

    /**
     * Simulates fourth pattern selection
     */
    @Test
    public void testAskPattern4() {
        String message = "4" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        IntColorPair[][] pattern = new IntColorPair[1][2];
        pattern[0][0] = new IntColorPair(1, ColorModel.RED);
        pattern[0][1] = new IntColorPair(1, ColorModel.RED);
        PatternView pattern1 = new PatternView("Test1", 1, pattern);
        PatternView pattern2 = new PatternView("Test2", 2, pattern);
        PatternView pattern3 = new PatternView("Test3", 3, pattern);
        PatternView pattern4 = new PatternView("Test4", 4, pattern);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(10, 1));
        cards.add(new SingleCardView(12, 1));

        CardView cardView = new CardView(new SingleCardView(1, 1), cards, cards);

        this.app.askPattern(pattern1, pattern2, pattern3, pattern4, cardView);

        assertEquals(1, this.app.getCardViewCreator().getPrivateObjectiveCard().cardID);
        assertArrayEquals(cards.toArray(), this.app.getCardViewCreator().getPublicObjectiveCards().toArray());
        assertArrayEquals(cards.toArray(), this.app.getCardViewCreator().getToolCards().toArray());
    }

    /**
     * Checks correct init game operations
     */
    @Test
    public void testInitGame() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter + "10" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        List<PlayerView> players = new ArrayList<>();
        PlayerView me = new PlayerView("Test", 0);
        PlayerView other = new PlayerView("OtherPlayerTest", 1);
        players.add(me);
        players.add(other);


        IntColorPair[][] fakeRoundt = new IntColorPair[1][1];
        fakeRoundt[0][0] = new IntColorPair(1, ColorModel.RED);
        RoundTrackerView roundTracker = new RoundTrackerView(0, fakeRoundt);

        IntColorPair[] fakeReserve = new IntColorPair[1];
        fakeReserve[0] = new IntColorPair(1, ColorModel.RED);
        ReserveView reserveView = new ReserveView(fakeReserve);

        this.app.startLogin(false);
        this.app.getGridViewCreator().setGridPattern(new IntColorPair[1][1]);
        this.app.getRoundTrackerViewCreator().setRound(0);
        this.app.getRoundTrackerViewCreator().setRoundTracker(fakeRoundt);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(10, 1));
        cards.add(new SingleCardView(12, 1));
        this.app.getCardViewCreator().setPrivateObjectiveCard(new SingleCardView(1, 0));
        this.app.getCardViewCreator().setPublicObjectiveCards(cards);
        this.app.getCardViewCreator().setToolCards(cards);

        this.app.initGame(players, reserveView, roundTracker);
        this.app.startTurn();

        assertEquals(me.getPlayerName(), this.app.getOwnerPlayerName());
        assertNotNull(this.app.getCardViewCreator());
        assertNotNull(this.app.getRoundTrackerViewCreator());
        assertNotNull(this.app.getReserveViewCreator());
        assertArrayEquals(fakeRoundt, this.app.getRoundTrackerViewCreator().getRoundTracker());
        assertEquals(0, this.app.getRoundTrackerViewCreator().getRound());
    }

    /**
     * Checks notify of other player disconnection
     */
    @Test
    public void testOtherPlayerLeave() {
        testSetApp(new FakeViewAction());
        PlayerState other = new PlayerState("OtherPlayerTest", 2, new IntColorPair[1][1], null, false, false);
        this.app.getPlayers().add(other);

        this.app.otherPlayerLeave("OtherPlayerTest");
        assertEquals("OtherPlayerTest ha lasciato il gioco." + enter, savedStream.toString());

    }

    /**
     * Checks notify of other player reconnection
     */
    @Test
    public void testOtherPlayerReconnection() {
        testSetApp(new FakeViewAction());
        PlayerState other = new PlayerState("OtherPlayerTest", 2, new IntColorPair[1][1], null, false, false);
        this.app.getPlayers().add(other);

        this.app.otherPlayerReconnection("OtherPlayerTest");
        assertEquals("OtherPlayerTest è rientrato in gioco." + enter, savedStream.toString());
    }

    /**
     * Checks notify of placement success
     */
    @Test
    public void testSetDieResult() {
        String message = "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(true, null);

        this.app.setDieResult(true, null);
        assertEquals("Dado piazzato!", savedStream.toString().split(enter)[1]);
    }

    /**
     * Checks notify of placement failure
     */
    @Test
    public void testSetDieResultFail() {
        String message = "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(true, null);

        this.app.setDieResult(false, "test error");
        assertEquals("Non sei riuscito a piazzare il dado: test error", savedStream.toString().split(enter)[1]);
    }

    /**
     * Checks notify of my placement
     */
    @Test
    public void testAddUpdateMe() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter + "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.startLogin(false);

        this.app.addUpdate("Test", 1, 1);
    }

    /**
     * Checks notify of other player's placement
     */
    @Test
    public void testAddUpdateOther() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter + "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.startLogin(false);

        IntColorPair[][] fakeGrid = new IntColorPair[1][1];
        fakeGrid[0][0] = new IntColorPair(1, ColorModel.RED);
        PlayerState playerState = new PlayerState("Other", 1, null, fakeGrid, false, false);
        this.app.players.add(playerState);

        this.app.addUpdate("Other", 0, 0);
    }

    /**
     * Checks notify of a toolCard usage success
     */
    @Test
    public void testUseToolCardResult() {
        String message = "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(true, null);

        this.app.useToolCardResult(true, null);
        assertEquals("Effetto carta completato!", savedStream.toString().split(enter)[1]);
    }

    /**
     * Checks notify of a toolCard usage failure
     */
    @Test
    public void testUseToolCardResultFail() {
        String message = "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(true, null);

        this.app.useToolCardResult(false, "test error");
        assertEquals("Non sei riuscito ad usare la carta: test error", savedStream.toString().split(enter)[1]);
    }

    /**
     * Checks notify of my toolCard usage
     */
    @Test
    public void testUseToolCardUpdateMe() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter + "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.startLogin(false);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(10, 1));
        cards.add(new SingleCardView(12, 1));

        this.app.getCardViewCreator().setPrivateObjectiveCard(new SingleCardView(1, 1));
        this.app.getCardViewCreator().setPublicObjectiveCards(cards);
        this.app.getCardViewCreator().setToolCards(cards);

        this.app.useToolCardUpdate("Test", 10);
    }

    /**
     * Checks notify of other player's toolCard usage
     */
    @Test
    public void testUseToolCardUpdateOther() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter + "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.startLogin(false);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(10, 1));
        cards.add(new SingleCardView(12, 1));

        this.app.getCardViewCreator().setPrivateObjectiveCard(new SingleCardView(1, 1));
        this.app.getCardViewCreator().setPublicObjectiveCards(cards);
        this.app.getCardViewCreator().setToolCards(cards);

        PlayerState playerState = new PlayerState("Other", 1, null, null, false, false);
        this.app.players.add(playerState);

        this.app.useToolCardUpdate("Other", 10);
    }

    /**
     * Checks notify of pass turn success
     */
    @Test
    public void testPassTurnResult() {
        String message = "0" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(true, null);

        this.app.passTurnResult(true);
        assertEquals("Turno passato con successo!", savedStream.toString().split(enter)[1]);
    }

    /**
     * Checks notify of pass turn failure
     */
    @Test
    public void testPassTurnResultFail() {
        String message = "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(true, null);

        this.app.passTurnResult(false);
        assertEquals("Non sei riuscito a passare il turno", savedStream.toString().split(enter)[1]);
    }

    /**
     * Checks notify of my pass turn
     */
    @Test
    public void testPassTurnUpdateMe() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter + "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.startLogin(false);

        this.app.passTurnUpdate("Test");
    }

    /**
     * Checks notify of other player's pass turn
     */
    @Test
    public void testPassTurnUpdateOther() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter + "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.startLogin(false);

        PlayerState playerState = new PlayerState("Other", 1, null, null, false, false);
        this.app.players.add(playerState);

        this.app.passTurnUpdate("Other");
    }

    /**
     * Simulates a game's end
     */
    @Test
    public void testGameEnd() {
        String message = "1" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(true, null);

        this.app.gameEnd(new MatchIdentifier("Test", "Test2", null, null), 1, 0, 0, 0);
        //No test of System.out because it's tested in CLIScoreViewCreator
    }

    /**
     * Checks notify of match abort from server
     */
    @Test
    public void testAbortMatch() {
        testSetApp(new FakeViewAction());

        app.abortMatch();
    }

    /**
     * Simulates a pop of a die from reserve
     */
    @Test
    public void testSelectDieFromReserve() {
        String message = "0" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        IntColorPair[] reserve = new IntColorPair[1];
        reserve[0] = new IntColorPair(1, ColorModel.RED);

        this.app.reserveViewCreator = new CLIReserveViewCreator(reserve);

        this.app.selectDieFromReserve();

        assertEquals("Seleziona un dado dalla riserva: ", savedStream.toString().split(enter)[0]);
    }

    /**
     * Simulates a choice of a new value for a die's face between two value
     */
    @Test
    public void testSelectNewValueForDie() {
        String message = "3" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        this.app.selectNewValueForDie(1, 3);

        assertEquals("Seleziona il valore del dado tra 1 e 3", savedStream.toString().split(enter)[0]);
    }

    /**
     * Checks reserve update success
     */
    @Test
    public void testUpdateReserve() {
        testSetApp(new FakeViewAction());

        IntColorPair[] reserve = new IntColorPair[1];
        reserve[0] = new IntColorPair(1, ColorModel.RED);

        this.app.reserveViewCreator = new CLIReserveViewCreator(reserve);

        this.app.updateReserve();

        assertEquals("Nuova riserva:", savedStream.toString().split(enter)[0]);
        assertEquals("0) 1-RED", savedStream.toString().split("\n")[1]);
    }

    /**
     * Simulates a pop of a die from my grid
     */
    @Test
    public void testSelectDieFromGrid() {
        String message = "3" + enter + "2" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        IntColorPair[][] pattern = new IntColorPair[1][1];
        pattern[0][0] = new IntColorPair(1, ColorModel.RED);
        IntColorPair[][] grid = new IntColorPair[1][1];
        grid[0][0] = new IntColorPair(1, ColorModel.RED);

        this.app.gridViewCreator = new CLIGridViewCreator(grid, pattern);

        this.app.selectDieFromGrid();

        assertEquals("Seleziona un dado dalla griglia (la numerazione parte da 0)", savedStream.toString().split(enter)[0]);
    }


    /**
     * Simulates a choice of a new value for a die's face
     */
    @Test
    public void testSelectFace() {
        String message = "4" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        this.app.selectFace(new IntColorPair(1, ColorModel.RED));

        assertEquals("Devi selezionare il nuovo valore del dado: 1-RED", savedStream.toString().split(enter)[0]);
        assertEquals("Inserisci nuovo valore:", savedStream.toString().split(enter)[1]);
    }

    /**
     * Simulates a pop of a die from grid by color restriction
     */
    @Test
    public void testSelectDieFromGridByColor() {
        String message = "3" + enter + "2" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        IntColorPair[][] pattern = new IntColorPair[1][1];
        pattern[0][0] = new IntColorPair(1, ColorModel.RED);
        IntColorPair[][] grid = new IntColorPair[1][1];
        grid[0][0] = new IntColorPair(1, ColorModel.RED);

        this.app.gridViewCreator = new CLIGridViewCreator(grid, pattern);

        this.app.selectDieFromGridByColor(ColorModel.BLUE);

        assertEquals("Seleziona un dado dalla griglia che ha colore: BLUE", savedStream.toString().split(enter)[0]);
        assertEquals("(la numerazione sulla griglia parte da 0)", savedStream.toString().split(enter)[1]);

    }

    /**
     * Simulates a choice of a number of dice to place
     */
    @Test
    public void testAskNumbersOfPlacement() {
        String message = "2" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        this.app.askNumbersOfPlacement();

        assertEquals("Seleziona il numero di dadi da spostare tra 1 e 2 ", savedStream.toString().split(enter)[0]);
    }

    /**
     * Checks notify of error in ToolCards usage
     */
    @Test
    public void testShowError() {
        String errorMessage = "Test error message";
        System.setIn(new ByteArrayInputStream(errorMessage.getBytes()));

        testSetApp(new FakeViewAction());

        this.app.showError(errorMessage);

        assertEquals(errorMessage, savedStream.toString().split(enter)[0]);
    }

    /**
     * Simulates a choice of x coordinate (grid's width)
     */
    @Test
    public void testGetCoordinateX() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        assertEquals(1, this.app.getCoordinateX());
    }

    /**
     * Simulates a choice of y coordinate (grid's height)
     */
    @Test
    public void testGetCoordinateY() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        assertEquals(1, this.app.getCoordinateY());
    }

    /**
     * Tests getter method of the printer object
     */
    @Test
    public void testGetPrinter() {
        testSetApp(new FakeViewAction());

        CLIPrinter instance = new CLIPrinter();
        Class<?> myPrinter = instance.getClass();

        assertEquals(myPrinter, this.app.getPrinter().getClass());

        Method methods[] = myPrinter.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            assertEquals(methods[i], this.app.getPrinter().getClass().getDeclaredMethods()[i]);
        }

        Field fields[] = myPrinter.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            assertEquals(fields[i], this.app.getPrinter().getClass().getDeclaredFields()[i]);
        }
    }

    /**
     * Tests getter method of the reader object
     */
    @Test
    public void testGetReader() {
        testSetApp(new FakeViewAction());

        CLIReader instance = new CLIReader(new CLIPrinter());
        Class<?> myReader = instance.getClass();

        assertEquals(myReader, this.app.getReader().getClass());

        Method methods[] = myReader.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            assertEquals(methods[i], this.app.getReader().getClass().getDeclaredMethods()[i]);
        }

        Field fields[] = myReader.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            assertEquals(fields[i], this.app.getReader().getClass().getDeclaredFields()[i]);
        }
    }

    /**
     * Tests methods with animation disabled
     */
    @Test
    public void testNoAnimation() {
        testSetApp(new FakeViewAction());

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(10, 1));
        cards.add(new SingleCardView(12, 1));

        CardView cardView = new CardView(new SingleCardView(1, 1), cards, cards);

        this.app.animation(false);
        this.app.startLogin(false);
        this.app.loginResult(false, null);
        this.app.changeLayerResult(false);
        this.app.createLobby();
        this.app.logoutResult(false);
        this.app.leaveMatchResult(false);
        this.app.askPattern(null, null, null, null, cardView);
        this.app.otherPlayerLeave(null);
        this.app.otherPlayerReconnection(null);
        this.app.startTurn();
        this.app.setDieResult(false, null);
        this.app.addUpdate(null, 0, 0);
        this.app.useToolCardResult(false, null);
        this.app.useToolCardUpdate(null, 0);
        this.app.passTurnResult(false);
        this.app.passTurnUpdate(null);
        this.app.gameEnd(null, 0, 0, 0, 0);
    }

    /**
     * Tests getter method of the GridViewCreator object
     */
    @Test
    public void testGridViewCreator() {
        testSetApp(new FakeViewAction());

        IntColorPair[][] pattern = new IntColorPair[1][1];
        pattern[0][0] = new IntColorPair(1, ColorModel.RED);
        IntColorPair[][] grid = new IntColorPair[1][1];
        grid[0][0] = new IntColorPair(1, ColorModel.RED);

        GridViewCreator gridViewCreator = new CLIGridViewCreator(grid, pattern);
        this.app.gridViewCreator = gridViewCreator;


        Class<?> instance = gridViewCreator.getClass();

        assertEquals(instance, this.app.getGridViewCreator().getClass());

        Method methods[] = instance.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            assertEquals(methods[i], this.app.getGridViewCreator().getClass().getDeclaredMethods()[i]);
        }

        Field fields[] = instance.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            assertEquals(fields[i], this.app.getGridViewCreator().getClass().getDeclaredFields()[i]);
        }
    }

    private class CLIAppMock extends CLIApp {

        CLIAppMock(ViewActions viewActions, ViewToolCardActions viewToolCardActions) {
            super(viewActions, viewToolCardActions);
        }

        @Override
        public void menu() {

        }
    }

    /**
     * Mock class of ViewActions object
     */
    private class FakeViewAction extends ViewActions {

        private FakeViewAction() {
            super(null);
        }

        @Override
        public String login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            assertEquals("Test", name);
            assertEquals("test", password);
            assertTrue(newUser);
            assertEquals("test", host);
            assertFalse(isRMI);
            assertEquals(80, objectPort);
            assertEquals(80, requestPort);
            return null;
        }

        @Override
        public void askLobby() {
            assert true;
        }

        @Override
        public void logout() {
            assert true;
        }

        @Override
        public void endInitGame() {
            assert true;
        }

        @Override
        public void selectedPattern(String name) {
            assert !name.equals("");
        }
    }

    /**
     * Mock class of ViewActions object for other tests
     */
    private class FakeViewAction2 extends ViewActions {

        private FakeViewAction2() {
            super(null);
        }

        @Override
        public String login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            assertEquals("Test", name);
            assertEquals("test", password);
            assertFalse(newUser);
            assertEquals("test", host);
            assertTrue(isRMI);
            assertEquals(80, requestPort);
            assertEquals(-1, objectPort);
            return null;
        }

        @Override
        public void askLobby() {
            assert true;
        }

        @Override
        public void logout() {
            assert true;
        }
    }

    /**
     * Mock class of ViewToolCardActions object
     */
    private class FakeViewToolActions extends ViewToolCardActions {

        FakeViewToolActions() {
            super(null);
        }

        @Override
        public void selectedDieFromReserve(int index) {
            assertEquals(0, index);
        }

        @Override
        public void selectedValueForDie(int value) {
            assertEquals(3, value);
        }

        @Override
        public void selectedDieFromGrid(int width, int height) {
            assertEquals(3, width);
            assertEquals(2, height);
        }

        @Override
        public void selectedDieToGrid(int width, int height) {
            assertEquals(3, width);
            assertEquals(2, height);
        }

        @Override
        public void selectedDieFromRoundTracker(int roundIndex, int dieIndex) {
            assertEquals(0, roundIndex);
            assertEquals(0, dieIndex);
        }

        @Override
        public void selectedFace(int value) {
            assertEquals(4, value);
        }

        @Override
        public void selectedDieFromGridByColor(int width, int height) {
            assertEquals(3, width);
            assertEquals(2, height);
        }

        @Override
        public void selectedNumbersOfPlacement(int number) {
            assertEquals(2, number);
        }
    }
}