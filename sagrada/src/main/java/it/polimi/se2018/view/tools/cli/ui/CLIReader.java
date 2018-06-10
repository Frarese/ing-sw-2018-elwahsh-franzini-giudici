package it.polimi.se2018.view.tools.cli.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to handle input from console
 *
 * @author Mathyas Giudici
 */

public class CLIReader {

    private final BufferedReader scanner;
    private final CLIPrinter printer;
    private static final String ERROR_MESSAGE = "Risposta non valida, riprovare";
    private final AtomicBoolean interrupt;
    private final Logger logger;
    private final Semaphore lock;
    /**
     * Class constructor
     */
    public CLIReader(CLIPrinter printer) {
        scanner = new BufferedReader(new InputStreamReader(System.in));
        this.printer = printer;
        interrupt=new AtomicBoolean(false);
        logger=Logger.getGlobal();
        lock=new Semaphore(1,true);
    }

    /**
     * To read a String in System.in
     *
     * @return the result string
     */
    public String read() throws IOException{
        try{
            if(!lock.tryAcquire()){
                logger.log(Level.WARNING,"Error reading, another thread is already inside the lock");
                throw new IOException("Error reading, already locked");
            }
            interrupt.set(false);
            while(!scanner.ready()){
                if(interrupt.get()){
                    lock.release();
                    throw new IOException("Error reading, interrupted");
                }
                Thread.sleep(1);
            }
            String message=scanner.readLine();
            lock.release();
            return message;
        } catch (InterruptedException e) {
            lock.release();
            logger.log(Level.WARNING,"Interrupted reading from console "+e);
            Thread.currentThread().interrupt();
        }
        throw new IOException("Unknown Error reading");
    }

    /**
     * To read a int number in System.in
     *
     * @return the result number
     */
    public synchronized int readInt() throws IOException {
        int number;
        try {
            number = Integer.parseInt(read());
        } catch (NumberFormatException e) {
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
    public synchronized boolean chooseYes() throws IOException {
        //Ask option
        printer.print("[Y=si, N= no]");
        String response = read();

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
    public synchronized int chooseInRange(int minValue, int maxValue) throws IOException {
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
    public synchronized int chooseBetweenTwo(int low, int high) throws IOException {
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
            return chooseInRange(low, high);
        }
    }

    /**
     * Closes the input reader
     */
    public synchronized void close() {
        try {
            scanner.close();
        } catch (IOException e) {
            logger.log(Level.WARNING,"Error closing input "+e.getMessage());
        }
    }

    public void interrupt(){
        interrupt.set(true);
    }
}
