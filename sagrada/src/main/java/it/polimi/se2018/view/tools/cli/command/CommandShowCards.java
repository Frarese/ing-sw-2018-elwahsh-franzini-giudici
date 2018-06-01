package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.app.CLIApp;

/**
 * Class to handle show cards in board
 *
 * @author Mathyas Giudici
 */

public class CommandShowCards extends CLICommand {

    public CommandShowCards(CLIApp app) {
        super("card) visualizza Carte sul tavolo", app);
    }

    @Override
    public void doAction() {

        this.app.getPrinter().print("Carta Obiettivo privato:");
        this.app.getPrinter().print(this.app.getCardViewCreator().makeCard(this.app.getCardViewCreator().getPrivateObjectiveCard().cardID));

        this.app.getPrinter().print("Carte Obiettivo pubblico:");
        for (int i = 0; i < this.app.getCardViewCreator().getPublicObjectiveCards().size(); i++) {
            SingleCardView el = (SingleCardView) this.app.getCardViewCreator().getPublicObjectiveCards().get(i);
            this.app.getPrinter().print(i + ") " + this.app.getCardViewCreator().makeCard(el.cardID));
        }

        this.app.getPrinter().print("Carte Utensile:");
        for (int i = 0; i < this.app.getCardViewCreator().getToolCards().size(); i++) {
            SingleCardView el = (SingleCardView) this.app.getCardViewCreator().getToolCards().get(i);
            this.app.getPrinter().print(i + ") " + this.app.getCardViewCreator().makeCard(el.cardID) + " prezzo: " + this.app.getCardViewCreator().makeCard(el.cardCost));
        }

        this.app.menu();
    }
}
