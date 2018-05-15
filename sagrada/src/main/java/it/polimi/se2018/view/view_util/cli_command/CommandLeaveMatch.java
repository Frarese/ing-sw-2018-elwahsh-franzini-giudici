package it.polimi.se2018.view.view_util.cli_command;

import it.polimi.se2018.view.app.CLIApp;

public class CommandLeaveMatch extends CLICommand {

    public CommandLeaveMatch(CLIApp app) {
        super("leave) comando per lasciare la partita a cui stai partecipando", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Sei sicuro di voler lasciare la partita?");
        if (this.app.getReader().chooseYes()) {
            this.app.getViewActions().leaveMatch();
        }
    }
}
