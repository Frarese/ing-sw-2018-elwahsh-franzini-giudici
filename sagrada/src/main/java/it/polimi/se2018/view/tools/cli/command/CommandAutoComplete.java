package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

public class CommandAutoComplete extends CLICommand {

    public CommandAutoComplete(CLIApp app) {
        super("auto) autocompleta e partecipa ad una partita", app);
    }

    @Override
    public void doAction() {
        this.app.getViewActions().joinMatchMaking();
    }
}
