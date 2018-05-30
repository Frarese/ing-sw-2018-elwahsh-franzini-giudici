package it.polimi.se2018.events;

import java.io.Serializable;


/**
 * Generic event
 * @author Alì El Wahsh
 */
public abstract class Event implements Serializable {

    protected String description;


    @Override
    public String toString() {
        return description;
    }
}
