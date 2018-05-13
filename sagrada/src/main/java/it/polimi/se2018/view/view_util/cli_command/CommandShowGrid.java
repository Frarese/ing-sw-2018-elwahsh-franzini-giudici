package it.polimi.se2018.view.view_util.cli_command;

import it.polimi.se2018.view.app.CLIApp;
import it.polimi.se2018.view.view_util.cli_creators.CLIGridViewCreator;

/**
 * Class to handle show grid request
 *
 * @author Mathyas Giudici
 */

public class CommandShowGrid extends CLICommand {

    public CommandShowGrid(CLIApp app) {
        super("show) visualizza Vetrata", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Vuoi visualizzare la tua vetrata?");
        if (this.app.getReader().chooseYes()) {
            this.app.getPrinter().printArray(this.app.getGridViewCreator().display());
        } else {
            for (int i = 0; i < this.app.getPlayers().size(); i++) {
                this.app.getPrinter().print(i + ") " + this.app.getPlayers().get(i).getPlayerName());
                this.app.getPrinter().print("Seleziona il giocatore");
                int player = this.app.getReader().choose(0, this.app.getPlayers().size() - 1);
                CLIGridViewCreator cliGridViewCreator = new CLIGridViewCreator(
                        this.app.getPlayers().get(player).getPlayerGrid(),
                        this.app.getPlayers().get(player).getPlayerTemplate());
                this.app.getPrinter().printArray(cliGridViewCreator.display());
            }
        }
        this.app.menuTurnControl();
    }
}
