package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

/**
 * Tests for CommandLeaveMatch class
 *
 * @author Mathyas Giudici
 */

public class CommandLeaveMatchTest extends AbsCommandTest {

    private boolean flag;

    /**
     * Mock class of ViewActions object
     */
    private class FakeViewAction extends ViewActions {
        private FakeViewAction() {
            super(null);
        }

        @Override
        public void leaveMatch() {
            flag = true;
        }
    }

    /**
     * Mock class of CLIApp object
     */
    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(new FakeViewAction(), new ViewToolCardActions(null));
        }

        @Override
        public void menu() {
            assert true;
        }
    }

    /**
     * Checks command perform with match leaving
     *
     * @throws Exception if an error occurs during reading
     */
    @Test
    public void testDoActionYes() throws Exception {
        flag = false;
        String message = "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        CommandLeaveMatch commandLeaveMatch = new CommandLeaveMatch(app);
        commandLeaveMatch.doAction();

        assertEquals("Sei sicuro di voler lasciare la partita?", savedStream.toString().split(regexControl)[0]);
        assertTrue(flag);
    }

    /**
     * Checks command perform without match leaving
     *
     * @throws Exception if an error occurs during reading
     */
    @Test
    public void testDoActionNo() throws Exception {
        String message = "n" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        CommandLeaveMatch commandLeaveMatch = new CommandLeaveMatch(app);
        commandLeaveMatch.doAction();

        assertEquals("Sei sicuro di voler lasciare la partita?", savedStream.toString().split(regexControl)[0]);
        assertFalse(flag);
    }
}