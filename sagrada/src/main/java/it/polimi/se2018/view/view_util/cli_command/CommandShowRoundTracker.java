package it.polimi.se2018.view.view_util.cli_command;

import it.polimi.se2018.view.app.CLIApp;

/**
 * Class to handle show Round Tracker request
 *
 * @author Mathyas Giudici
 */

public class CommandShowRoundTracker extends CLICommand {

    public CommandShowRoundTracker(CLIApp app) {
        super("round) visualizza Round Tracker", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().printArray(this.app.getRoundTrackerViewCreator().display());

        //Call menu method
        this.app.menu();
    }
}
