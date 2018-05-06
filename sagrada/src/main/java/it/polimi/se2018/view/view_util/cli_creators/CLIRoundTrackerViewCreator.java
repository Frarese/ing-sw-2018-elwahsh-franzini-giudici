package it.polimi.se2018.view.view_util.cli_creators;

import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.RoundTrackerViewCreator;

/**
 * Class to create round tracker in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIRoundTrackerViewCreator implements RoundTrackerViewCreator<String> {

    private CLIDieViewCreator cliDieViewCreator;

    private int round;
    private Pair roundTracker;

    @Override
    public String display() {
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
