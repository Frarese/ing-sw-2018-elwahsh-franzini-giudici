package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.observer.ReserveView;
import it.polimi.se2018.observer.RoundTrackerView;
import it.polimi.se2018.util.*;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.tools.GridViewCreator;
import it.polimi.se2018.view.tools.cli.creators.CLIGridViewCreator;
import it.polimi.se2018.view.tools.cli.creators.CLIReserveViewCreator;
import it.polimi.se2018.view.tools.cli.creators.CLIRoundTrackerViewCreator;
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
     * ----------------------------------
     * Helper Classes
     * ----------------------------------
     */

    private class FakeViewAction extends ViewActions {

        FakeViewAction(String ownerName) {
            super(ownerName);
        }

        @Override
        public void login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            assertEquals("Test", name);
            assertEquals("test", password);
            assertTrue(newUser);
            assertEquals("test", host);
            assertFalse(isRMI);
            assertEquals(80, objectPort);
            assertEquals(80, requestPort);
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
        public void selectedPattern(PatternView selected) {
            Pair<Integer, ColorModel>[][] pattern = new Pair[1][2];
            pattern[0][0] = new Pair<>(1, ColorModel.RED);
            pattern[0][1] = new Pair<>(1, ColorModel.RED);

            assertArrayEquals(pattern, selected.template);
        }
    }

    private class FakeViewAction2 extends ViewActions {

        FakeViewAction2(String ownerName) {
            super(ownerName);
        }

        @Override
        public void login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            assertEquals("Test", name);
            assertEquals("test", password);
            assertFalse(newUser);
            assertEquals("test", host);
            assertTrue(isRMI);
            assertEquals(80, requestPort);
            assertEquals(-1, objectPort);
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


    private class FakeViewToolActions extends ViewToolCardActions {

        FakeViewToolActions(String ownerName) {
            super(ownerName);
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
    }

    /**
     * ----------------------------------
     * Helper Methods
     * ----------------------------------
     */

    private void testSetApp(ViewActions fakeViewAction) {
        this.app = new CLIApp(fakeViewAction, new FakeViewToolActions(null), new ViewMessage(null));
        this.app.animation(true);
    }

    private void testMenuHelper() {
        this.app.loginResult(true, null);
    }

    /**
     * ----------------------------------
     * Start Test
     * ----------------------------------
     */

    @Before
    public void testInit() {
        savedStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(savedStream));
    }

    @After
    public void testCloseOperation() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    public void testAnimation() {
        testSetApp(new FakeViewAction(null));
        assertTrue(this.app.animationEnable);
    }

    @Test
    public void testStartLoginDisplayWelcome() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        this.app.startLogin(true);

        assertFalse(this.app.useRMI());
    }

    @Test
    public void testStartLogin() {
        String message = "n" + enter + "Test" + enter + "test" + enter + "test" + enter + "y" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2(null));
        this.app.startLogin(false);

        assertTrue(this.app.useRMI());
    }

    @Test
    public void testLoginResult() {
        testSetApp(new FakeViewAction(null));
        this.app.loginResult(true, null);

        assertEquals("Login riuscito con successo!" + enter, this.savedStream.toString());
    }

    @Test
    public void testLoginResultFail() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        this.app.loginResult(false, null);
        assertEquals("Login NON riuscito! Riprova.", this.savedStream.toString().split(enter)[0]);
    }

    @Test
    public void testChangeLayerResultRMI() {
        String message = 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2(null));
        this.testMenuHelper();

        this.app.changeLayerResult(true);

        assertEquals("L'attuale layer e\' RMI.", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testChangeLayerResultSocket() {
        String message = 0 + enter + "n" + enter + 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2(null));
        this.testMenuHelper();

        this.app.changeLayerResult(false);

        assertEquals("L'attuale layer e\' Socket.", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testLeaveMatchResult() {
        testSetApp(new FakeViewAction2(null));

        this.app.leaveMatchResult(true);

        assertEquals("Match lasciato con successo." + enter, this.savedStream.toString());
    }

    @Test
    public void testLeaveMatchResultFail() {
        String message = 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2(null));
        this.testMenuHelper();

        this.app.leaveMatchResult(false);

        assertEquals("Non sono riuscito a disconnettermi dal match.", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testLogoutResult() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        this.app.logoutResult(true);

        assertEquals("Logout riuscito con successo!", this.savedStream.toString().split(enter)[0]);
    }

    @Test
    public void testLogoutResultFail() {
        String message = 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2(null));
        this.testMenuHelper();

        this.app.logoutResult(false);

        assertEquals("Logout NON riuscito!", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testCreateLobby() {
        String message = 6 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2(null));
        this.testMenuHelper();
        this.app.createLobby();
    }

    @Test
    public void testPullConnectedPlayers() {
        ArrayList<ScoreEntry> arrayList = new ArrayList<>();
        arrayList.add(0, new ScoreEntry("Test", 0, 0));
        arrayList.add(1, new ScoreEntry("Test1", 1, 1));

        testSetApp(new FakeViewAction(null));

        this.app.pullConnectedPlayers(arrayList);

        assertArrayEquals(arrayList.toArray(), this.app.getConnectedUsers().toArray());
    }

    @Test
    public void testPullLeaderBoard() {
        ArrayList<ScoreEntry> arrayList = new ArrayList<>();
        arrayList.add(0, new ScoreEntry("Test", 0, 0));
        arrayList.add(1, new ScoreEntry("Test1", 1, 1));

        testSetApp(new FakeViewAction(null));

        this.app.pullLeaderBoard(arrayList);

        assertArrayEquals(arrayList.toArray(), this.app.getLeaderBoard().toArray());
    }

    @Test
    public void testPullInvitate() {
        MatchIdentifier matchIdentifier1 = new MatchIdentifier("TestP0", "TestP1", "TestP2", "TestP3");
        MatchIdentifier matchIdentifier2 = new MatchIdentifier("TestP0", "Test", null, null);

        testSetApp(new FakeViewAction(null));

        this.app.pullInvitate(matchIdentifier1);
        this.app.pullInvitate(matchIdentifier2);


        assertEquals(matchIdentifier1.toString(), this.app.invites.get(0).toString());
        assertEquals(matchIdentifier2.toString(), this.app.invites.get(1).toString());
    }

    @Test
    public void testAskPattern1() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[][] pattern = new Pair[1][2];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        pattern[0][1] = new Pair<>(1, ColorModel.RED);
        PatternView pattern1 = new PatternView("Test1", 1, pattern);
        PatternView pattern2 = new PatternView("Test2", 2, pattern);
        PatternView pattern3 = new PatternView("Test3", 3, pattern);
        PatternView pattern4 = new PatternView("Test4", 4, pattern);

        this.app.askPattern(pattern1, pattern2, pattern3, pattern4);

        String result = "Carta Schema 1: Test1 (Favori : 1)" + enter + "Pattern\n" + enter +
                "--------------------" + enter + "|1-RED   ||1-RED   |" + enter +
                "--------------------" + enter + "Carta Schema 2: Test2 (Favori : 2)" +
                enter + "Pattern\n" + enter + "--------------------" +
                enter + "|1-RED   ||1-RED   |" + enter + "--------------------" +
                enter + "Carta Schema 3: Test3 (Favori : 3)" + enter + "Pattern\n" +
                enter + "--------------------" + enter + "|1-RED   ||1-RED   |" +
                enter + "--------------------" + enter + "Carta Schema 4: Test4 (Favori : 4)" +
                enter + "Pattern\n" + enter + "--------------------" + enter +
                "|1-RED   ||1-RED   |" + enter + "--------------------" + enter +
                "Opzione: " + enter;
        assertEquals(result, savedStream.toString());
    }

    @Test
    public void testAskPattern2() {
        String message = "2" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[][] pattern = new Pair[1][2];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        pattern[0][1] = new Pair<>(1, ColorModel.RED);
        PatternView pattern1 = new PatternView("Test1", 1, pattern);
        PatternView pattern2 = new PatternView("Test2", 2, pattern);
        PatternView pattern3 = new PatternView("Test3", 3, pattern);
        PatternView pattern4 = new PatternView("Test4", 4, pattern);

        this.app.askPattern(pattern1, pattern2, pattern3, pattern4);

        String result = "Carta Schema 1: Test1 (Favori : 1)" + enter + "Pattern\n" + enter +
                "--------------------" + enter + "|1-RED   ||1-RED   |" + enter +
                "--------------------" + enter + "Carta Schema 2: Test2 (Favori : 2)" +
                enter + "Pattern\n" + enter + "--------------------" +
                enter + "|1-RED   ||1-RED   |" + enter + "--------------------" +
                enter + "Carta Schema 3: Test3 (Favori : 3)" + enter + "Pattern\n" +
                enter + "--------------------" + enter + "|1-RED   ||1-RED   |" +
                enter + "--------------------" + enter + "Carta Schema 4: Test4 (Favori : 4)" +
                enter + "Pattern\n" + enter + "--------------------" + enter +
                "|1-RED   ||1-RED   |" + enter + "--------------------" + enter +
                "Opzione: " + enter;
        assertEquals(result, savedStream.toString());
    }

    @Test
    public void testAskPattern3() {
        String message = "3" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[][] pattern = new Pair[1][2];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        pattern[0][1] = new Pair<>(1, ColorModel.RED);
        PatternView pattern1 = new PatternView("Test1", 1, pattern);
        PatternView pattern2 = new PatternView("Test2", 2, pattern);
        PatternView pattern3 = new PatternView("Test3", 3, pattern);
        PatternView pattern4 = new PatternView("Test4", 4, pattern);

        this.app.askPattern(pattern1, pattern2, pattern3, pattern4);

        String result = "Carta Schema 1: Test1 (Favori : 1)" + enter + "Pattern\n" + enter +
                "--------------------" + enter + "|1-RED   ||1-RED   |" + enter +
                "--------------------" + enter + "Carta Schema 2: Test2 (Favori : 2)" +
                enter + "Pattern\n" + enter + "--------------------" +
                enter + "|1-RED   ||1-RED   |" + enter + "--------------------" +
                enter + "Carta Schema 3: Test3 (Favori : 3)" + enter + "Pattern\n" +
                enter + "--------------------" + enter + "|1-RED   ||1-RED   |" +
                enter + "--------------------" + enter + "Carta Schema 4: Test4 (Favori : 4)" +
                enter + "Pattern\n" + enter + "--------------------" + enter +
                "|1-RED   ||1-RED   |" + enter + "--------------------" + enter +
                "Opzione: " + enter;
        assertEquals(result, savedStream.toString());
    }

    @Test
    public void testAskPattern4() {
        String message = "4" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[][] pattern = new Pair[1][2];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        pattern[0][1] = new Pair<>(1, ColorModel.RED);
        PatternView pattern1 = new PatternView("Test1", 1, pattern);
        PatternView pattern2 = new PatternView("Test2", 2, pattern);
        PatternView pattern3 = new PatternView("Test3", 3, pattern);
        PatternView pattern4 = new PatternView("Test4", 4, pattern);

        this.app.askPattern(pattern1, pattern2, pattern3, pattern4);

        String result = "Carta Schema 1: Test1 (Favori : 1)" + enter + "Pattern\n" + enter +
                "--------------------" + enter + "|1-RED   ||1-RED   |" + enter +
                "--------------------" + enter + "Carta Schema 2: Test2 (Favori : 2)" +
                enter + "Pattern\n" + enter + "--------------------" +
                enter + "|1-RED   ||1-RED   |" + enter + "--------------------" +
                enter + "Carta Schema 3: Test3 (Favori : 3)" + enter + "Pattern\n" +
                enter + "--------------------" + enter + "|1-RED   ||1-RED   |" +
                enter + "--------------------" + enter + "Carta Schema 4: Test4 (Favori : 4)" +
                enter + "Pattern\n" + enter + "--------------------" + enter +
                "|1-RED   ||1-RED   |" + enter + "--------------------" + enter +
                "Opzione: " + enter;
        assertEquals(result, savedStream.toString());
    }

    @Test
    public void testInitGame() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter + "6" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        List<PlayerView> players = new ArrayList<>();
        PlayerView me = new PlayerView("Test", 0, 2, new Pair[1][1], null, false, false);
        PlayerView other = new PlayerView("OtherPlayerTest", 1, 2, new Pair[1][1], null, false, false);
        players.add(me);
        players.add(other);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(10, 1));
        cards.add(new SingleCardView(12, 1));

        Pair<Integer, ColorModel>[][] fakeRoundt = new Pair[1][1];
        fakeRoundt[0][0] = new Pair<>(1, ColorModel.RED);
        RoundTrackerView roundTracker = new RoundTrackerView(0, fakeRoundt);

        Pair<Integer, ColorModel>[] fakeReserve = new Pair[1];
        fakeReserve[0] = new Pair<>(1, ColorModel.RED);
        ReserveView reserveView = new ReserveView(fakeReserve);

        this.app.startLogin(false);
        this.app.getGridViewCreator().setGridPattern(new Pair[1][1]);
        this.app.getCardViewCreator().setPrivateObjectiveCard(new SingleCardView(1, 0));
        this.app.getCardViewCreator().setPublicObjectiveCards(cards);
        this.app.getCardViewCreator().setToolCards(cards);
        this.app.getRoundTrackerViewCreator().setRound(0);
        this.app.getRoundTrackerViewCreator().setRoundTracker(fakeRoundt);
        this.app.initGame(players);
        this.app.startTurn();


        assertEquals(me.getPlayerName(), this.app.getOwnerPlayerName());
        assertEquals(me.getPlayerID(), this.app.getOwnerPlayerID());
        assertNotNull(this.app.getCardViewCreator());
        assertNotNull(this.app.getRoundTrackerViewCreator());
        assertNotNull(this.app.getReserveViewCreator());
        assertEquals(1, this.app.getCardViewCreator().getPrivateObjectiveCard().cardID);
        assertArrayEquals(cards.toArray(), this.app.getCardViewCreator().getPublicObjectiveCards().toArray());
        assertArrayEquals(cards.toArray(), this.app.getCardViewCreator().getToolCards().toArray());
        assertArrayEquals(fakeRoundt, this.app.getRoundTrackerViewCreator().getRoundTracker());
        assertEquals(0, this.app.getRoundTrackerViewCreator().getRound());
    }

    @Test
    public void testOtherPlayerLeave() {
        testSetApp(new FakeViewAction(null));
        PlayerView other = new PlayerView("OtherPlayerTest", 1, 2, new Pair[1][1], null, false, false);
        this.app.getPlayers().add(other);

        this.app.otherPlayerLeave("OtherPlayerTest");
        assertEquals("OtherPlayerTest ha lasciato il gioco." + enter, savedStream.toString());

    }

    @Test
    public void testOtherPlayerReconnection() {
        testSetApp(new FakeViewAction(null));
        PlayerView other = new PlayerView("OtherPlayerTest", 1, 2, new Pair[1][1], null, false, false);
        this.app.getPlayers().add(other);

        this.app.otherPlayerReconnection("OtherPlayerTest");
        assertEquals("OtherPlayerTest e\' rientrato in gioco." + enter, savedStream.toString());
    }

    @Test
    public void testSetDieResult() {
        String message = "0" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        this.app.loginResult(true, null);

        this.app.setDieResult(true, null);
        assertEquals("Dado piazzato!", savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testSetDieResultFail() {
        String message = "0" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        this.app.loginResult(true, null);

        this.app.setDieResult(false, "test error");
        assertEquals("Non sei riuscito a piazzare il dado: test error", savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testAddUpdate() {
        //TODO
//        this.app.addUpdate(0, 0, 0, 0);
//        this.app.addUpdate(1, 2, 3, 0);
//
//        assertEquals("OtherPlayerTest ha posizionato il dado: 1-RED in posizione (2,3).", savedStream.toString().split(enter)[savedStream.toString().split(enter).length - 1]);
    }

    @Test
    public void testUseToolCardResult() {
        String message = "0" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        this.app.loginResult(true, null);

        this.app.useToolCardResult(true, null);
        assertEquals("Effetto carta completato!", savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testUseToolCardResultFail() {
        String message = "0" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        this.app.loginResult(true, null);

        this.app.useToolCardResult(false, "test error");
        assertEquals("Non sei riuscito ad usare la carta: test error", savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testUseToolCardUpdate() {
        //TODO
    }

    @Test
    public void testPassTurnResult() {
        String message = "0" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        this.app.loginResult(true, null);

        this.app.passTurnResult(true);
        assertEquals("Turno passato con successo!", savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testPassTurnResultFail() {
        String message = "0" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        this.app.loginResult(true, null);

        this.app.passTurnResult(false);
        assertEquals("Non sei riuscito a passare il turno", savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testPassTurnUpdate() {
    }

    @Test
    public void testGameEnd() {
        String message = "0" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        this.app.loginResult(true, null);

        List<ScoreEntry> scoreEntries = new ArrayList<>();
        scoreEntries.add(new ScoreEntry("Test", 1, 1));

        this.app.gameEnd(scoreEntries);
        //No test of System.out because it's tested in CLIScoreViewCreator
    }

    @Test
    public void testSelectDieFromReserve() {
        String message = "0" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[] reserve = new Pair[1];
        reserve[0] = new Pair<>(1, ColorModel.RED);

        this.app.reserveViewCreator = new CLIReserveViewCreator(reserve);

        this.app.selectDieFromReserve();

        assertEquals("Seleziona un dado dalla riserva: ", savedStream.toString().split(enter)[0]);
    }

    @Test
    public void testSelectNewValueForDie() {
        String message = "3" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        this.app.selectNewValueForDie(1, 3);

        assertEquals("Seleziona il valore del dado tra 1 e 3", savedStream.toString().split(enter)[0]);
    }

    @Test
    public void testUpdateReserve() {
        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[] reserve = new Pair[1];
        reserve[0] = new Pair<>(1, ColorModel.RED);

        this.app.reserveViewCreator = new CLIReserveViewCreator(reserve);

        this.app.updateReserve();

        assertEquals("Nuova riserva:", savedStream.toString().split(enter)[0]);
        assertEquals("0) 1-RED", savedStream.toString().split("\n")[1]);
    }

    @Test
    public void testSelectDieFromGrid() {
        String message = "3" + enter + "2" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[][] pattern = new Pair[1][1];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        Pair<Integer, ColorModel>[][] grid = new Pair[1][1];
        grid[0][0] = new Pair<>(1, ColorModel.RED);

        this.app.gridViewCreator = new CLIGridViewCreator(grid, pattern, this.app.getPrinter());

        this.app.selectDieFromGrid();

        assertEquals("Seleziona un dado dalla griglia (la numerazione parte da 0)", savedStream.toString().split(enter)[0]);
    }

    @Test
    public void testSetDieOnGrid() {
        String message = "3" + enter + "2" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[][] pattern = new Pair[1][1];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        Pair<Integer, ColorModel>[][] grid = new Pair[1][1];
        grid[0][0] = new Pair<>(1, ColorModel.RED);

        this.app.gridViewCreator = new CLIGridViewCreator(grid, pattern, this.app.getPrinter());

        this.app.setDieOnGrid(new Pair<>(1, ColorModel.RED));

        assertEquals("Devi posizionare il dado: 1-RED", savedStream.toString().split(enter)[0]);
        assertEquals("(la numerazione sulla griglia parte da 0)", savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testSelectDieFromRoundTracker() {
        String message = "1" + enter + "0" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[][] fakeRoundt = new Pair[1][1];
        fakeRoundt[0][0] = new Pair<>(1, ColorModel.RED);

        this.app.roundTrackerViewCreator = new CLIRoundTrackerViewCreator(2, fakeRoundt);

        this.app.selectDieFromRoundTracker();

        assertEquals("Siamo al turno: 2", savedStream.toString().split(enter)[0]);
        assertEquals("Turno 1 : ", savedStream.toString().split(enter)[1]);
        assertEquals("0) 1-RED", savedStream.toString().split(enter)[2]);
        assertEquals("Inserire il numero del round da cui si vuole estrarre il dado:", savedStream.toString().split(enter)[3]);
        assertEquals("Opzione: ", savedStream.toString().split(enter)[4]);
        assertEquals("Inserire il numero del dado nel round selezionato", savedStream.toString().split(enter)[5]);
        assertEquals("Opzione: ", savedStream.toString().split(enter)[6]);

    }

    @Test
    public void testSelectFace() {
        String message = "4" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        this.app.selectFace(new Pair<>(1, ColorModel.RED));

        assertEquals("Devi selezionare il nuovo valore del dado: 1-RED", savedStream.toString().split(enter)[0]);
        assertEquals("Inserisci nuovo valore:", savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testSelectDieFromGridByColor() {
        String message = "3" + enter + "2" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[][] pattern = new Pair[1][1];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        Pair<Integer, ColorModel>[][] grid = new Pair[1][1];
        grid[0][0] = new Pair<>(1, ColorModel.RED);

        this.app.gridViewCreator = new CLIGridViewCreator(grid, pattern, this.app.getPrinter());

        this.app.selectDieFromGridByColor(ColorModel.BLUE);

        assertEquals("Seleziona un dado dalla griglia che ha colore: BLUE", savedStream.toString().split(enter)[0]);
        assertEquals("(la numerazione sulla griglia parte da 0)", savedStream.toString().split(enter)[1]);

    }

    @Test
    public void testGetCoordinateX() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        assertEquals(1, this.app.getCoordinateX());
    }

    @Test
    public void testGetCoordinateY() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction(null));
        assertEquals(1, this.app.getCoordinateY());
    }

    @Test
    public void testGetPrinter() {
        testSetApp(new FakeViewAction(null));

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

    @Test
    public void testGetReader() {
        testSetApp(new FakeViewAction(null));

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

    @Test
    public void testNoAnimation() {
        testSetApp(new FakeViewAction(null));
        this.app.animation(false);
        this.app.startLogin(false);
        this.app.loginResult(false, null);
        this.app.changeLayerResult(false);
        this.app.logoutResult(false);
        this.app.leaveMatchResult(false);
        this.app.pullLeaderBoard(null);
        this.app.askPattern(null, null, null, null);
        this.app.initGame(null);
        this.app.otherPlayerLeave(null);
        this.app.otherPlayerReconnection(null);
        this.app.startTurn();
        this.app.setDieResult(false, null);
        this.app.addUpdate(null, 0, 0, 0);
        this.app.useToolCardResult(false, null);
        this.app.useToolCardUpdate(null, 0);
        this.app.passTurnResult(false);
        this.app.passTurnUpdate(null);
        this.app.gameEnd(null);
    }

    @Test
    public void testGridViewCreator() {
        testSetApp(new FakeViewAction(null));

        Pair<Integer, ColorModel>[][] pattern = new Pair[1][1];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        Pair<Integer, ColorModel>[][] grid = new Pair[1][1];
        grid[0][0] = new Pair<>(1, ColorModel.RED);

        GridViewCreator gridViewCreator = new CLIGridViewCreator(grid, pattern, this.app.getPrinter());
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
}