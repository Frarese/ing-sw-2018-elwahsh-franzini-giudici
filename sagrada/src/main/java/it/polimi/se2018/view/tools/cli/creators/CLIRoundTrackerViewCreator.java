package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.dice.RoundTracker;
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
        for (int i = 0; i < round && i < RoundTracker.ROUNDS; i++) {
            if (this.roundTracker[0][i] != null) {
                strings.add("Turno " + (i + 1) + " : ");
                for (int j = 0; j < MAX_DIE_IN_ROUNDS; j++) {
                    if (this.roundTracker[j][i] != null)
                        strings.add(j + ") " + this.dieViewCreator.makeDie(this.roundTracker[j][i]));
                }
            }
        }
        return strings;
    }
}
