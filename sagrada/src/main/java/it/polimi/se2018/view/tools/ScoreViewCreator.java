package it.polimi.se2018.view.tools;

import it.polimi.se2018.util.ScoreEntry;

import java.util.List;

/**
 * Class to define ScoreViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public interface ScoreViewCreator<E> {

    /**
     * Use to show the score at the game's end
     *
     * @param scores the scores to use
     * @return score the object to display
     */
    E display(List<ScoreEntry> scores);
}
