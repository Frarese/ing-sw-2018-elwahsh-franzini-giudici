package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.ScoreViewCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create score visualizer in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIScoreViewCreator extends ScoreViewCreator<List<String>> {

    /**
     * Class constructor
     */
    public CLIScoreViewCreator() {
        super();
    }

    @Override
    public List<String> display(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3) {

        this.createOrderList(matchIdentifier, player0, player1, player2, player3);

        ArrayList<String> strings = new ArrayList<>();

        for (Pair<String, Integer> score : scores) {
            strings.add(score.getFirst() + ", Punti " + score.getSecond());
        }
        return strings;
    }
}
