package it.polimi.se2018.view.tools.cli.ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CLIPrinter class
 *
 * @author Mathyas Giudici
 */

public class CLIPrinterTest {

    private final String enter = System.lineSeparator();

    private CLIPrinter printer;

    private ByteArrayOutputStream savedStream;

    /**
     * Prepares System.out to be saved in a stream and creates new CLIApp
     */
    @Before
    public void testInit() {
        savedStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(savedStream));
        this.printer = new CLIPrinter();
    }

    /**
     * Restore System.out
     */
    @After
    public void testCloseOperation() {
        System.setOut(System.out);
        this.printer.close();
    }

    /**
     * Checks single line print
     */
    @Test
    public void testPrint() {
        printer.print("Test");

        assertEquals("Test" + enter, savedStream.toString());
    }

    /**
     * Checks single line print fail
     */
    @Test
    public void testPrintFail() {
        printer.print(new Exception());

        assertEquals("", savedStream.toString());
    }

    /**
     * Checks array print
     */
    @Test
    public void testPrintArray() {
        List<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Test 1");
        stringArrayList.add("Test 2");
        printer.printArray(stringArrayList);

        assertEquals("Test 1" + enter + "Test 2" + enter, savedStream.toString());
    }

    /**
     * Checks array print fail
     */
    @Test
    public void testPrintArrayFail() {
        printer.printArray(new Exception());

        assertEquals("", savedStream.toString());
    }
}