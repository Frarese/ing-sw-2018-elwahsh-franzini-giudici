package it.polimi.se2018.view.tools.cli.ui;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class to handle print in console
 *
 * @author Mathyas Giudici
 */

public class CLIPrinter {

    private PrintWriter printWriter;

    /**
     * Class constructor
     */
    public CLIPrinter() {
        this.printWriter = new PrintWriter(System.out);
    }

    /**
     * To print a message in System.out
     *
     * @param input contains the message to print
     */
    public void print(Object input) {
        String message;
        try {
            message = (String) input;
        } catch (Exception e) {
            message = input.toString();
        }
        printWriter.println(message);
        printWriter.flush();
    }

    /**
     * To print an array of messages in System.out
     *
     * @param input contains the array of messages
     */
    public void printArray(Object input) {
        ArrayList<String> array;
        try {
            array = (ArrayList<String>) input;
            array.forEach(this::print);
        } catch (Exception e) {
            print(input.toString());
        }
    }

    /**
     * To close the input reader
     */
    public void close() {
        printWriter.close();
    }
}
