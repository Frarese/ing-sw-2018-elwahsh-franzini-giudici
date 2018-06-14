package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.tools.CardViewCreator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to create cards in CLI
 *
 * @author Mathyas Giudici
 */

public class CLICardViewCreator extends CardViewCreator<String> {

    /**
     * Basic Class constructor that initializes elements at default value
     */
    public CLICardViewCreator() {
        super();
    }

    /**
     * Class constructor
     *
     * @param privateObjectiveCard contains the private objective
     * @param publicObjectiveCards contains the public objectives
     * @param toolCards            contains the tool cards
     */
    CLICardViewCreator(SingleCardView privateObjectiveCard, List<SingleCardView> publicObjectiveCards, List<SingleCardView> toolCards) {
        super(privateObjectiveCard, publicObjectiveCards, toolCards);
    }

    @Override
    public String makeCard(int cardID) {
        //Check private objective card
        if (cardID == privateObjectiveCard.cardID) {
            return this.cardIdentifier.getCardInfo(cardID);
        }

        //Check public objective cards
        for (SingleCardView publicObjectiveCard : this.publicObjectiveCards) {
            if (cardID == publicObjectiveCard.cardID) {
                return this.cardIdentifier.getCardInfo(cardID);
            }
        }
        //Check tool cards
        for (SingleCardView toolCard : this.toolCards) {
            if (cardID == toolCard.cardID) {
                return this.cardIdentifier.getCardInfo(cardID);
            }
        }

        //Problems
        String message = "Qualcosa non va nella creazione ne della carta: " + cardID;
        Logger.getGlobal().log(Level.WARNING, message);
        return null;
    }
}
