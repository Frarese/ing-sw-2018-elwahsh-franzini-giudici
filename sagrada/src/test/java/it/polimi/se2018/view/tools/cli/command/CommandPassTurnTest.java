package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.controller.game.client.ActionSender;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

public class CommandPassTurnTest extends AbsCommandTest {

    private class FakeViewAction extends ViewActions {
        private FakeViewAction(ActionSender actionSender) {
            super(actionSender);
        }

        @Override
        public void passTurn() {
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
    public void testDoActionYes() throws Exception{
        String message = "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        CommandPassTurn commandPassTurn = new CommandPassTurn(app);
        commandPassTurn.doAction();

        assertEquals("Sei sicuro di voler passare il turno?", savedStream.toString().split(regexControl)[0]);
    }

    @Test
    public void testDoActionNo() throws Exception{
        String message = "n" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        CommandPassTurn commandPassTurn = new CommandPassTurn(app);
        commandPassTurn.doAction();

        assertEquals("Sei sicuro di voler passare il turno?", savedStream.toString().split(regexControl)[0]);
    }
}