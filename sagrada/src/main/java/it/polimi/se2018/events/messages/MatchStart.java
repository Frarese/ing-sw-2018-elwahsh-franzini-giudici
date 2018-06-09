package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;

/**
 * Message representing the end of the match setup phase
 * @author Francesco Franzini
 */
public class MatchStart extends Event {
    public MatchStart() {
        this.description="MatchStart";
    }
}
