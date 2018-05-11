package it.polimi.se2018.view.view_util.cli_interface;

import java.util.ArrayList;

/**
 * Class to handle print in console
 *
 * @author Mathyas Giudici
 */

public class CLIPrinter {

    /**
     * Class constructor
     */
    public CLIPrinter() {
    }

    /**
     * To print a message in System.out
     *
     * @param message contains the message to print
     */
    public void print(String message) {
        System.out.println(message);
        System.out.flush();
    }

    /**
     * To print an array of messages in Sysem.out
     *
     * @param array contains the array of messages
     */
    public void printArray(ArrayList<String> array) {
        array.forEach(this::print);
    }
}
