package it.polimi.se2018.observer;

import it.polimi.se2018.util.SingleCardView;

import java.util.List;
import java.util.Observable;

public class CardView extends Observable {

    /**
     * Class attributes
     */
    private SingleCardView privateObjectiveCard;
    private List<SingleCardView> publicObjectiveCards;
    private List<SingleCardView> toolCards;

    public CardView(SingleCardView privateObjectiveCard, List<SingleCardView> publicObjectiveCards, List<SingleCardView> toolCards) {
        this.privateObjectiveCard = privateObjectiveCard;
        this.publicObjectiveCards = publicObjectiveCards;
        this.toolCards = toolCards;
        this.uniqueNotify();
    }

    public SingleCardView getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public void setPrivateObjectiveCard(SingleCardView privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
        this.uniqueNotify();
    }

    public List<SingleCardView> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public void setPublicObjectiveCards(List<SingleCardView> publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.uniqueNotify();
    }

    public List<SingleCardView> getToolCards() {
        return toolCards;
    }

    public void setToolCards(List<SingleCardView> toolCards) {
        this.toolCards = toolCards;
        this.uniqueNotify();
    }

    private synchronized void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
