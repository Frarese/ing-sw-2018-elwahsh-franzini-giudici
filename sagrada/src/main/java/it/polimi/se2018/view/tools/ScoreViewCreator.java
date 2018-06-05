package it.polimi.se2018.view.tools;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to define ScoreViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public abstract class ScoreViewCreator<E> {

    protected final List<Pair<String, Integer>> scores;

    public ScoreViewCreator() {
        scores = new ArrayList<>();
    }

    /**
     * Use to show the score at the game's end
     *
     * @param matchIdentifier contains the MatchIdentifier of the match just ended
     * @param player0         contains the points of first player
     * @param player1         contains the points of second player
     * @param player2         contains the points of third player
     * @param player3         contains the points of fourth player
     * @return score the object to display
     */
    public abstract E display(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3);

    protected void createOrderList(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3) {
        scores.add(new Pair<>(matchIdentifier.player0, player0));
        scores.add(new Pair<>(matchIdentifier.player1, player1));
        if (matchIdentifier.player2 != null && !matchIdentifier.player2.equals("")) {
            scores.add(new Pair<>(matchIdentifier.player2, player2));
            if (matchIdentifier.player3 != null && !matchIdentifier.player3.equals("")) {
                scores.add(new Pair<>(matchIdentifier.player3, player3));
            }
        }
        scores.sort((o1, o2) -> o2.getSecond() - o1.getSecond());
    }
}
