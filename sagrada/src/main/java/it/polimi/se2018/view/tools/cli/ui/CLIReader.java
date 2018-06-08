package it.polimi.se2018.view.tools.cli.ui;

import java.util.Scanner;

/**
 * Class to handle input from console
 *
 * @author Mathyas Giudici
 */

public class CLIReader {

    private final Scanner scanner;

    private final CLIPrinter printer;

    private static final String ERROR_MESSAGE = "Risposta non valida, riprovare";

    /**
     * Class constructor
     */
    public CLIReader(CLIPrinter printer) {
        scanner = new Scanner(System.in);
        this.printer = printer;
    }

    /**
     * To read a String in System.in
     *
     * @return the result string
     */
    public synchronized String read() {
        String message = scanner.next();
        reset();
        return message;
    }

    /**
     * To read a int number in System.in
     *
     * @return the result number
     */
    public synchronized int readInt() {
        int number;
        try {
            number = scanner.nextInt();
            reset();
        } catch (Exception e) {
            printer.print("Non hai inserito un numero, riprova");
            scanner.nextLine();
            number = readInt();
        }

        return number;
    }

    /**
     * Yes or no question
     *
     * @return the result
     */
    public synchronized boolean chooseYes() {
        //Ask option
        printer.print("[Y=si, N= no]");
        String response = scanner.next();
        reset();

        //Upper case
        response = response.toUpperCase();

        //Check option
        if (response.equals("Y")) {
            //Valid -> yes
            return true;
        } else {
            if (response.equals("N")) {
                //Valid -> no
                return false;
            } else {
                //Invalid option, recall
                printer.print(ERROR_MESSAGE);
                scanner.nextLine();
                return chooseYes();
            }
        }

    }

    /**
     * Choose int value in a range
     *
     * @param minValue contains the min value of the range
     * @param maxValue contains the max value of the range
     * @return the result of the choice
     */
    public synchronized int chooseInRange(int minValue, int maxValue) {
        //Ask option
        printer.print("Opzione: ");
        int response = readInt();

        //Check option
        if (response >= minValue && response <= maxValue) {
            //Valid option
            return response;
        } else {
            //Invalid option, recall
            printer.print(ERROR_MESSAGE);
            scanner.nextLine();
            return chooseInRange(minValue, maxValue);
        }
    }

    /**
     * Choose between two values
     *
     * @param low  contains lower value
     * @param high contains higher value
     * @return the user choice
     */
    public synchronized int chooseBetweenTwo(int low, int high) {
        //Ask option
        printer.print("Opzione: ");
        int response = readInt();

        //Check option
        if (response == low || response == high) {
            //Valid option
            return response;
        } else {
            //Invalid option, recall
            printer.print(ERROR_MESSAGE);
            scanner.nextLine();
            return chooseInRange(low, high);
        }
    }

    /**
     * Resets the input reader
     */
    public synchronized void reset() {
        scanner.reset();
    }

    /**
     * Closes the input reader
     */
    public synchronized void close() {
        scanner.reset();
        scanner.close();
    }
}
