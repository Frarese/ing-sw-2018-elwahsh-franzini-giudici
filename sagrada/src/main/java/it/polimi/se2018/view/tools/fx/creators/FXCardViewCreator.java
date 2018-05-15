package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.view.tools.CardViewCreator;
import javafx.scene.image.Image;

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
    public Image makeCart(int cardID) {
        throw new UnsupportedOperationException();
    }
}
