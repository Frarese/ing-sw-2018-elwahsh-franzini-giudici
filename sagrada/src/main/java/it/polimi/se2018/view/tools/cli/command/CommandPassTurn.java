package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

/**
 * Class to handle pass turn request
 *
 * @author Mathyas Giudici
 */

public class CommandPassTurn extends CLICommand {

    public CommandPassTurn(CLIApp app) {
        super("pass) passa il turno", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Sei sicuro di voler passare il turno?");
        if (this.app.getReader().chooseYes()) {
            this.app.getViewActions().passTurn();
            return;
        }
    }
}
