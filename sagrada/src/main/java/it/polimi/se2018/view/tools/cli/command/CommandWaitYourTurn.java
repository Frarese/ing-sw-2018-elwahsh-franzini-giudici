package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

/**
 * Class to handle wait turn request
 *
 * @author Mathyas Giudici
 */

public class CommandWaitYourTurn extends CLICommand {

    public CommandWaitYourTurn(CLIApp app) {
        super("wait) Aspetta il tuo turno (non verra\' piu\' mostrato il menu\'", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Aspettiamo il tuo turno...");
    }
}

