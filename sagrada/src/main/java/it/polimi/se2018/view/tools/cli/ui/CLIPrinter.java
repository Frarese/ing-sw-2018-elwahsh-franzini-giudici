package it.polimi.se2018.view.tools.cli.ui;

import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to handle print in console
 *
 * @author Mathyas Giudici
 */


public class CLIPrinter {

    private final PrintWriter printWriter;

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
    public synchronized void print(Object input) {
        String message;
        try {
            message = (String) input;
            printWriter.println(message);
            printWriter.flush();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * To print an array of messages in System.out
     *
     * @param input contains the array of messages
     */
    public synchronized void printArray(Object input) {
        List<String> array;
        try {
            //noinspection unchecked
            array = (List<String>) input;
            array.forEach(this::print);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * To close the input reader
     */
    public synchronized void close() {
        printWriter.close();
    }
}
