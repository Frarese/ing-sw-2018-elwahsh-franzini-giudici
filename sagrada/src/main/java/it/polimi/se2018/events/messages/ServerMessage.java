package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.events.ServerMessageHandler;

public abstract class ServerMessage extends Event {
    public abstract void visit(ServerMessageHandler handler);
}
