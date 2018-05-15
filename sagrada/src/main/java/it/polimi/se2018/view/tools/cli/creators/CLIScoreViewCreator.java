package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.tools.ScoreViewCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create score visualizer in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIScoreViewCreator implements ScoreViewCreator<List<String>> {

    /**
     * Class constructor
     */
    public CLIScoreViewCreator() {
        super();
    }

    @Override
    public List<String> display(List<ScoreEntry> scores) {
        ArrayList<String> strings = new ArrayList<>();
        for (ScoreEntry score : scores) {
            strings.add(score.usn + ", Punti " + score.tot);
        }
        return strings;
    }
}
