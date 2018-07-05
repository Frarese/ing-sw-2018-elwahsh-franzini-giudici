package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;

/**
 * Request for the number of placements for TapWheel
 * @author Alì El wahsh
 */
public class AskPlacements extends ServerMessage {


    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }
}
