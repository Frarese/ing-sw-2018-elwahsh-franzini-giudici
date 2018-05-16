package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

public class CommandAcceptInvite extends CLICommand {

    public CommandAcceptInvite(CLIApp app) {
        super("accept) accetta un invito", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Inserire ID invito (numerazione da 0)");

        int invite = this.app.getReader().chooseInRange(0, this.app.getInvites().size() - 1);

        this.app.getViewActions().acceptInvite(this.app.getInvites().get(invite));
    }
}
