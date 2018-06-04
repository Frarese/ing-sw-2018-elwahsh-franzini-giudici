package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.controller.game.client.ActionSender;
import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for CommandUseTool class
 *
 * @author Mathyas Giudici
 */

public class CommandUseToolTest extends AbsCommandTest {

    private class FakeViewAction extends ViewActions {

        private FakeViewAction(ActionSender actionSender) {
            super(actionSender);
        }

        @Override
        public String login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            assertEquals("Test", name);
            assertEquals("test", password);
            assertTrue(newUser);
            assertEquals("test", host);
            assertFalse(isRMI);
            assertEquals(80, objectPort);
            assertEquals(1, requestPort);
            return null;
        }

        @Override
        public void askLobby() {
            assert true;
        }

        @Override
        public void useToolCard(int card) {
            assertEquals(20,card);
        }
    }

    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(new FakeViewAction(null), new ViewToolCardActions(null), new ViewMessage(null));
        }
    }

    @Test
    public void testDoAction() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "1" + enter + "80" + enter + "0" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        app.startLogin(false);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(20, 1));
        cards.add(new SingleCardView(25, 1));

        app.getCardViewCreator().setPrivateObjectiveCard(new SingleCardView(1,1));
        app.getCardViewCreator().setPublicObjectiveCards(cards);
        app.getCardViewCreator().setToolCards(cards);

        CommandUseTool commandUseTool = new CommandUseTool(app);
        commandUseTool.doAction();
    }
}