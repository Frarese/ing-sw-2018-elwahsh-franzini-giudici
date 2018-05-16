package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
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
     * Class constructor
     *
     * @param round        contains the round
     * @param roundTracker contains the round tracker
     */
    public CLIRoundTrackerViewCreator(int round, Pair<Integer, ColorModel>[][] roundTracker) {
        super(round, roundTracker);
        this.dieViewCreator = new CLIDieViewCreator();
    }

    @Override
    public List<String> display() {
        //Make an array
        ArrayList<String> strings = new ArrayList<>();

        strings.add("Siamo al turno: " + this.round);

        //Visit round tracker
        for (int i = 0; i < round; i++) {
            strings.add("Turno " + (i + 1) + " : ");
            for (int j = 0; j < this.roundTracker[i].length; j++) {
                strings.add(j + ") " + this.dieViewCreator.makeDie(this.roundTracker[i][j]));
            }
        }
        return strings;
    }
}
