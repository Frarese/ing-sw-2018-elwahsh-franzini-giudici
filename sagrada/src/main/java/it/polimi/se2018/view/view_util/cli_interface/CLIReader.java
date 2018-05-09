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

    public CLIReader() {
        scanner = new Scanner(System.in);
        printer = new CLIPrinter();
    }

    public String read(){
        String message = scanner.next();
        return message;
    }

    public int readInt(){
        int number = scanner.nextInt();
        return number;
    }

    public boolean choise(){
        printer.print("[Y=si, N= no] ");
        String response = scanner.next();
        if (response.equals("Y")){
            return true;
        }else{
            if (response.equals("N")){
                return false;
            }
            else{
                printer.print("Risposta non valida, riprovare");
                return choise();
            }
        }

    }

    public void close(){
        scanner.close();
    }
}
