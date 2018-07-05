package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;
import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Abstract class for CLICommands tests
 *
 * @author Mathyas Giudici
 */

public abstract class AbsCommandTest {

    final String enter = System.lineSeparator();

    final String regexControl = "\\p{Cntrl}";

    final String emptyString = "";


    ByteArrayOutputStream savedStream;
    CLIApp app;

    /**
     * Prepares System.out to be saved in a stream
     */
    @Before
    public void testInit() {
        savedStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(savedStream));
    }

    /**
     * Restore System.in and System.out
     */
    @After
    public void testClose() {
        System.setIn(System.in);
        System.setOut(System.out);
    }
}