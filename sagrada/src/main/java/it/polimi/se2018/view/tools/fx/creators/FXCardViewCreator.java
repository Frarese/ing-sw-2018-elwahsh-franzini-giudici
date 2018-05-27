package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.view.tools.CardViewCreator;
import javafx.scene.image.Image;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to create cards in GUI
 *
 * @author Mathyas Giudici
 */

public class FXCardViewCreator extends CardViewCreator<Image> {

    /**
     * Class constructor
     *
     * @param privateObjectiveCard contains the private objective
     * @param publicObjectiveCards contains the public objectives
     * @param toolCards            contains the tool cards
     */
    public FXCardViewCreator(int privateObjectiveCard, int[] publicObjectiveCards, int[] toolCards) {
        super(privateObjectiveCard, publicObjectiveCards, toolCards);
    }

    @Override
    public Image makeCard(int cardID) {
        //Check private objective card
        if (cardID == privateObjectiveCard) {
            String url = "/it/polimi/se2018/view/images/private_cards/privateObjective" + cardID + ".jpg";
            return new Image(url);
        }

        //Check public objective cards
        for (int publicObjectiveCard : this.publicObjectiveCards)
            if (cardID == publicObjectiveCard) {
                int card = cardID - 10;
                String url = "/it/polimi/se2018/view/images/public_cards/publicObjective" + card + ".jpg";
                return new Image(url);
            }

        //Check tool cards
        for (int toolCards : this.toolCards)
            if (cardID == toolCards) {
                int card = cardID - 20;
                String url = "/it/polimi/se2018/view/images/tool_cards/toolCard" + card + ".jpg";
                return new Image(url);
            }

        //Problems
        String message = "Qualcosa non va nella creazione ne della carta: " + cardID;
        Logger.getGlobal().log(Level.WARNING, message);
        return null;

    }
}
