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

    private String enter = System.lineSeparator();

    private CLIPrinter printer;

    private ByteArrayOutputStream savedStream;

    @Before
    public void testInit() {
        savedStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(savedStream));
        this.printer = new CLIPrinter();
    }

    @Test
    public void testPrint() {
        printer.print("Test");

        assertEquals("Test" + enter, savedStream.toString());
    }

    @Test
    public void testPrintFail() {
        printer.print(new Exception());

        assertEquals("java.lang.Exception cannot be cast to java.lang.String" + enter, savedStream.toString());
    }

    @Test
    public void testPrintArray() {
        List<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Test 1");
        stringArrayList.add("Test 2");
        printer.printArray(stringArrayList);

        assertEquals("Test 1" + enter + "Test 2" + enter, savedStream.toString());
    }

    @Test
    public void testPrintArrayFail() {
        printer.printArray(new Exception());

        assertEquals("java.lang.Exception cannot be cast to java.util.List" + enter, savedStream.toString());
    }

    @After
    public void testCloseOperation() {
        System.setOut(System.out);
        this.printer.close();
    }
}