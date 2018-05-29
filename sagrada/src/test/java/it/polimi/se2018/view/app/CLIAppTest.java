package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.observer.ReserveView;
import it.polimi.se2018.observer.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.ViewActions;
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

    private String enter = System.lineSeparator();

    private ByteArrayOutputStream savedStream;

    private CLIApp app;

    /**
     * ----------------------------------
     * Helper Classes
     * ----------------------------------
     */

    private class FakeViewAction extends ViewActions {
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

            assertEquals(1, selected.favours);
            assertEquals("Test1", selected.patternName);
            assertArrayEquals(pattern, selected.template);
        }
    }

    private class FakeViewAction2 extends ViewActions {
        @Override
        public void login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            assertEquals("Test", name);
            assertEquals("test", password);
            assertFalse(newUser);
            assertEquals("test", host);
            assertTrue(isRMI);
            assertEquals(80, objectPort);
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
     * ----------------------------------
     * Helper Methods
     * ----------------------------------
     */

    private void testSetApp(ViewActions fakeViewAction) {
        this.app = new CLIApp(fakeViewAction, null, null);
        this.app.animation(true);
    }

    private void testMenuHelper() {
        this.app.loginResult(true);
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
        testSetApp(new FakeViewAction());
        assertTrue(this.app.animationEnable);
    }

    @Test
    public void testStartLoginDisplayWelcome() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.startLogin(true);
    }

    @Test
    public void testStartLogin() {
        String message = "n" + enter + "Test" + enter + "test" + enter + "test" + enter + "y" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.app.startLogin(false);
    }

    @Test
    public void testLoginResult() {
        testSetApp(new FakeViewAction());
        this.app.loginResult(true);

        assertEquals("Login riuscito con successo!" + enter, this.savedStream.toString());
    }

    @Test
    public void testLoginResultFail() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(false);
        assertEquals("Login NON riuscito! Riprova.", this.savedStream.toString().split(enter)[0]);
    }

    @Test
    public void testChangeLayerResultRMI() {
        String message = 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.changeLayerResult(true);

        assertEquals("L'attuale layer e\' RMI.", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testChangeLayerResultSocket() {
        String message = 0 + enter + "n" + enter + 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.changeLayerResult(false);

        assertEquals("L'attuale layer e\' Socket.", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testLeaveMatchResult() {
        testSetApp(new FakeViewAction2());

        this.app.leaveMatchResult(true);

        assertEquals("Match lasciato con successo." + enter, this.savedStream.toString());
    }

    @Test
    public void testLeaveMatchResultFail() {
        String message = 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.leaveMatchResult(false);

        assertEquals("Non sono riuscito a disconnettermi dal match.", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testLogoutResult() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.logoutResult(true);

        assertEquals("Logout riuscito con successo!", this.savedStream.toString().split(enter)[0]);
    }

    @Test
    public void testLogoutResultFail() {
        String message = 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.logoutResult(false);

        assertEquals("Logout NON riuscito!", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testPullLeaderBoard() {
        ArrayList<ScoreEntry> arrayList = new ArrayList<>();
        arrayList.add(0, new ScoreEntry("Test", 0, 0));
        arrayList.add(1, new ScoreEntry("Test1", 1, 1));

        String message = 5 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.pullLeaderBoard(arrayList);

        for (int i = 0; i < arrayList.size(); i++) {
            assertEquals(arrayList.get(i).usn, this.app.leaderBoard.get(i).usn);
            assertEquals(arrayList.get(i).wins, this.app.leaderBoard.get(i).wins);
            assertEquals(arrayList.get(i).tot, this.app.leaderBoard.get(i).tot);
        }
    }

    @Test
    public void testPullInvitate() {
        MatchIdentifier matchIdentifier1 = new MatchIdentifier("TestP0", "TestP1", "TestP2", "TestP3");
        MatchIdentifier matchIdentifier2 = new MatchIdentifier("TestP0", "Test", null, null);

        testSetApp(new FakeViewAction());

        this.app.pullInvitate(matchIdentifier1);
        this.app.pullInvitate(matchIdentifier2);


        assertEquals(matchIdentifier1.toString(), this.app.invites.get(0).toString());
        assertEquals(matchIdentifier2.toString(), this.app.invites.get(1).toString());
    }

    @Test
    public void testAskPattern() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        Pair<Integer, ColorModel>[][] pattern = new Pair[1][2];
        pattern[0][0] = new Pair<>(1, ColorModel.RED);
        pattern[0][1] = new Pair<>(1, ColorModel.RED);
        PatternView pattern1 = new PatternView("Test1", 1, pattern);
        PatternView pattern2 = new PatternView("Test2", 2, pattern);
        PatternView pattern3 = new PatternView("Test3", 3, pattern);
        PatternView pattern4 = new PatternView("Test4", 4, pattern);

        List<String> result = new ArrayList<>();
        result.add("Carta Schema 1: Test1 (Favori : 1)");
        result.add("Pattern");
        result.add("");
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        result.add("Carta Schema 2: Test2 (Favori : 2)");
        result.add("Pattern");
        result.add("");
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        result.add("Carta Schema 3: Test3 (Favori : 3)");
        result.add("Pattern");
        result.add("");
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        result.add("Carta Schema 4: Test4 (Favori : 4)");
        result.add("Pattern");
        result.add("");
        result.add("--------------------");
        result.add("|1-RED   ||1-RED   |");
        result.add("--------------------");
        result.add("Opzione: ");

        this.app.askPattern(pattern1, pattern2, pattern3, pattern4);

        assertArrayEquals(result.toArray(), savedStream.toString().split(enter));
    }

    @Test
    public void testInitGame() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter + "4" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

        List<PlayerView> players = new ArrayList<>();
        PlayerView me = new PlayerView(true, "Test", 0, 2, new Pair[1][1], null, false, false);
        PlayerView other = new PlayerView(true, "OtherPlayerTest", 1, 2, new Pair[1][1], null, false, false);
        players.add(me);
        players.add(other);

        int[] cards = new int[3];
        cards[0] = 0;
        cards[1] = 1;

        Pair<Integer, ColorModel>[][] fakeRoundt = new Pair[1][1];
        fakeRoundt[0][0] = new Pair<>(1, ColorModel.RED);
        RoundTrackerView roundTracker = new RoundTrackerView(true, 0, fakeRoundt);

        Pair<Integer, ColorModel>[] fakeReserve = new Pair[1];
        fakeReserve[0] = new Pair<>(1, ColorModel.RED);
        ReserveView reserveView = new ReserveView(true, fakeReserve);

        this.app.startLogin(false);
        this.app.initGame(players, 1, cards, cards, roundTracker);
        this.app.startTurn(reserveView, roundTracker);


        assertEquals(me.getPlayerName(), this.app.getOwnerPlayerName());
        assertEquals(me.getPlayerID(), this.app.getOwnerPlayerID());
        assertNotNull(this.app.getCardViewCreator());
        assertNotNull(this.app.getRoundTrackerViewCreator());
        assertNotNull(this.app.getReserveViewCreator());
        assertEquals(1, this.app.getCardViewCreator().getPrivateObjectiveCard());
        assertArrayEquals(cards, this.app.getCardViewCreator().getPublicObjectiveCards());
        assertArrayEquals(cards, this.app.getCardViewCreator().getToolCards());
        assertArrayEquals(fakeRoundt, this.app.getRoundTrackerViewCreator().getRoundTracker());
        assertEquals(0, this.app.getRoundTrackerViewCreator().getRound());
    }

    @Test
    public void testOtherPlayerLeave() {
        testSetApp(new FakeViewAction());
        PlayerView other = new PlayerView(true, "OtherPlayerTest", 1, 2, new Pair[1][1], null, false, false);
        this.app.getPlayers().add(other);

        this.app.otherPlayerLeave(1);
        assertEquals("OtherPlayerTest ha lasciato il gioco." + enter, savedStream.toString());

    }

    @Test
    public void testOtherPlayerReconnection() {
        testSetApp(new FakeViewAction());
        PlayerView other = new PlayerView(true, "OtherPlayerTest", 1, 2, new Pair[1][1], null, false, false);
        this.app.getPlayers().add(other);

        this.app.otherPlayerReconnection(1);
        assertEquals("OtherPlayerTest e\' rientrato in gioco." + enter, savedStream.toString());
    }

    @Test
    public void testSetDieResult() {
        String message = "0" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(true);

        this.app.setDieResult(true, null);
        assertEquals("Dado piazzato!", savedStream.toString().split(enter)[1]);
    }

    @Test
    public void testSetDieResultFail() {
        String message = "0" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(true);

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
    }

    @Test
    public void testUseToolCardUpdate() {
    }

    @Test
    public void testPassTurnResult() {
    }

    @Test
    public void testPassTurnUpdate() {
    }

    @Test
    public void testGameEnd() {
    }

    @Test
    public void testSelectDieFromReserve() {
    }

    @Test
    public void testSelectNewValueForDie() {
    }

    @Test
    public void testUpdateReserve() {
    }

    @Test
    public void testSelectDieFromGrid() {
    }

    @Test
    public void testSetDieOnGrid() {
    }

    @Test
    public void testSelectDieFromRoundTracker() {
    }

    @Test
    public void testSelectFace() {
    }

    @Test
    public void testSelectDieFromGridByColor() {
    }

    @Test
    public void testGetCoordinateX() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        assertEquals(1, this.app.getCoordinateX());
    }

    @Test
    public void testGetCoordinateY() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        assertEquals(1, this.app.getCoordinateY());
    }

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

    @Test
    public void testNoAnimation() {
        testSetApp(new FakeViewAction());
        this.app.animation(false);
        this.app.startLogin(false);
        this.app.loginResult(false);
        this.app.changeLayerResult(false);
        this.app.logoutResult(false);
        this.app.leaveMatchResult(false);
        this.app.pullLeaderBoard(null);
        this.app.askPattern(null,null,null,null);
        this.app.initGame(null, 0, null, null, null);
        this.app.otherPlayerLeave(0);
        this.app.otherPlayerReconnection(0);
        this.app.startTurn(null, null);
        this.app.setDieResult(false, null);
        this.app.addUpdate(0, 0, 0, 0);
        this.app.useToolCardResult(false, null);
        this.app.useToolCardUpdate(0, 0);
        this.app.passTurnResult(false);
        this.app.passTurnUpdate(0);
        this.app.gameEnd(null);
    }
}