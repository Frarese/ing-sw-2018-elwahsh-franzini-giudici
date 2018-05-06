package it.polimi.se2018.view.view_util.fx_creators;

import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.RoundTrackerViewCreator;
import javafx.scene.Group;

/**
 * Class to create round tracker in GUI
 *
 * @author Mathyas Giudici
 */

public class FXRoundTrackerViewCreator implements RoundTrackerViewCreator<Group> {

    private FXDieViewCreator fxDieViewCreator;

    private int round;
    private Pair roundTracker;

    @Override
    public Group display() {
        //TODO
        return null;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Pair getRoundTracker() {
        return roundTracker;
    }

    public void setRoundTracker(Pair roundTracker) {
        this.roundTracker = roundTracker;
    }
}
