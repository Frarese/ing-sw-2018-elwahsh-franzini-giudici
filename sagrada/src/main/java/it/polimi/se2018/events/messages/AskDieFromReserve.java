package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;

/**
 * Asks for a die from the reserve
 * @author Alì El wahsh
 */
public class AskDieFromReserve extends ServerMessage {

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }
}
