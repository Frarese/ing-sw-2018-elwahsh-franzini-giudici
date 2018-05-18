package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

/**
 * Class to handle add die in grid command
 *
 * @author Mathyas Giudici
 */

public class CommandAddDie extends CLICommand {

    public CommandAddDie(CLIApp app) {
        super("add) Prendi un dado dalla riserva e piazzalo alla tua vetrata", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Seleziona un dado dalla riserva: ");
        this.app.getPrinter().print(this.app.getReserveViewCreator().display());
        int reserveIndex = this.app.getReader().chooseInRange(0, this.app.getReserveViewCreator().getReserve().length - 1);
        this.app.getPrinter().print("Seleziona la posizione (la numerazione parte da 0)");
        this.app.getPrinter().printArray(this.app.getGridViewCreator().display());
        int width = this.app.getCoordinateX();
        int height = this.app.getCoordinateY();
        this.app.getViewActions().setDie(reserveIndex, height, width);
    }
}
