package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;

/**
 * Notifies players the beginning of a new turn
 */
public class TurnStart extends Event {

    private String name;

    /**
     * TurnStart's constructor
     * @param playerName new current player
     */
    public TurnStart(String playerName)
    {
         this.name = playerName;
    }

    /**
     * Getter for current player's name
     * @return current player's name
     */
    public String getName() {
        return name;
    }
}
