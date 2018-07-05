package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.ViewActions;
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

    /**
     * Mock class of ViewActions object
     */
    private class FakeViewAction extends ViewActions {

        private FakeViewAction() {
            super(null);
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
            //Nothing
        }

        @Override
        public void useToolCard(int card) {
            assertEquals(20, card);
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
            //Nothing
        }
    }

    /**
     * Checks command perform
     *
     * @throws Exception if an error occurs during reading
     */
    @Test
    public void testDoAction() throws Exception{
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "1" + enter + "80" + enter + 0 + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        app.startLogin(false);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(20, 1));
        cards.add(new SingleCardView(25, 1));

        app.getCardViewCreator().setPrivateObjectiveCard(new SingleCardView(1, 1));
        app.getCardViewCreator().setPublicObjectiveCards(cards);
        app.getCardViewCreator().setToolCards(cards);

        CommandUseTool commandUseTool = new CommandUseTool(app);
        commandUseTool.doAction();
    }
}