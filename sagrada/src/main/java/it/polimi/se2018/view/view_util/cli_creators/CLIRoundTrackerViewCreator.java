package it.polimi.se2018.view.view_util.cli_creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.RoundTrackerViewCreator;

import java.util.ArrayList;

/**
 * Class to create round tracker in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIRoundTrackerViewCreator extends RoundTrackerViewCreator<ArrayList<String>> {

    /**
     * Class constructor
     * @param round       contains the round
     * @param roundTracker contains the round tracker
     */
    public CLIRoundTrackerViewCreator(int round, Pair<Integer, ColorModel>[][] roundTracker) {
        super(round, roundTracker);
        this.dieViewCreator = new CLIDieViewCreator();
    }

    @Override
    public ArrayList<String> display() {
        //Make an array
        ArrayList<String> strings = new ArrayList<>();

        strings.add("Siamo al turno: " + this.round);

        //Visit round tracker
        for (int i = 0; i < round; i++) {
            strings.add("Turno " + (i + 1) + " : ");
            for (int j = 0; j < this.roundTracker[i].length; j++) {
                strings.add((String) this.dieViewCreator.makeDie(this.roundTracker[i][j]));
            }
        }
        return strings;
    }
}
