package it.polimi.se2018.view.tools;

import it.polimi.se2018.model.IntColorPair;

/**
 * Interface to define DieViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public interface DieViewCreator<E> {

    /**
     * Creates a Game Die
     *
     * @param die contains the die's information
     * @return die
     */
    E makeDie(IntColorPair die);
}
