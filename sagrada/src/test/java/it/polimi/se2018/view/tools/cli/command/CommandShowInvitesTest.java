package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CommandShowInvites class
 *
 * @author Mathyas Giudici
 */

public class CommandShowInvitesTest extends AbsCommandTest {

    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(null, null);
        }

        @Override
        public void menu() {
            assert true;
        }
    }

    @Test
    public void testDoAction() {
        MatchIdentifier matchIdentifier1 = new MatchIdentifier("TestP0", "TestP1", "TestP2", "TestP3");
        MatchIdentifier matchIdentifier2 = new MatchIdentifier("TestP0", "Test", null, null);

        app = new FakeApp();
        app.pullInvitate(matchIdentifier1);
        app.pullInvitate(matchIdentifier2);

        CommandShowInvites commandShowInvites = new CommandShowInvites(app);
        commandShowInvites.doAction();

        String returnString = "Inviti" + "____________________________" + "0) TestP0, TestP1, TestP2, TestP3" + "1) Test, TestP0";

        assertEquals(returnString, savedStream.toString().replaceAll(regexControl, emptyString));
    }
}