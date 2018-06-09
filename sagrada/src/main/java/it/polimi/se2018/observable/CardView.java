package it.polimi.se2018.observable;

import it.polimi.se2018.events.messages.CardInfo;
import it.polimi.se2018.util.SingleCardView;

import java.util.ArrayList;
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

    void setPublicObjectiveCards(List<SingleCardView> publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.uniqueNotify();
    }

    public List<SingleCardView> getToolCards() {
        return toolCards;
    }

    void setToolCards(List<SingleCardView> toolCards) {
        this.toolCards = toolCards;
        this.uniqueNotify();
    }

    public synchronized void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
