package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

/**
 * Tests for CommandShowGrid class
 *
 * @author Mathyas Giudici
 */

public class CommandShowGridTest extends AbsCommandTest {

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
            assertEquals(20, requestPort);
        }
    }

    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(new FakeViewAction(null), new ViewToolCardActions(null), new ViewMessage(null));
        }

        @Override
        public void menu() {
            assert true;
        }
    }

    @Test
    public void testDoActionYour() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "20" + enter + "80" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        app.startLogin(false);

        PlayerView me = new PlayerView("Test", 0, 2, new Pair[1][1], null, false, false);
        PlayerView other = new PlayerView("OtherPlayerTest", 1, 2, new Pair[1][1], null, false, false);
        app.getPlayers().add(me);
        app.getPlayers().add(other);

        app.getGridViewCreator().setGridPattern(new Pair[1][1]);

        CommandShowGrid commandShowGrid = new CommandShowGrid(app);
        commandShowGrid.doAction();
    }

    @Test
    public void testDoActionOther() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "20" + enter + "80" + enter + "n" + enter + "0" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        app.startLogin(false);

        PlayerView me = new PlayerView("Test", 0, 2, new Pair[1][1], null, false, false);
        PlayerView other = new PlayerView("OtherPlayerTest", 1, 2, new Pair[1][1], null, false, false);
        app.getPlayers().add(me);
        app.getPlayers().add(other);


        CommandShowGrid commandShowGrid = new CommandShowGrid(app);
        commandShowGrid.doAction();
    }
}