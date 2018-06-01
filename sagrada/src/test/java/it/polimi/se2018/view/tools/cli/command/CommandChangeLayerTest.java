package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

/**
 * Tests for CommandChangeLayer class
 *
 * @author Mathyas Giudici
 */

public class CommandChangeLayerTest extends AbsCommandTest {

    private class FakeViewAction extends ViewActions {
        private FakeViewAction(String ownerName) {
            super(ownerName);
        }

        @Override
        public void login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            assertEquals("Test", name);
            assertEquals("test", password);
            assertTrue(newUser);
            assertEquals("test", host);
            assertFalse(isRMI);
            assertEquals(90, objectPort);
            assertEquals(80, requestPort);
        }

        @Override
        public void changeLayer(boolean toRMI, int objectPort, int requestPort) {
            assertTrue(toRMI);
            assertEquals(-1, objectPort);
            assertEquals(72, requestPort);
        }
    }

    private class FakeViewAction2 extends ViewActions {
        private FakeViewAction2(String ownerName) {
            super(ownerName);
        }

        @Override
        public void login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            assertEquals("Test", name);
            assertEquals("test", password);
            assertTrue(newUser);
            assertEquals("test", host);
            assertTrue(isRMI);
            assertEquals(-1, objectPort);
            assertEquals(80, requestPort);
        }

        @Override
        public void changeLayer(boolean toRMI, int objectPort, int requestPort) {
            assertFalse(toRMI);
            assertEquals(72, objectPort);
            assertEquals(5, requestPort);
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

    private class FakeApp2 extends CLIApp {
        private FakeApp2() {
            super(new FakeViewAction2(null), new ViewToolCardActions(null), new ViewMessage(null));
        }
    }

    @Test
    public void testDoActionToSocket() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "y" + enter + "80" + enter + "y" + enter + "72" + enter + "5" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp2();
        app.startLogin(false);

        CommandChangeLayer commandChangeLayer = new CommandChangeLayer(app);
        commandChangeLayer.doAction();
    }

    @Test
    public void testDoActionToRMI() {
        String message = "y" + enter + "Test" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "90" + enter + "y" + enter + "72" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        app.startLogin(false);

        CommandChangeLayer commandChangeLayer = new CommandChangeLayer(app);
        commandChangeLayer.doAction();
    }

    @Test
    public void testNoChange() {
        String message = "n" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        app = new FakeApp();
        CommandChangeLayer commandChangeLayer = new CommandChangeLayer(app);
        commandChangeLayer.doAction();

    }
}