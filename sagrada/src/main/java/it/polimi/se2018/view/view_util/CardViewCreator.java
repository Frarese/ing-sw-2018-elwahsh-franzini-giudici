package it.polimi.se2018.view.view_util;

/**
 * Interface to define CardViewCreator's Object
 *
 * @param <E>
 * @author Mathyas Giudici
 */

public interface CardViewCreator<E> {

    /**
     * Creates a Game Card
     *
     * @param cardID contains the unique identifier of the card
     * @return card
     */
    E makeCart(int cardID);
}
