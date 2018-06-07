package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;

/**
 * Each player get a single private objective ID
 * @author Alì El wahsh
 */
public class PrivateObjectiveStatus extends Event {

    private int cardId;

    public PrivateObjectiveStatus(PrivateObjectiveCard card)
    {
        cardId = card.getId();
        this.description = "PrivateObjective";
    }

    /**
     * Getter for private objective card'S ID
     * @return card's ID
     */
    public int getCardId() {
        return cardId;
    }
}
