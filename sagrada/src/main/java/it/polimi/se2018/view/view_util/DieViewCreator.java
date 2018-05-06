package it.polimi.se2018.view.view_util;

import it.polimi.se2018.util.Pair;

/**
 * Interface to define DieViewCreator's Object
 *
 * @param <E>
 * @author Mathyas Giudici
 */

public interface DieViewCreator<E> {

    /**
     * Creates a Game Die
     *
     * @param die contains the die's information
     * @return die
     */
    E makeDie(Pair die);
}
