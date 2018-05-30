package it.polimi.se2018.view.tools;

import it.polimi.se2018.util.CardIdentifier;

/**
 * Class to define CardViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public abstract class CardViewCreator<E> {

    protected final CardIdentifier cardIdentifier;
    protected final int privateObjectiveCard;
    protected final int[] publicObjectiveCards;
    protected final int[] toolCards;

    /**
     * Class constructor
     *
     * @param privateObjectiveCard contains the private objective
     * @param publicObjectiveCards contains the public objectives
     * @param toolCards            contains the tool cards
     */
    protected CardViewCreator(int privateObjectiveCard, int[] publicObjectiveCards, int[] toolCards) {
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
    public abstract E makeCard(int cardID);

    public int getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public int[] getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public int[] getToolCards() {
        return toolCards;
    }
}
