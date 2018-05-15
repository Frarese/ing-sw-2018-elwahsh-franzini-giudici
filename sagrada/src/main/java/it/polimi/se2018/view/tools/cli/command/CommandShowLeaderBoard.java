package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

public class CommandShowLeaderBoard extends CLICommand {

    public CommandShowLeaderBoard(CLIApp app) {
        super("leader) visualizza la LeaderBoard", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Leader Board (Utente, Punti, Vittorie):");
        this.app.getPrinter().print("____________________________");
        for (int i = 0; i < this.app.getLeaderBoard().size(); i++) {
            this.app.getPrinter().print(i + ") " +
                    this.app.getLeaderBoard().get(i).usn + ", " +
                    this.app.getLeaderBoard().get(i).tot + ", " +
                    this.app.getLeaderBoard().get(i).wins);
        }
        //Call menu method
        this.app.menu();
    }
}
