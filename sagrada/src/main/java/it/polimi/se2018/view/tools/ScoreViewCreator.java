package it.polimi.se2018.view.tools;

import it.polimi.se2018.util.MatchIdentifier;

/**
 * Class to define ScoreViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public interface ScoreViewCreator<E> {

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
    E display(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3);
}
