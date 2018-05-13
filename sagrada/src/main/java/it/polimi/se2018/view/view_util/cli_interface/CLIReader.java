package it.polimi.se2018.view.view_util.cli_interface;

import java.util.Scanner;

/**
 * Class to handle input from console
 *
 * @author Mathyas Giudici
 */

public class CLIReader {

    private Scanner scanner;
    private CLIPrinter printer;

    /**
     * Class constructor
     */
    public CLIReader() {
        scanner = new Scanner(System.in);
        printer = new CLIPrinter();
    }

    /**
     * To read a String in System.in
     *
     * @return the result string
     */
    public String read() {
        //Print message
        return scanner.next();
    }

    /**
     * To read a int number in System.in
     *
     * @return the result number
     */
    public int readInt() {
        int number;
        try {
            number = scanner.nextInt();
        }
        catch (Exception e){
            printer.print("Non hai inserito un numero, riprova");
            number = readInt();
        }

        return number;
    }

    /**
     * Yes or no question
     *
     * @return the result
     */
    public boolean chooseYes() {
        //Ask option
        printer.print("[Y=si, N= no] ");
        String response = scanner.next();

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
                printer.print("Risposta non valida, riprovare");
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
    public int choose(int minValue, int maxValue){
        //Ask option
        printer.print("Opzione: ");
        int response = readInt();

        //Check option
        if (response>=minValue && response <= maxValue){
            //Valid option
            return response;
        }
        else{
            //Invalid option, recall
            printer.print("Risposta non valida, riprovare");
            return choose(minValue, maxValue);
        }
    }

    /**
     * To close the input reader
     */
    public void close() {
        scanner.close();
    }
}
