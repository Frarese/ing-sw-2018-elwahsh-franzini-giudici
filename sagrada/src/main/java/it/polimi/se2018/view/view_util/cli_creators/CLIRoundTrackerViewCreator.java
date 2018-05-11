package it.polimi.se2018.view.view_util.cli_creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.RoundTrackerViewCreator;

/**
 * Class to create round tracker in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIRoundTrackerViewCreator extends RoundTrackerViewCreator<String> {

    /**
     * Class constructor
     *
     * {@link RoundTrackerViewCreator}
     */
    public CLIRoundTrackerViewCreator(int round, Pair<Integer, ColorModel>[][] rondTracker) {
        super(round, rondTracker);
        this.dieViewCreator = new CLIDieViewCreator();
    }

    @Override
    public String display() {
        //TODO
        return null;
    }
}
