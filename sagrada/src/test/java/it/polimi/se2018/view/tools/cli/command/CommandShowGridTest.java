package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.CLIApp;
import it.polimi.se2018.view.observer.PlayerState;
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
            assertEquals(20, requestPort);
            return null;
        }

        @Override
        public void askLobby() {
            assert true;
        }
    }

    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(new FakeViewAction(), new ViewToolCardActions(null), new ViewMessage(null));
        }

        @Override
        public void menu() {
            assert true;
        }
    }

    @Test
    public void testDoActionYour() throws Exception{
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "20" + enter + "80" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        app.startLogin(false);

        PlayerState me = new PlayerState("Test",  2, new IntColorPair[1][1], null, false, false);
        PlayerState other = new PlayerState("OtherPlayerTest",  2, new IntColorPair[1][1], null, false, false);
        app.getPlayers().add(me);
        app.getPlayers().add(other);

        app.getGridViewCreator().setGridPattern(new IntColorPair[1][1]);

        CommandShowGrid commandShowGrid = new CommandShowGrid(app);
        commandShowGrid.doAction();
    }

    @Test
    public void testDoActionOther() throws Exception{
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "20" + enter + "80" + enter + "n" + enter + "0" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        app.startLogin(false);

        PlayerState me = new PlayerState("Test",  2, new IntColorPair[1][1], null, false, false);
        PlayerState other = new PlayerState("OtherPlayerTest",  2, new IntColorPair[1][1], null, false, false);
        app.getPlayers().add(me);
        app.getPlayers().add(other);


        CommandShowGrid commandShowGrid = new CommandShowGrid(app);
        commandShowGrid.doAction();
    }
}