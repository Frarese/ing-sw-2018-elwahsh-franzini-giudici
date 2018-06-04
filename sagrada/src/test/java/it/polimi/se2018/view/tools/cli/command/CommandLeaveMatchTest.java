package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CommandLeaveMatch class
 *
 * @author Mathyas Giudici
 */


public class CommandLeaveMatchTest extends AbsCommandTest {

    private class FakeViewAction extends ViewActions {
        private FakeViewAction(String ownerName) {
            super(ownerName);
        }

        @Override
        public void leaveMatch() {
            assert true;
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
    public void testDoActionYes() {
        String message = "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        CommandLeaveMatch commandLeaveMatch = new CommandLeaveMatch(app);
        commandLeaveMatch.doAction();

        assertEquals("Sei sicuro di voler lasciare la partita?", savedStream.toString().split(enter)[0]);
    }

    @Test
    public void testDoActionNo() {
        String message = "n" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        CommandLeaveMatch commandLeaveMatch = new CommandLeaveMatch(app);
        commandLeaveMatch.doAction();

        assertEquals("Sei sicuro di voler lasciare la partita?", savedStream.toString().split(enter)[0]);
    }
}