package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.view.app.CLIApp;

/**
 * Class to handle show Reserve request
 *
 * @author Mathyas Giudici
 */

public class CommandUseTool extends CLICommand {

    public CommandUseTool(CLIApp app) {
        super("tool) Usa una carta utensile", app);
    }

    @Override
    public void doAction() {
        for (int el : this.app.getCardViewCreator().getToolCards()) {
            this.app.getPrinter().print(el + ") " + this.app.getCardViewCreator().makeCard(el));
        }
        this.app.getPrinter().print("Seleziona il numero della carta");
        int card = this.app.getReader().readInt();

        this.app.getViewActions().useToolCard(card);
    }
}
