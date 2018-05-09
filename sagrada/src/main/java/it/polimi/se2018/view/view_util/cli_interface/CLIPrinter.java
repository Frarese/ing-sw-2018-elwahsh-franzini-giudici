package it.polimi.se2018.view.view_util.cli_interface;

/**
 * Class to handle print in console
 *
 * @author Mathyas Giudici
 */

public class CLIPrinter {

    public CLIPrinter() {
    }

    public void print(String message){
        System.out.println(message);
        System.out.flush();
    }
}
