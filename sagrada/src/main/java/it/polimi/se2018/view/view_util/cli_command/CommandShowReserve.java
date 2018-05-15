package it.polimi.se2018.view.view_util.cli_command;

import it.polimi.se2018.view.app.CLIApp;

/**
 * Class to handle show Reserve request
 *
 * @author Mathyas Giudici
 */

public class CommandShowReserve extends CLICommand {

    public CommandShowReserve(CLIApp app) {
        super("ris) visualizza la Riserva", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print(this.app.getReserveViewCreator().display());

        //Call menu method
        this.app.menu();
    }
}
