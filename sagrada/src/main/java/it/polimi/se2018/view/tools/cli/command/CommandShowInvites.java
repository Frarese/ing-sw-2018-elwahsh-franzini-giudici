package it.polimi.se2018.view.tools.cli.command;

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
            StringBuilder list = new StringBuilder();
            list.append(this.app.getInvites().get(i).player0).append(", ").append(this.app.getInvites().get(i).player1);
            if (!this.app.getInvites().get(i).player2.equals("")) {
                list.append(", ").append(this.app.getInvites().get(i).player2);
            }
            if (!this.app.getInvites().get(i).player3.equals("")) {
                list.append(", ").append(this.app.getInvites().get(i).player3);
            }

            this.app.getPrinter().print(i + ") " + list);
        }
        //Call menu method
        this.app.menu();
    }
}
