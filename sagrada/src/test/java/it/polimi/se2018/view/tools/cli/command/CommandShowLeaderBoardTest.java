package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CommandShowLeaderBoard class
 *
 * @author Mathyas Giudici
 */

public class CommandShowLeaderBoardTest extends AbsCommandTest {

    /**
     * Mock class of CLIApp object
     */
    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(null, null);
        }

        @Override
        public void menu() {
            //Nothing
        }
    }

    /**
     * Checks command perform with leader board not empty
     */
    @Test
    public void testDoAction() {
        ArrayList<ScoreEntry> arrayList = new ArrayList<>();
        arrayList.add(0, new ScoreEntry("Test", 0, 0));
        arrayList.add(1, new ScoreEntry("Test1", 1, 1));

        app = new FakeApp();
        app.pullLeaderBoard(arrayList);

        CommandShowLeaderBoard commandShowLeaderBoard = new CommandShowLeaderBoard(app);
        commandShowLeaderBoard.doAction();

        String returnString = "Leader Board (Utente, Punti, Vittorie):" + "____________________________" +
                "0) Test, 0, 0" + "1) Test1, 1, 1";

        assertEquals(returnString, savedStream.toString().replaceAll(regexControl, emptyString));
    }

    /**
     * Checks command perform with leader board empty
     */
    @Test
    public void testDoActionNothing() {
        app = new FakeApp();

        CommandShowLeaderBoard commandShowLeaderBoard = new CommandShowLeaderBoard(app);
        commandShowLeaderBoard.doAction();

        String returnString = "Leader Board al momento non disponibile";

        assertEquals(returnString, savedStream.toString().replaceAll(regexControl, emptyString));
    }
}