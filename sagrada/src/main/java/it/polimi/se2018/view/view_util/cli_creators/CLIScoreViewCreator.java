package it.polimi.se2018.view.view_util.cli_creators;

import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.view_util.ScoreViewCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create score visualizer in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIScoreViewCreator extends ScoreViewCreator<ArrayList<String>> {

    @Override
    public ArrayList<String> display(List<ScoreEntry> scores) {
        ArrayList<String> strings = new ArrayList<>();
        for (ScoreEntry score : scores) {
            strings.add(score.usn + ", Punti " + score.tot);
        }
        return strings;
    }
}
