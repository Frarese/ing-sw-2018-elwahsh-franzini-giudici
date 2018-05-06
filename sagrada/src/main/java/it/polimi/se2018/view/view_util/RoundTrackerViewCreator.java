package it.polimi.se2018.view.view_util;

/**
 * Interface to define RoundTrackerViewCreator's Object
 *
 * @param <E>
 * @author Mathyas Giudici
 */

public interface RoundTrackerViewCreator<E> {

    /**
     * Use to show the current round tracker
     *
     * @return round tracker
     */
    E display();
}
