package it.polimi.se2018.view.view_util.cli_command;

import it.polimi.se2018.view.app.CLIApp;

/**
 * Class to handle logout command
 *
 * @author Mathyas Giudici
 */

public class CommandLogout extends CLICommand {

    public CommandLogout(CLIApp app) {
        super("logout) comando per uscire dal gioco", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Sei sicuro di voler uscire dal gioco?");
        if (this.app.getReader().chooseYes()) {
            this.app.getViewActions().logout();
        }
    }
}
