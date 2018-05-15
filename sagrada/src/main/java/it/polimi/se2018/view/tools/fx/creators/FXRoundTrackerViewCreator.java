package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.RoundTrackerViewCreator;
import javafx.scene.image.Image;

/**
 * Class to create round tracker in GUI
 *
 * @author Mathyas Giudici
 */

public class FXRoundTrackerViewCreator extends RoundTrackerViewCreator<Image> {

    /**
     * Class constructor
     *
     * @param round        contains the round
     * @param roundTracker contains the round tracker
     */
    public FXRoundTrackerViewCreator(int round, Pair<Integer, ColorModel>[][] roundTracker) {
        super(round, roundTracker);
        this.dieViewCreator = new FXDieViewCreator();
    }

    @Override
    public Image display() {
        throw new UnsupportedOperationException();
    }
}
