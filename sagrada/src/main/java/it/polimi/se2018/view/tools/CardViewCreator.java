package it.polimi.se2018.view.tools;

import it.polimi.se2018.util.CardIdentifier;
import it.polimi.se2018.util.SingleCardView;

import java.util.List;

/**
 * Class to define CardViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public abstract class CardViewCreator<E> {

    protected final CardIdentifier cardIdentifier;
    protected SingleCardView privateObjectiveCard;
    protected List<SingleCardView> publicObjectiveCards;
    protected List<SingleCardView> toolCards;

    /**
     * Basic Class constructor that initializes elements at default value
     */
    protected CardViewCreator() {
        this.privateObjectiveCard = null;
        this.publicObjectiveCards = null;
        this.toolCards = null;
        this.cardIdentifier = new CardIdentifier();
    }

    /**
     * Class constructor
     *
     * @param privateObjectiveCard contains the private objective
     * @param publicObjectiveCards contains the public objectives
     * @param toolCards            contains the tool cards
     */
    protected CardViewCreator(SingleCardView privateObjectiveCard, List<SingleCardView> publicObjectiveCards, List<SingleCardView> toolCards) {
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

    public synchronized SingleCardView getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public synchronized void setPrivateObjectiveCard(SingleCardView privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public synchronized List<SingleCardView> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public synchronized void setPublicObjectiveCards(List<SingleCardView> publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
    }

    public synchronized List<SingleCardView> getToolCards() {
        return toolCards;
    }

    public synchronized void setToolCards(List<SingleCardView> toolCards) {
        this.toolCards = toolCards;
    }
}
