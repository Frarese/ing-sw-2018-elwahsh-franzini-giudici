package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CommandShowConnectedUser class
 *
 * @author Mathyas Giudici
 */

public class CommandShowConnectedUsersTest extends AbsCommandTest {

    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(null, null, null);
        }

        @Override
        public void menu() {
            assert true;
        }
    }

    @Test
    public void testDoAction() {
        ArrayList<ScoreEntry> arrayList = new ArrayList<>();
        arrayList.add(0, new ScoreEntry("Test", 0, 0));
        arrayList.add(1, new ScoreEntry("Test1", 1, 1));

        app = new FakeApp();
        app.pullConnectedPlayers(arrayList);

        CommandShowConnectedUsers commandShowConnectedUsers = new CommandShowConnectedUsers(app);
        commandShowConnectedUsers.doAction();

        assertEquals("Utenti connessi:", savedStream.toString().split(enter)[0]);
        assertEquals("____________________________", savedStream.toString().split(enter)[1]);
        assertEquals("0) Test", savedStream.toString().split(enter)[2]);
        assertEquals("1) Test1", savedStream.toString().split(enter)[3]);
    }
}