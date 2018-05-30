package it.polimi.se2018.observer;

import it.polimi.se2018.util.SingleCardView;

import java.util.Observable;

public class CardView extends Observable {

    /**
     * Class attributes
     */
    private SingleCardView privateObjectiveCard;
    private SingleCardView[] publicObjectiveCards;
    private SingleCardView[] toolCards;

    public CardView(SingleCardView privateObjectiveCard, SingleCardView[] publicObjectiveCards, SingleCardView[] toolCards) {
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

    public SingleCardView[] getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public void setPublicObjectiveCards(SingleCardView[] publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.uniqueNotify();
    }

    public SingleCardView[] getToolCards() {
        return toolCards;
    }

    public void setToolCards(SingleCardView[] toolCards) {
        this.toolCards = toolCards;
        this.uniqueNotify();
    }

    private synchronized void uniqueNotify(){
        setChanged();
        notifyObservers(this);
    }
}
