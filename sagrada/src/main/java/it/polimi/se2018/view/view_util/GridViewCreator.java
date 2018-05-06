package it.polimi.se2018.view.view_util;

/**
 * Interface to define GridViewCreator's Object
 *
 * @param <E>
 * @param <V>
 * @author Mathyas Giudici
 */

public interface GridViewCreator<E, V> {

    /**
     * Use to show the current grid
     *
     * @return grid
     */
    E display();

    /**
     * Use to add a die in the grid
     *
     * @param die    contains the die to add
     * @param height contains the height position on the grid
     * @param width  contains the width position on the grid
     */
    void addADie(V die, int height, int width);

    /**
     * Use to pick a die from the grid
     *
     * @param height contains the height position in the grid
     * @param width  contains the width position in the grid
     * @return die
     */
    V pickDie(int height, int width);
}
