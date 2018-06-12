package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;

/**
 * Error sent during card execution
 * @author Alì El wahsh
 */
public class CardExecutionError extends ServerMessage {

    /**
     * Constructor
     * @param error error string
     */
    public CardExecutionError(String error)
    {
        this.description = error;
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }
}
