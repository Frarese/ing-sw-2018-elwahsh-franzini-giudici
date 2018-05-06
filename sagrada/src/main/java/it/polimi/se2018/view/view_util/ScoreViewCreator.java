package it.polimi.se2018.view.view_util;

import java.util.ArrayList;

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
     */
    E display(ArrayList scores);
}
