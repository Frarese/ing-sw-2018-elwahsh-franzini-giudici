package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CommandWaitYourTurn class
 *
 * @author Mathyas Giudici
 */

public class CommandWaitYourTurnTest {

    @Test
    public void testMessage() {
        CommandWaitYourTurn commandWaitYourTurn = new CommandWaitYourTurn(null);

        assertEquals("wait) Aspetta il tuo turno (non verrà più mostrato il menu)", commandWaitYourTurn.display());
    }

    @Test
    public void testDoAction() {
        ByteArrayOutputStream savedStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(savedStream));

        CLIApp cliApp = new CLIApp(null, null, null);

        CommandWaitYourTurn commandWaitYourTurn = new CommandWaitYourTurn(cliApp);
        commandWaitYourTurn.doAction();

        assertEquals("Aspettiamo il tuo turno...", savedStream.toString().replaceAll("\\p{Cntrl}", ""));
    }
}