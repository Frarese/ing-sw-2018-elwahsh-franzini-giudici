package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;

/**
 * Request for a die from the round track
 * @author Alì El wahsh
 */
public class AskDieFromRoundTrack extends ServerMessage {

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }
}
