package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.util.MatchIdentifier;
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
    public List<String> display(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3) {
        ArrayList<String> strings = new ArrayList<>();
        List<ScoreEntry> scores = new ArrayList<>();

        scores.add(new ScoreEntry(matchIdentifier.player0, player0, 0));
        scores.add(new ScoreEntry(matchIdentifier.player1, player1, 0));
        if (!matchIdentifier.player2.equals("")) {
            scores.add(new ScoreEntry(matchIdentifier.player2, player2, 0));
            if (!matchIdentifier.player3.equals("")) {
                scores.add(new ScoreEntry(matchIdentifier.player3, player3, 0));
            }
        }

        for (ScoreEntry score : scores) {
            strings.add(score.usn + ", Punti " + score.tot);
        }
        return strings;
    }
}
