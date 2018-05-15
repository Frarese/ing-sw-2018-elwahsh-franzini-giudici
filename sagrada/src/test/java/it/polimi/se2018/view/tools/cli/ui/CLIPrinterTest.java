package it.polimi.se2018.view.tools.cli.ui;

import org.junit.Test;

/**
 * Tests for CLIPrinter class
 *
 * @author Mathyas Giudici
 */

public class CLIPrinterTest {

    private CLIPrinter printer;

    public CLIPrinterTest() {
        this.printer = new CLIPrinter();
    }

    @Test
    public void printTest() {
        printer.print(new Object());
    }

    @Test
    public void printArrayTest() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Test 1");
        stringBuilder.append("Test 2");
        printer.printArray(stringBuilder);
    }
}