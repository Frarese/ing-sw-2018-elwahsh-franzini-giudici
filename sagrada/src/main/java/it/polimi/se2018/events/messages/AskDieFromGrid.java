package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;

/**
 * Request for a die from the grid
 * @author Alì El Wahsh
 */
public class AskDieFromGrid extends  ServerMessage {

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }
}
