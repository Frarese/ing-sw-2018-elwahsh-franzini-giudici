package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.app.CLIApp;

import java.io.IOException;

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
    public void doAction() throws IOException {
        for (int i = 0; i < this.app.getCardViewCreator().getToolCards().size(); i++) {
            SingleCardView el = (SingleCardView) this.app.getCardViewCreator().getToolCards().get(i);
            this.app.getPrinter().print(i + ") " + this.app.getCardViewCreator().makeCard(el.cardID) + " prezzo: " + this.app.getCardViewCreator().makeCard(el.cardCost));
        }
        this.app.getPrinter().print("Seleziona il numero della carta");
        int card = this.app.getReader().readInt();
        SingleCardView singleCardView = (SingleCardView) this.app.getCardViewCreator().getToolCards().get(card);
        this.app.getViewActions().useToolCard(singleCardView.cardID);
    }
}
