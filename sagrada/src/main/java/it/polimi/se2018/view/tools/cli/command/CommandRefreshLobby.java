package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

import java.io.IOException;

/**
 * Class to handle lobby refresh request
 *
 * @author Mathyas Giudici
 */

public class CommandRefreshLobby extends CLICommand {

    public CommandRefreshLobby(CLIApp app) {
        super("refresh) comando per ricaricare la lobby", app);
    }

    @Override
    public void doAction() throws IOException {
        this.app.getPrinter().print("Sei sicuro di voler ricaricare la lobby?");
        if (this.app.getReader().chooseYes()) {
            this.app.getViewActions().askLobby();
            new Thread(this.app::createLobby).start();
        } else {
            this.app.menu();
        }
    }
}
