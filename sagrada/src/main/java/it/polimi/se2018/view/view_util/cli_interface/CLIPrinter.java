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
        super();
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
        System.out.println(message);
        System.out.flush();
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
}
