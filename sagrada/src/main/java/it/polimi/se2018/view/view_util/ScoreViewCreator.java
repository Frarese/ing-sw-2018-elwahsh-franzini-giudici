package it.polimi.se2018.view.view_util;

import it.polimi.se2018.util.ScoreEntry;

/**
 * Interface to define ScoreViewCreator's Object
 *
 * @param <E>
 * @author Mathyas Giudici
 */

public interface ScoreViewCreator<E> {

    /**
     * Use to show the score at the game's end
     *
     * @return score
     * @param scores
     */
    E display(ScoreEntry scores);
}
