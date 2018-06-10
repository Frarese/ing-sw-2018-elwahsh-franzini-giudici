package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.controller.game.client.ActionSender;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for CommandCreateInvite class
 *
 * @author Mathyas Giudici
 */

public class CommandCreateInviteTest extends AbsCommandTest {

    private class FakeViewAction extends ViewActions {
        private FakeViewAction(ActionSender actionSender) {
            super(actionSender);
        }

        @Override
        public String login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            assertEquals("TestPlayer", name);
            assertEquals("test", password);
            assertTrue(newUser);
            assertEquals("test", host);
            assertTrue(isRMI);
            assertEquals(-1, objectPort);
            assertEquals(80, requestPort);
            return null;
        }

        @Override
        public void pushInvite(MatchIdentifier invite) {
            assertEquals("TestPerson1", invite.player0);
            assertEquals("TestPerson2", invite.player1);
            assertEquals("TestPerson3", invite.player2);
            assertEquals("TestPlayer", invite.player3);
        }

        @Override
        public void askLobby() {
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
    public void testDoAction() throws Exception{
        String message = "y" + enter + "TestPlayer" + enter + "test" + enter + "test" + enter + "y" + enter + "80" + enter +
                "TestPerson1" + enter + "y" + enter + "TestPerson2" + enter + "y" + enter + "TestPerson3" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        app.startLogin(false);
        CommandCreateInvite commandCreateInvite = new CommandCreateInvite(app);
        commandCreateInvite.doAction();
    }
}