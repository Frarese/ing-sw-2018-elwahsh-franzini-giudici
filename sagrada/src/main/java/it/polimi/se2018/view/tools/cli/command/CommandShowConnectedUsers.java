package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

public class CommandShowConnectedUsers extends CLICommand {

    public CommandShowConnectedUsers(CLIApp app) {
        super("users) visualizza gli utenti connessi", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Utenti connessi:");
        this.app.getPrinter().print("____________________________");
        for (int i = 0; i < this.app.getConnectedUsers().size(); i++)
            this.app.getPrinter().print(i + ") " + this.app.getConnectedUsers().get(i).usn);

        //Call menu method
        this.app.menu();
    }
}

