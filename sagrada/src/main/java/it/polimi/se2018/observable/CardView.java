package it.polimi.se2018.observable;

import it.polimi.se2018.events.messages.CardInfo;
import it.polimi.se2018.util.SingleCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Model proxy of the in-game cards
 * @author Alì El wahsh, Mathyas Giudici
 */
public class CardView extends Observable {

    /**
     * Class attributes
     */
    private SingleCardView privateObjectiveCard;
    private List<SingleCardView> publicObjectiveCards;
    private List<SingleCardView> toolCards;

    /**
     * Constructor
     * @param privateObjectiveCard private objective
     * @param publicObjectiveCards public objectives
     * @param toolCards toll cards
     */
    public CardView(SingleCardView privateObjectiveCard, List<SingleCardView> publicObjectiveCards, List<SingleCardView> toolCards) {
        this.privateObjectiveCard = privateObjectiveCard;
        this.publicObjectiveCards = publicObjectiveCards;
        this.toolCards = toolCards;
        this.uniqueNotify();
    }

    /**
     * Setter for the cards in game
     * @param info card in game status
     */
    public void setCardView(CardInfo info)
    {
        publicObjectiveCards=new ArrayList<>();
        toolCards=new ArrayList<>();
        for(int i= 0; i<3;i++)
        {
            this.publicObjectiveCards.add(new SingleCardView(info.getObjectives()[i],0));
            this.toolCards.add(new SingleCardView(info.getTools()[i],info.getToolCost()[i]));
        }
    }

    /**
     * Getter for the private objective
     * @return private objective
     */
    public SingleCardView getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }
    /**
     * Setter for the private objective
     * @param privateObjectiveCard private objective card
     */
    public void setPrivateObjectiveCard(SingleCardView privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
        this.uniqueNotify();
    }
    /**
     * Getter for the public objectives
     * @return public objectives
     */
    public List<SingleCardView> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    /**
     * Setter for the public objectives
     * @param publicObjectiveCards public objective cards
     */
    void setPublicObjectiveCards(List<SingleCardView> publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.uniqueNotify();
    }

    /**
     * Getter for the tool cards
     * @return tool cards
     */
    public List<SingleCardView> getToolCards() {
        return toolCards;
    }

    /**
     * Setter for the tool cards
     * @param toolCards the tool cards in game
     */
    void setToolCards(List<SingleCardView> toolCards) {
        this.toolCards = toolCards;
        this.uniqueNotify();
    }

    /**
     * Notifies all observers
     */
    public synchronized void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
