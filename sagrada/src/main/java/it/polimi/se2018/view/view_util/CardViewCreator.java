package it.polimi.se2018.view.view_util;

import it.polimi.se2018.util.CardIdentifier;

/**
 * Class to define CardViewCreator's Object
 *
 * @param <E>
 * @author Mathyas Giudici
 */

public abstract class CardViewCreator<E> {

    protected CardIdentifier cardIdentifier;
    protected int privateObjectiveCard;
    protected int[] publicObjectiveCards, toolCards;

    /**
     * Class constructor
     *
     * @param privateObjectiveCard contains the private objective
     * @param publicObjectiveCards contains the public objectives
     * @param toolCards contians the tool cards
     */
    public CardViewCreator(int privateObjectiveCard, int[] publicObjectiveCards, int[] toolCards) {
        this.privateObjectiveCard = privateObjectiveCard;
        this.publicObjectiveCards = publicObjectiveCards;
        this.toolCards = toolCards;
        this.cardIdentifier = new CardIdentifier();
    }

    /**
     * Creates a Game Card
     *
     * @param cardID contains the unique identifier of the card
     * @return card
     */
    public abstract E makeCart(int cardID);
}
