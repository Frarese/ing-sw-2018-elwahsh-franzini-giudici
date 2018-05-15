package it.polimi.se2018.view.view_util.cli_command;

import it.polimi.se2018.view.app.CLIApp;

public class CommandShowInvites extends CLICommand {

    public CommandShowInvites(CLIApp app) {
        super("invites) visualizza i tuoi inviti", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Inviti");
        this.app.getPrinter().print("____________________________");
        for (int i = 0; i < this.app.getInvites().size(); i++) {
            String list = "";
            if (this.app.getInvites().get(i).player0 != null) {
                list.concat(this.app.getInvites().get(i).player0 + " ,");
            }
            if (this.app.getInvites().get(i).player1 != null) {
                list.concat(this.app.getInvites().get(i).player1 + " ,");
            }
            if (this.app.getInvites().get(i).player2 != null) {
                list.concat(this.app.getInvites().get(i).player2 + " ,");
            }

            this.app.getPrinter().print(i + ") " + list);
        }
        //Call menu method
        this.app.menu();
    }
}
