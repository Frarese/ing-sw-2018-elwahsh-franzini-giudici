package it.polimi.se2018.view.view_util.fx_creators;

import it.polimi.se2018.view.view_util.CardViewCreator;
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
     * {@link CardViewCreator}
     */
    public FXCardViewCreator(int privateObjectiveCard, int[] publicObjectiveCards, int[] toolCards) {
        super(privateObjectiveCard, publicObjectiveCards, toolCards);
    }

    @Override
    public Image makeCart(int cardID) {
        //TODO
        return null;
    }
}
