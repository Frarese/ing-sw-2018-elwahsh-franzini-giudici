package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

/**
 * Class to handle layer's change command
 *
 * @author Mathyas Giudici
 */

public class CommandChangeLayer extends CLICommand {

    public CommandChangeLayer(CLIApp app) {
        super("layer) comando per cambiare tipo di connessione", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Sei sicuro di voler cambiare tipo di connessione?");
        if (this.app.getReader().chooseYes()) {
            if (this.app.isUseRMI()) {
                this.app.getPrinter().print("Inserire object port: ");
                int objectPort = this.app.getReader().readInt();
                this.app.getPrinter().print("Inserire request port: ");
                int requestPort = this.app.getReader().readInt();
                this.app.getViewActions().changeLayer(false, objectPort, requestPort);
            } else {
                this.app.getPrinter().print("Inserire object port: ");
                int objectPort = this.app.getReader().readInt();
                this.app.getViewActions().changeLayer(true, objectPort, 0);
            }
        }

    }
}

