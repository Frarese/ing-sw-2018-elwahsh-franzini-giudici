package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;

/**
 * Message representing the end of the match setup phase
 * @author Francesco Franzini
 */
public class MatchStart extends ServerMessage {
    final boolean recover;

    /**
     * Initializes this Match start
     * @param recover true if this is a recovery of a match
     */
    public MatchStart(boolean recover) {
        this.description="MatchStart";
        this.recover=recover;
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }
}
