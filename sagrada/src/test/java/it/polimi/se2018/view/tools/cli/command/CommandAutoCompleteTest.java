package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

public class CommandAutoCompleteTest extends AbsCommandTest {

    private class FakeViewAction extends ViewActions {
        private FakeViewAction(String ownerName) {
            super(ownerName);
        }

        @Override
        public void autoCompleteGame() {
            assert true;
        }
    }

    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(new FakeViewAction(null), null, null);
        }
    }

    @Test
    public void testDoAction() {
        app = new FakeApp();
        CommandAutoComplete commandAutoComplete = new CommandAutoComplete(app);
        commandAutoComplete.doAction();
    }
}