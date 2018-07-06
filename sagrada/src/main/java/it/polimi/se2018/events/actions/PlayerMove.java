package it.polimi.se2018.events.actions;

import it.polimi.se2018.events.Event;


/**
 * Generic player's move
 * @author Alì El Wahsh
 */
public abstract class PlayerMove extends Event {

    String playerName;

    /**
     * Getter fot the player's ID
     * @return the player's ID
     */
    public String getPlayerName()
    {
        return playerName;
    }



}
