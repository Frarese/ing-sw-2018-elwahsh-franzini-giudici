package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

/**
 * Tests for CommandShowRoundTracker class
 *
 * @author Mathyas Giudici
 */

public class CommandShowRoundTrackerTest extends AbsCommandTest {

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
            assertEquals(100, requestPort);
            return null;
        }

        @Override
        public void askLobby() {
            assert true;
        }
    }

    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(new FakeViewAction(), new ViewToolCardActions(null));
        }

        @Override
        public void menu() {
            assert true;
        }
    }

    @Test
    public void testDoAction() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "100" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        app.startLogin(false);

        IntColorPair[][] fakeRoundt = new IntColorPair[9][10];
        fakeRoundt[0][0] = new IntColorPair(1, ColorModel.RED);

        app.getRoundTrackerViewCreator().setRound(1);
        app.getRoundTrackerViewCreator().setRoundTracker(fakeRoundt);

        CommandShowRoundTracker commandShowRoundTracker = new CommandShowRoundTracker(app);
        commandShowRoundTracker.doAction();
    }
}