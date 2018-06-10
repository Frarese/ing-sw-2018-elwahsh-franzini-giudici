package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.view.tools.RoundTrackerViewCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create round tracker in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIRoundTrackerViewCreator extends RoundTrackerViewCreator<List<String>> {

    /**
     * Basic Class constructor that initializes elements at default value
     */
    public CLIRoundTrackerViewCreator() {
        super();
        this.dieViewCreator = new CLIDieViewCreator();
    }

    /**
     * Class constructor
     *
     * @param round        contains the round
     * @param roundTracker contains the round tracker
     */
    public CLIRoundTrackerViewCreator(int round, IntColorPair[][] roundTracker) {
        super(round, roundTracker);
        this.dieViewCreator = new CLIDieViewCreator();
    }

    @Override
    public List<String> display() {
        //Make an array
        ArrayList<String> strings = new ArrayList<>();

        strings.add("Siamo al turno: " + this.round);

        //Visit round tracker
        for (int i = 0; i < round && i < roundTracker.length; i++) {
            strings.add("Turno " + (i + 1) + " : ");
            if (this.roundTracker[i] != null) {
                for (int j = 0; j < this.roundTracker[i].length; j++) {
                        if(this.roundTracker[i][j] != null)
                            strings.add(j + ") " + this.dieViewCreator.makeDie(this.roundTracker[i][j]));
                }
            }
        }
        return strings;
    }
}
