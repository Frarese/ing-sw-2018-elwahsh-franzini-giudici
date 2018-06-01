package it.polimi.se2018.view.tools.cli.command;

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
 * Tests for CommandShowCards class
 *
 * @author Mathyas Giudici
 */

public class CommandShowCardsTest extends AbsCommandTest {

    private class FakeViewAction extends ViewActions {

        FakeViewAction(String ownerName) {
            super(ownerName);
        }

        @Override
        public void login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            assertEquals("Test", name);
            assertEquals("test", password);
            assertTrue(newUser);
            assertEquals("test", host);
            assertFalse(isRMI);
            assertEquals(80, objectPort);
            assertEquals(70, requestPort);
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
    public void testDoAction() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "70" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        app.startLogin(false);

        List<SingleCardView> cards = new ArrayList<>();
        cards.add(new SingleCardView(10, 1));
        cards.add(new SingleCardView(12, 1));

        app.getCardViewCreator().setPrivateObjectiveCard(new SingleCardView(1, 0));
        app.getCardViewCreator().setPublicObjectiveCards(cards);
        app.getCardViewCreator().setToolCards(cards);

        CommandShowCards commandShowCards = new CommandShowCards(app);
        commandShowCards.doAction();
    }
}