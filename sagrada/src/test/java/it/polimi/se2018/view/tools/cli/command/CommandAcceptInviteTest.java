package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.controller.game.client.ActionSender;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CommandAcceptInvite class
 *
 * @author Mathyas Giudici
 */

public class CommandAcceptInviteTest extends AbsCommandTest {

    private class FakeViewAction extends ViewActions {
        private FakeViewAction(ActionSender actionSender) {
            super(actionSender);
        }

        @Override
        public void acceptInvite(MatchIdentifier matchIdentifier) {
            assertEquals("TestName1", matchIdentifier.player0);
            assertEquals("TestName2", matchIdentifier.player1);
            assertEquals("TestName3", matchIdentifier.player2);
            assertEquals("TestName4", matchIdentifier.player3);
        }
    }

    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(new FakeViewAction(null), null, null);
        }
    }

    @Test
    public void testDoAction() {
        String input = "0" + enter;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        app = new FakeApp();

        app.getInvites().add(new MatchIdentifier("TestName1", "TestName2", "TestName3", "TestName4"));

        CommandAcceptInvite commandAcceptInvite = new CommandAcceptInvite(app);
        commandAcceptInvite.doAction();

        assertEquals("Inserire ID invito (numerazione da 0)", savedStream.toString().split(regexControl)[0]);
        assertEquals("Opzione: ", savedStream.toString().split(regexControl)[1]);
    }
}