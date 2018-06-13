package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CommandLogout class
 *
 * @author Mathyas Giudici
 */

public class CommandLogoutTest extends AbsCommandTest {

    private class FakeViewAction extends ViewActions {
        private FakeViewAction() {
            super(null);
        }

        @Override
        public void logout() {
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
    public void testDoActionYes() throws Exception{
        String message = "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        CommandLogout commandLogout = new CommandLogout(app);
        commandLogout.doAction();

        assertEquals("Sei sicuro di voler uscire dal gioco?", savedStream.toString().split(regexControl)[0]);
    }

    @Test
    public void testDoActionNo() throws Exception{
        String message = "n" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        CommandLogout commandLogout = new CommandLogout(app);
        commandLogout.doAction();

        assertEquals("Sei sicuro di voler uscire dal gioco?", savedStream.toString().split(regexControl)[0]);
    }
}