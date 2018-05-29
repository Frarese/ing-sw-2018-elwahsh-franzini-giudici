package it.polimi.se2018.view.app;

import it.polimi.se2018.util.MatchIdentifier;
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
    public void animationTest() {
        testSetApp(new FakeViewAction());
        assertTrue(this.app.animationEnable);
    }

    @Test
    public void startLoginDisplayWelcomeTest() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.startLogin(true);
    }

    @Test
    public void startLoginTest() {
        String message = "n" + enter + "Test" + enter + "test" + enter + "test" + enter + "y" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.app.startLogin(false);
    }

    @Test
    public void loginResultTest() {
        testSetApp(new FakeViewAction());
        this.app.loginResult(true);

        assertEquals("Login riuscito con successo!" + enter, this.savedStream.toString());
    }

    @Test
    public void loginResultFailTest() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.loginResult(false);
        assertEquals("Login NON riuscito! Riprova.", this.savedStream.toString().split(enter)[0]);
    }

    @Test
    public void changeLayerResultRMITest() {
        String message = 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.changeLayerResult(true);

        assertEquals("L'attuale layer e\' RMI.", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void changeLayerResultSocketTest() {
        String message = 0 + enter + "n" + enter + 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.changeLayerResult(false);

        assertEquals("L'attuale layer e\' Socket.", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void leaveMatchResultTest() {
        testSetApp(new FakeViewAction2());

        this.app.leaveMatchResult(true);

        assertEquals("Match lasciato con successo." + enter, this.savedStream.toString());
    }

    @Test
    public void leaveMatchResultFailTest() {
        String message = 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.leaveMatchResult(false);

        assertEquals("Non sono riuscito a disconnettermi dal match.", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void logoutResultTest() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        this.app.logoutResult(true);

        assertEquals("Logout riuscito con successo!", this.savedStream.toString().split(enter)[0]);
    }

    @Test
    public void logoutResultFailTest() {
        String message = 0 + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction2());
        this.testMenuHelper();

        this.app.logoutResult(false);

        assertEquals("Logout NON riuscito!", this.savedStream.toString().split(enter)[1]);
    }

    @Test
    public void pullLeaderBoardTest() {
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
    public void pullInvitateTest() {
        MatchIdentifier matchIdentifier1 = new MatchIdentifier("TestP0", "TestP1", "TestP2", "TestP3");
        MatchIdentifier matchIdentifier2 = new MatchIdentifier("TestP0", "Test", null, null);

        testSetApp(new FakeViewAction());

        this.app.pullInvitate(matchIdentifier1);
        this.app.pullInvitate(matchIdentifier2);


        assertEquals(matchIdentifier1.toString(), this.app.invites.get(0).toString());
        assertEquals(matchIdentifier2.toString(), this.app.invites.get(1).toString());
    }

    @Test
    public void askPatternTest() {
        //TODO think to refactor and change signature ad super method
    }

    @Test
    public void initGameTest() {
        String message = "y" + enter + "ME" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());

//        List<PlayerView> players = new ArrayList<>();
//        players.add(new FakePlayerView("ME", 0, 0, true, true, new Pair[1][1], new Pair[1][1]));
//        players.add(new FakePlayerView("Other", 1, 0, true, true, new Pair[1][1], new Pair[1][1]));
//
//        int[] cards = new int[3];
//        cards[0] = 0;
//        cards[1] = 1;
//
//        this.app.initGame(players, 1, cards, cards, new FakeRoundTraker);

    }

    @Test
    public void otherPlayerLeaveTest() {
    }

    @Test
    public void otherPlayerReconnectionTest() {
    }

    @Test
    public void startTurnTest() {
    }

    @Test
    public void setDieResultTest() {
    }

    @Test
    public void addUpdateTest() {
    }

    @Test
    public void useToolCardResultTest() {
    }

    @Test
    public void useToolCardUpdateTest() {
    }

    @Test
    public void passTurnResultTest() {
    }

    @Test
    public void passTurnUpdateTest() {
    }

    @Test
    public void gameEndTest() {
    }

    @Test
    public void selectDieFromReserveTest() {
    }

    @Test
    public void selectNewValueForDieTest() {
    }

    @Test
    public void updateReserveTest() {
    }

    @Test
    public void selectDieFromGridTest() {
    }

    @Test
    public void setDieOnGridTest() {
    }

    @Test
    public void selectDieFromRoundTrackerTest() {
    }

    @Test
    public void selectFaceTest() {
    }

    @Test
    public void selectDieFromGridByColorTest() {
    }

    @Test
    public void getCoordinateXTest() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        assertEquals(1, this.app.getCoordinateX());
    }

    @Test
    public void getCoordinateYTest() {
        String message = "1" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        testSetApp(new FakeViewAction());
        assertEquals(1, this.app.getCoordinateY());
    }

    @Test
    public void getPrinterTest() {
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
    public void getReaderTest() {
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
    public void noAnimationTest() {
        testSetApp(new FakeViewAction());
        this.app.animation(false);
        this.app.startLogin(false);
        this.app.loginResult(false);
        this.app.changeLayerResult(false);
        this.app.leaveMatchResult(false);
        this.app.pullLeaderBoard(null);
        this.app.initGame(null, 0, null, null, null);
        this.app.otherPlayerLeave(0);
        this.app.otherPlayerReconnection(0);
        this.app.startTurn(null, null, null);
        this.app.setDieResult(false, null);
        this.app.addUpdate(0, 0, 0, 0);
        this.app.useToolCardResult(false, null);
        this.app.useToolCardUpdate(0, 0);
        this.app.passTurnResult(false);
        this.app.passTurnUpdate(0);
        this.app.gameEnd(null);
    }
}