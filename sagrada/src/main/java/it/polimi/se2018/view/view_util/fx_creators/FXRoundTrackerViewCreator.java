package it.polimi.se2018.view.view_util.fx_creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.RoundTrackerViewCreator;
import javafx.scene.image.Image;

/**
 * Class to create round tracker in GUI
 *
 * @author Mathyas Giudici
 */

public class FXRoundTrackerViewCreator extends RoundTrackerViewCreator<Image> {

    /**
     * Class constructor
     * <p>
     * {@link RoundTrackerViewCreator}
     */
    public FXRoundTrackerViewCreator(int round, Pair<Integer, ColorModel>[][] rondTracker) {
        super(round, rondTracker);
        this.dieViewCreator = new FXDieViewCreator();
    }

    @Override
    public Image display() {
        //TODO
        return null;
    }
}
