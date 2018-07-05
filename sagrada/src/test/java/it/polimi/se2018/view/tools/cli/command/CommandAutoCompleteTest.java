package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests for CommandAutoComplete class
 *
 * @author Mathyas Giudici
 */

public class CommandAutoCompleteTest extends AbsCommandTest {

    private boolean flag = false;

    /**
     * Mock class of ViewActions object
     */
    private class FakeViewAction extends ViewActions {
        private FakeViewAction() {
            super(null);
        }

        @Override
        public void joinMatchMaking() {
            flag = true;
        }
    }

    /**
     * Mock class of CLIApp object
     */
    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(new FakeViewAction(), null);
        }
    }

    /**
     * Checks command perform
     */
    @Test
    public void testDoAction() {
        app = new FakeApp();
        CommandAutoComplete commandAutoComplete = new CommandAutoComplete(app);
        commandAutoComplete.doAction();

        assertTrue(flag);
    }
}