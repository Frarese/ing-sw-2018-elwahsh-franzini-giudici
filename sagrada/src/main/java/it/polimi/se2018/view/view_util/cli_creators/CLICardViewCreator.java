package it.polimi.se2018.view.view_util.cli_creators;

import it.polimi.se2018.view.view_util.CardViewCreator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to create cards in CLI
 *
 * @author Mathyas Giudici
 */

public class CLICardViewCreator extends CardViewCreator<String> {

    /**
     * Class constructor
     *@param privateObjectiveCard contains the private objective
     *@param publicObjectiveCards contains the public objectives
     *@param toolCards contains the tool cards
     */
    public CLICardViewCreator(int privateObjectiveCard, int[] publicObjectiveCards, int[] toolCards) {
        super(privateObjectiveCard, publicObjectiveCards, toolCards);
    }

    @Override
    public String makeCart(int cardID) {
        //Check private objective card
        if (cardID == privateObjectiveCard) {
            return this.cardIdentifier.getCardInfo(cardID);
        }

        //Check public objective cards
        for (int publicObjectiveCard : this.publicObjectiveCards)
            if (cardID == publicObjectiveCard) {
                return this.cardIdentifier.getCardInfo(cardID);
            }

        //Check tool cards
        for (int toolCards : this.toolCards)
            if (cardID == toolCards) {
                return this.cardIdentifier.getCardInfo(cardID);
            }

        //Problems
        String message = "Qualcosa non va nella creazione ne della carta: " + cardID;
        Logger.getGlobal().log(Level.WARNING, message);
        return null;
    }
}
