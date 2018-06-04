package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

/**
 * Abstract Class to handle CLI command during game
 *
 * @author Mathyas Giudici
 */

public abstract class CLICommand {

    private final String message;

    final CLIApp app;

    /**
     * Class constructor that initializes private attributes with passed attributes
     *
     * @param message contains the command text displayed
     * @param app     contains the CLIApp reference
     */
    public CLICommand(String message, CLIApp app) {
        this.message = message;
        this.app = app;
    }

    /**
     * To perform the command's action
     */
    public abstract void doAction();

    /**
     * Returns the message of the command to print in CLI' menu
     *
     * @return the message to display
     */
    public String display() {
        return this.message;
    }
}
